package com.iridium.iridiumteams.managers;

import com.iridium.iridiumcore.dependencies.nbtapi.NBTItem;
import com.iridium.iridiumcore.dependencies.nbtapi.NBTType;
import com.iridium.iridiumcore.dependencies.paperlib.PaperLib;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.*;
import com.iridium.iridiumteams.api.EnhancementUpdateEvent;
import com.iridium.iridiumteams.configs.BlockValues;
import com.iridium.iridiumteams.database.*;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.EnhancementData;
import com.iridium.iridiumteams.missions.Mission;
import com.iridium.iridiumteams.missions.MissionType;
import com.iridium.iridiumteams.sorting.ExperienceTeamSort;
import com.iridium.iridiumteams.sorting.TeamSorting;
import com.iridium.iridiumteams.sorting.ValueTeamSort;
import com.iridium.iridiumteams.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class TeamManager<T extends Team, U extends IridiumUser<T>> {
    private final IridiumTeams<T, U> iridiumTeams;

    public TeamManager(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    public abstract Optional<T> getTeamViaID(int id);

    public abstract Optional<T> getTeamViaName(String name);

    public abstract Optional<T> getTeamViaLocation(Location location);

    public abstract Optional<T> getTeamViaNameOrPlayer(String name);

    public Optional<T> getTeamViaPlayerLocation(Player player) {
        return getTeamViaLocation(player.getLocation());
    }

    public abstract void sendTeamTitle(Player player, T team);

    public boolean canVisit(Player player, T team) {
        TeamSetting teamSetting = getTeamSetting(team, SettingType.TEAM_VISITING.getSettingKey());
        U user = iridiumTeams.getUserManager().getUser(player);
        return user.isBypassing() || user.getTeamID() == team.getId() || teamSetting.getValue().equalsIgnoreCase("Enabled");
    }

    public abstract List<T> getTeams();

    public List<T> getTeams(TeamSorting<T> sortType, boolean excludePrivate) {
        return sortType.getSortedTeams(iridiumTeams).stream()
                .filter(team -> !excludePrivate || getTeamSetting(team, SettingType.VALUE_VISIBILITY.getSettingKey()).getValue().equalsIgnoreCase("Public"))
                .collect(Collectors.toList());
    }

    public List<T> getTeams(SortType sortType, boolean excludePrivate) {
        switch (sortType) {
            case Value:
                return getTeams(new ValueTeamSort<>(), excludePrivate);
            case Experience:
                return getTeams(new ExperienceTeamSort<>(), excludePrivate);
            default:
                return getTeams();
        }
    }

    public List<U> getTeamMembers(T team) {
        return iridiumTeams.getUserManager().getUsers().stream()
                .filter(user -> user.getTeamID() == team.getId())
                .collect(Collectors.toList());
    }

    public abstract CompletableFuture<T> createTeam(@NotNull Player owner, @Nullable String name);

    public abstract boolean deleteTeam(T team, U user);

    public int getUserRank(T team, U user) {
        if (user.getTeamID() == team.getId()) return user.getUserRank();
        // if they are not in the same team, they are a visitor
        return Rank.VISITOR.getId();
    }

    public abstract boolean getTeamPermission(T team, int rank, String permission);

    public abstract void setTeamPermission(T team, int rank, String permission, boolean allowed);

    public boolean getTeamPermission(T team, U user, String permission) {
        if (user.isBypassing()) return true;
        if (getTeamTrust(team, user).isPresent()) {
            if (getTeamPermission(team, 1, permission)) return true;
        }
        return getTeamPermission(team, getUserRank(team, user), permission);
    }

    public boolean getTeamPermission(T team, U user, PermissionType permissionType) {
        return getTeamPermission(team, user, permissionType.getPermissionKey());
    }

    public boolean getTeamPermission(Location location, U user, String permission) {
        return getTeamViaLocation(location).map(team -> getTeamPermission(team, user, permission)).orElse(true);
    }

    public abstract Optional<TeamInvite> getTeamInvite(T team, U user);

    public abstract List<TeamInvite> getTeamInvites(T team);

    public abstract void createTeamInvite(T team, U user, U invitee);

    public abstract void deleteTeamInvite(TeamInvite teamInvite);

    public abstract Optional<TeamTrust> getTeamTrust(T team, U user);

    public abstract List<TeamTrust> getTeamTrusts(T team);

    public abstract void createTeamTrust(T team, U user, U invitee);

    public abstract void deleteTeamTrust(TeamTrust teamTrust);

    public abstract TeamBank getTeamBank(T team, String bankItem);

    public abstract TeamSpawners getTeamSpawners(T team, EntityType entityType);

    public abstract TeamBlock getTeamBlock(T team, XMaterial xMaterial);

    public abstract TeamSetting getTeamSetting(T team, String setting);

    public double getTeamValue(T team) {
        double value = 0;

        for (Map.Entry<XMaterial, BlockValues.ValuableBlock> valuableBlock : iridiumTeams.getBlockValues().blockValues.entrySet()) {
            value += getTeamBlock(team, valuableBlock.getKey()).getAmount() * valuableBlock.getValue().value;
        }

        for (Map.Entry<EntityType, BlockValues.ValuableBlock> valuableSpawner : iridiumTeams.getBlockValues().spawnerValues.entrySet()) {
            value += getTeamSpawners(team, valuableSpawner.getKey()).getAmount() * valuableSpawner.getValue().value;
        }

        return value;
    }

    public abstract TeamEnhancement getTeamEnhancement(T team, String enhancement);

    public boolean UpdateEnhancement(T team, String booster, Player player) {
        Enhancement<?> enhancement = iridiumTeams.getEnhancementList().get(booster);
        TeamEnhancement teamEnhancement = getTeamEnhancement(team, booster);

        if (!teamEnhancement.isActive(enhancement.type)) teamEnhancement.setLevel(0);

        EnhancementData enhancementData = enhancement.levels.get(teamEnhancement.getLevel() + 1);
        if (enhancementData == null) enhancementData = enhancement.levels.get(teamEnhancement.getLevel());

        if (enhancementData.minLevel > team.getLevel()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notHighEnoughLevel
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%level%", String.valueOf(enhancementData.minLevel))
            ));
            return false;
        }

        if (iridiumTeams.getEconomy().getBalance(player) < enhancementData.money) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notEnoughMoney
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        for (Map.Entry<String, Double> bankCost : enhancementData.bankCosts.entrySet()) {
            if (getTeamBank(team, bankCost.getKey()).getNumber() < bankCost.getValue()) {
                player.sendMessage(StringUtils.color(iridiumTeams.getMessages().notEnoughBankItem
                        .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                        .replace("%bank%", bankCost.getKey())
                ));
                return false;
            }
        }

        iridiumTeams.getEconomy().withdrawPlayer(player, enhancementData.money);

        for (Map.Entry<String, Double> bankCost : enhancementData.bankCosts.entrySet()) {
            TeamBank teamBank = getTeamBank(team, bankCost.getKey());
            teamBank.setNumber(teamBank.getNumber() - bankCost.getValue());
        }

        if (enhancement.levels.containsKey(teamEnhancement.getLevel() + 1)) {
            U user = iridiumTeams.getUserManager().getUser(player);
            EnhancementUpdateEvent<T, U> enhancementUpdateEvent = new EnhancementUpdateEvent<>(team, user, teamEnhancement.getLevel() + 1, booster);

            Bukkit.getPluginManager().callEvent(enhancementUpdateEvent);
            if (enhancementUpdateEvent.isCancelled()) return false;

            teamEnhancement.setLevel(enhancementUpdateEvent.getNextLevel());
        }
        if (teamEnhancement.getExpirationTime().isBefore(LocalDateTime.now())) {
            teamEnhancement.setExpirationTime(LocalDateTime.now().plusHours(1));
        } else {
            teamEnhancement.setExpirationTime(teamEnhancement.getExpirationTime().plusHours(1));
        }
        return true;
    }

    public abstract CompletableFuture<Void> recalculateTeam(T team);

    public abstract void createWarp(T team, UUID creator, Location location, String name, String password);

    public abstract void deleteWarp(TeamWarp teamWarp);

    public abstract List<TeamWarp> getTeamWarps(T team);

    public abstract Optional<TeamWarp> getTeamWarp(T team, String name);

    public abstract List<TeamMission> getTeamMissions(T team);

    public abstract TeamMission getTeamMission(T team, String missionName);

    public abstract TeamMissionData getTeamMissionData(TeamMission teamMission, int missionIndex);

    public abstract List<TeamMissionData> getTeamMissionData(TeamMission teamMission);

    public abstract void deleteTeamMission(TeamMission teamMission);

    public void resetTeamMissionData(TeamMission teamMission) {
        getTeamMissionData(teamMission).forEach(teamMissionData -> teamMissionData.setProgress(0));
    }

    public List<String> getTeamMission(T team, MissionType missionType) {
        // Get list of current missions
        List<TeamMission> teamMissions = getTeamMissions(team).stream()
                .filter(teamMission -> iridiumTeams.getMissions().missions.containsKey(teamMission.getMissionName()))
                .filter(teamMission -> iridiumTeams.getMissions().missions.get(teamMission.getMissionName()).getMissionType() == missionType)
                .filter(teamMission -> iridiumTeams.getMissions().missions.get(teamMission.getMissionName()).getMissionData().get(teamMission.getMissionLevel()).getItem().slot == null)
                .collect(Collectors.toList());
        // Filter and delete expired ones
        List<String> missions = new ArrayList<>();
        for (TeamMission teamMission : teamMissions) {
            if (teamMission.hasExpired()) {
                deleteTeamMission(teamMission);
            } else {
                missions.add(teamMission.getMissionName());
            }
        }

        // Fill to make sure list is at correct size
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Map.Entry<String, Mission>> availableMissions = iridiumTeams.getMissions().missions.entrySet().stream()
                .filter(mission -> !mission.getValue().getMissionData().isEmpty())
                .filter(mission -> mission.getValue().getMissionData().get(1).getItem().slot == null)
                .filter(mission -> mission.getValue().getMissionType() == missionType)
                .filter(mission -> !missions.contains(mission.getKey()))
                .collect(Collectors.toList());
        while (missions.size() < iridiumTeams.getMissions().dailySlots.size() && availableMissions.size() > 0) {
            Map.Entry<String, Mission> newMission = availableMissions.get(random.nextInt(availableMissions.size()));
            missions.add(newMission.getKey());
            availableMissions.remove(newMission);
        }
        return missions;
    }

    public abstract List<TeamReward> getTeamRewards(T team);

    public abstract void addTeamReward(TeamReward teamReward);

    public abstract void deleteTeamReward(TeamReward teamReward);

    public void claimTeamReward(TeamReward teamReward, Player player) {
        Reward reward = teamReward.getReward();
        deleteTeamReward(teamReward);
        reward.sound.play(player);
        iridiumTeams.getEconomy().depositPlayer(player, reward.money);
        PlayerUtils.setTotalExperience(player, PlayerUtils.getTotalExperience(player) + reward.experience);
        getTeamViaID(teamReward.getTeamID()).ifPresent(team -> {
            team.setExperience(team.getExperience() + reward.teamExperience);
            for (Map.Entry<String, Double> entry : reward.bankRewards.entrySet()) {
                TeamBank teamBank = getTeamBank(team, entry.getKey());
                teamBank.setNumber(teamBank.getNumber() + entry.getValue());
            }
        });
        reward.commands.forEach(command ->
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()))
        );
    }

    public void sendTeamTime(Player player) {
        getTeamViaPlayerLocation(player).ifPresent(team -> {
            TeamSetting teamSetting = getTeamSetting(team, SettingType.TIME.getSettingKey());
            switch (teamSetting.getValue().toLowerCase()) {
                case "sunrise":
                    setPlayerTime(player, 0, false);
                    return;
                case "day":
                    setPlayerTime(player, 1000, false);
                    return;
                case "morning":
                    setPlayerTime(player, 6000, false);
                    return;
                case "noon":
                    setPlayerTime(player, 9000, false);
                    return;
                case "sunset":
                    setPlayerTime(player, 12000, false);
                    return;
                case "night":
                    setPlayerTime(player, 13000, false);
                    return;
                case "midnight":
                    setPlayerTime(player, 18000, false);
                    return;
                default:
                    setPlayerTime(player, 0, true);
            }
        });
    }

    public void sendTeamWeather(Player player) {
        getTeamViaPlayerLocation(player).ifPresent(team -> {
            TeamSetting teamSetting = getTeamSetting(team, SettingType.WEATHER.getSettingKey());
            switch (teamSetting.getValue().toLowerCase()) {
                case "sunny":
                    setPlayerWeather(player, WeatherType.CLEAR);
                    return;
                case "raining":
                    setPlayerWeather(player, WeatherType.DOWNFALL);
                    return;
                default:
                    player.resetPlayerWeather();
            }
        });
    }

    private void setPlayerTime(Player player, long time, boolean relative) {
        if (player.isPlayerTimeRelative() != relative || player.getPlayerTime() != time) {
            player.setPlayerTime(time, relative);
        }
    }

    private void setPlayerWeather(Player player, WeatherType weatherType) {
        if (player.getPlayerWeather() != weatherType) {
            player.setPlayerWeather(weatherType);
        }
    }

    public boolean teleport(Player player, Location location, T team) {
        player.setFallDistance(0);
        PaperLib.teleportAsync(player, location);
        player.teleport(location);
        return true;
    }

    public void handleBlockBreakOutsideTerritory(BlockBreakEvent blockEvent) {
        // By default do nothing
    }

    public void handleBlockPlaceOutsideTerritory(BlockPlaceEvent blockEvent) {
        // By default do nothing
    }

    public boolean isBankItem(ItemStack item) {
        if(item == null || item.getType() == Material.AIR) {
            return false;
        }
        return new NBTItem(item).hasTag(iridiumTeams.getName().toLowerCase() , NBTType.NBTTagCompound);
    }

    public enum SortType {
        Experience, Value
    }
}
