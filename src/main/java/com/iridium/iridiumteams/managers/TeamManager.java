package com.iridium.iridiumteams.managers;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.CreateCancelledException;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.configs.BlockValues;
import com.iridium.iridiumteams.database.*;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.EnhancementData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class TeamManager<T extends Team, U extends IridiumUser<T>> {
    private final IridiumTeams<T, U> iridiumTeams;

    public TeamManager(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    public abstract Optional<T> getTeamViaID(int id);

    public abstract Optional<T> getTeamViaName(String name);

    public abstract Optional<T> getTeamViaLocation(Location location);

    public abstract Optional<T> getTeamViaNameOrPlayer(String name);

    public abstract List<T> getTeams();

    public abstract List<U> getTeamMembers(T team);

    public abstract CompletableFuture<T> createTeam(@NotNull Player owner, @NotNull String name) throws CreateCancelledException;

    public abstract void deleteTeam(T team, U user);

    public int getUserRank(T team, U user) {
        if (user.getTeamID() == team.getId()) return user.getUserRank();
        // if they are not in the same team, they are a visitor
        return Rank.VISITOR.getId();
    }

    public abstract boolean getTeamPermission(T team, int rank, String permission);

    public abstract void setTeamPermission(T team, int rank, String permission, boolean allowed);

    public boolean getTeamPermission(T team, U user, String permission) {
        return user.isBypassing() || getTeamPermission(team, getUserRank(team, user), permission);
    }

    public boolean getTeamPermission(T team, U user, PermissionType permissionType) {
        return getTeamPermission(team, user, permissionType.getPermissionKey()) || user.isBypassing();
    }

    public boolean getTeamPermission(Location location, U user, String permission) {
        return getTeamViaLocation(location).map(team -> getTeamPermission(team, user, permission)).orElse(true);
    }

    public abstract Optional<TeamInvite> getTeamInvite(T team, U user);

    public abstract List<TeamInvite> getTeamInvites(T team);

    public abstract void createTeamInvite(T team, U user, U invitee);

    public abstract void deleteTeamInvite(TeamInvite teamInvite);

    public abstract TeamBank getTeamBank(T team, String bankItem);

    public abstract TeamSpawners getTeamSpawners(T team, EntityType entityType);

    public abstract TeamBlock getTeamBlock(T team, XMaterial xMaterial);

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
            teamEnhancement.setLevel(teamEnhancement.getLevel() + 1);
        }
        teamEnhancement.setExpirationTime(LocalDateTime.now().plusHours(1));
        return true;
    }

    public abstract CompletableFuture<Void> recalculateTeam(T team);

    public abstract void createWarp(T team, Location location, String name, String password);
    public abstract void deleteWarp(TeamWarp teamWarp);

    public abstract List<TeamWarp> getTeamWarps(T team);

    public abstract Optional<TeamWarp> getTeamWarp(T team, String name);
}
