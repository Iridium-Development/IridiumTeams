package com.iridium.iridiumteams.managers;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.*;
import com.iridium.iridiumteams.gui.MissionGUI;
import com.iridium.iridiumteams.missions.Mission;
import com.iridium.iridiumteams.missions.MissionData;
import com.iridium.iridiumteams.missions.MissionType;
import org.bukkit.World;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class MissionManager<T extends Team, U extends IridiumUser<T>> {
    private final IridiumTeams<T, U> iridiumTeams;

    public MissionManager(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    public LocalDateTime getExpirationTime(MissionType missionType, LocalDateTime startTime) {
        LocalDateTime baseTime = startTime.withSecond(0).withMinute(0).withHour(0);
        switch (missionType) {
            case ONCE:
                return null;
            case DAILY:
                return baseTime.plusDays(1);
            case WEEKLY:
                baseTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            case INFINITE:
                return null;
        }
        return null;
    }

    /**
     * Determines the missions to be checked
     *
     * @param team        The team
     * @param missionWorld       The world we are in
     * @param missionType The mission type e.g. BREAK
     * @param identifier  The mission identifier e.g. COBBLESTONE
     * @param amount      The amount we are incrementing by
     */
    public void handleMissionUpdate(T team, World missionWorld, String missionType, String identifier, int amount) {

        if (iridiumTeams.getConfiguration().whitelistedWorlds.stream().noneMatch(world -> missionWorld.getName().equalsIgnoreCase(world))
        && !iridiumTeams.getConfiguration().whitelistedWorlds.isEmpty()) return;

        generateMissionData(team);
        incrementMission(team, missionType + ":" + identifier, amount);
        incrementMission(team, missionType + ":ANY", amount);
        incrementMission(team, missionWorld.getEnvironment().name() + ":" + missionType + ":" + identifier, amount);
        incrementMission(team, missionWorld.getEnvironment().name() + ":" + missionType + ":ANY", amount);

        for (Map.Entry<String, List<String>> itemList : iridiumTeams.getMissions().customMaterialLists.entrySet()) {
            if (itemList.getValue().contains(identifier)) {
                incrementMission(team, missionType + ":" + itemList.getKey(), amount);
                incrementMission(team, missionWorld.getEnvironment().name() + ":" + missionType + ":" + itemList.getKey(), amount);
            }
        }
    }

    private synchronized void incrementMission(T team, String condition, int amount) {
        List<TeamMission> teamMissions = iridiumTeams.getTeamManager().getTeamMissions(team);
        String[] missionConditions = condition.toUpperCase().split(":");

        for (Map.Entry<String, Mission> entry : iridiumTeams.getMissions().missions.entrySet()) {
            Optional<TeamMission> teamMission = teamMissions.stream()
                    .filter(mission -> mission.getMissionName().equals(entry.getKey()))
                    .findFirst();
            if (!teamMission.isPresent()) continue;
            //Check if we have completed the mission before by testing if we update any values
            boolean completedBefore = true;
            int level = teamMissions.stream().filter(m -> m.getMissionName().equals(entry.getKey())).map(TeamMission::getMissionLevel).findFirst().orElse(1);
            MissionData missionData = entry.getValue().getMissionData().get(level);
            if (!dependenciesComplete(team, missionData.getMissionDependencies(), teamMissions)) continue;

            List<String> missions = missionData.getMissions();
            for (int missionIndex = 0; missionIndex < missions.size(); missionIndex++) {
                TeamMissionData teamMissionData = iridiumTeams.getTeamManager().getTeamMissionData(teamMission.get(), missionIndex);
                String missionRequirement = missions.get(missionIndex).toUpperCase();
                String[] conditions = missionRequirement.split(":");
                // If the conditions arnt the same length continue (+1 since we add amount onto the missionConditions dynamically)
                if (missionConditions.length + 1 != conditions.length) continue;

                // Check if this is a mission we want to increment
                boolean matches = matchesMission(missionConditions, conditions);
                if (!matches) continue;

                String number = conditions[condition.split(":").length];

                try {
                    int totalAmount = Integer.parseInt(number);
                    if (teamMissionData.getProgress() >= totalAmount) break;
                    completedBefore = false;
                    teamMissionData.setProgress(Math.min(teamMissionData.getProgress() + amount, totalAmount));
                } catch (NumberFormatException exception) {
                    iridiumTeams.getLogger().warning("Unknown format " + missionRequirement);
                    iridiumTeams.getLogger().warning(number + " Is not a number");
                }
            }

            // Check if this mission is now completed
            if (!completedBefore && hasCompletedMission(team, entry.getKey(), missionData)) {
                iridiumTeams.getTeamManager().addTeamReward(new TeamReward(team, missionData.getReward()));
                iridiumTeams.getTeamManager().getTeamMembers(team).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(member -> {
                    member.sendMessage(StringUtils.color(missionData.getMessage().replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
                    missionData.getCompleteSound().play(member);
                });
                // Next Mission Level
                if (entry.getValue().getMissionData().containsKey(level + 1)) {
                    teamMission.get().setMissionLevel(level + 1);
                    iridiumTeams.getTeamManager().resetTeamMissionData(teamMission.get());
                }
            }
        }
    }

    private boolean dependenciesComplete(T team, List<MissionData.MissionDependency> missionDependencies, List<TeamMission> teamMissions) {
        for (MissionData.MissionDependency missionDependency : missionDependencies) {
            Optional<TeamMission> teamMission = teamMissions.stream()
                    .filter(mission -> mission.getMissionName().equals(missionDependency.getMission()))
                    .filter(mission -> mission.getMissionLevel() == missionDependency.getLevel())
                    .findFirst();
            if (!teamMission.isPresent()) return false;
            MissionData missionData = iridiumTeams.getMissions().missions.get(missionDependency.getMission()).getMissionData().get(missionDependency.getLevel());
            if (!hasCompletedMission(team, missionDependency.getMission(), missionData)) return false;
        }
        return true;
    }

    private boolean matchesMission(String[] missionConditions, String[] conditions) {
        for (int i = 0; i < missionConditions.length; i++) {
            if (!conditions[i].equals(missionConditions[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean hasCompletedMission(T team, String missionName, MissionData missionData) {
        List<String> missions = missionData.getMissions();
        TeamMission teamMission = iridiumTeams.getTeamManager().getTeamMission(team, missionName);
        for (int missionIndex = 0; missionIndex < missions.size(); missionIndex++) {
            TeamMissionData teamMissionData = iridiumTeams.getTeamManager().getTeamMissionData(teamMission, missionIndex);
            String missionRequirement = missions.get(missionIndex).toUpperCase();
            String[] conditions = missionRequirement.split(":");
            String number = conditions[conditions.length - 1];

            try {
                if (teamMissionData.getProgress() < Integer.parseInt(number)) return false;
            } catch (NumberFormatException exception) {
                iridiumTeams.getLogger().warning("Unknown format " + missionRequirement);
                iridiumTeams.getLogger().warning(number + " Is not a number");
            }
        }
        return true;
    }

    private void generateMissionData(T team) {
        // Generate mission data by opening all missionGUI's
        new MissionGUI<T, U>(team, MissionType.ONCE, null, iridiumTeams).getInventory();
        new MissionGUI<T, U>(team, MissionType.DAILY, null, iridiumTeams).getInventory();
        new MissionGUI<T, U>(team, MissionType.WEEKLY, null, iridiumTeams).getInventory();
    }
}
