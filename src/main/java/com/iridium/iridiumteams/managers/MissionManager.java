package com.iridium.iridiumteams.managers;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Mission;
import com.iridium.iridiumteams.MissionType;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamMission;
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
     * @param environment The environment we are in
     * @param missionType The mission type e.g. BREAK
     * @param identifier  The mission identifier e.g. COBBLESTONE
     * @param amount      The amount we are incrementing by
     */
    public void handleMissionUpdate(T team, World.Environment environment, String missionType, String identifier, int amount) {
        //TODO Do something to generate all missions
        incrementMission(team, missionType + ":" + identifier, amount);
        incrementMission(team, missionType + ":ANY", amount);
        incrementMission(team, environment.name() + ":" + missionType + ":" + identifier, amount);
        incrementMission(team, environment.name() + ":" + missionType + ":ANY", amount);

        for (Map.Entry<String, List<String>> itemList : iridiumTeams.getMissions().customMaterialLists.entrySet()) {
            if (itemList.getValue().contains(identifier)) {
                incrementMission(team, missionType + ":" + itemList.getKey(), amount);
                incrementMission(team, environment.name() + ":" + missionType + ":" + itemList.getKey(), amount);
            }
        }
    }

    private synchronized void incrementMission(T team, String missionData, int amount) {
        List<TeamMission> teamMissions = iridiumTeams.getTeamManager().getTeamMissions(team);
        String[] missionConditions = missionData.toUpperCase().split(":");

        for (Map.Entry<String, Mission> entry : iridiumTeams.getMissions().missions.entrySet()) {
            //Check if we have completed the mission before by testing if we update any values
            boolean completedBefore = true;
            List<String> missions = entry.getValue().getMissions();
            for (int i = 0; i < entry.getValue().getMissions().size(); i++) {
                int missionIndex = i;
                String missionRequirement = missions.get(missionIndex).toUpperCase();
                String[] conditions = missionRequirement.split(":");
                // If the conditions arnt the same length continue (+1 since we add amount onto the missionConditions dynamically)
                if (missionConditions.length + 1 != conditions.length) continue;

                // Check if this is a mission we want to increment
                boolean matches = matchesMission(missionConditions, conditions);
                if (!matches) continue;

                Optional<TeamMission> teamMission = teamMissions.stream()
                        .filter(mission -> mission.getMissionName().equals(entry.getKey()) && mission.getMissionIndex() == missionIndex)
                        .findFirst();
                if (!teamMission.isPresent()) continue;
                String number = conditions[missionData.split(":").length];

                try {
                    int totalAmount = Integer.parseInt(number);
                    if (teamMission.get().getProgress() >= totalAmount) break;
                    completedBefore = false;
                    teamMission.get().setProgress(Math.min(teamMission.get().getProgress() + amount, totalAmount));
                } catch (NumberFormatException exception) {
                    iridiumTeams.getLogger().warning("Unknown format " + missionRequirement);
                    iridiumTeams.getLogger().warning(number + " Is not a number");
                }
            }

            // Check if this mission is now completed
            if (!completedBefore && hasCompletedMission(team, entry.getKey())) {
                iridiumTeams.getTeamManager().getTeamMembers(team).stream().map(U::getPlayer).filter(Objects::nonNull).forEach(member -> {
                    member.sendMessage(StringUtils.color(entry.getValue().getMessage().replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
                    entry.getValue().getCompleteSound().play(member);
                });
            }
        }
    }

    private boolean matchesMission(String[] missionConditions, String[] conditions) {
        for (int i = 0; i < missionConditions.length; i++) {
            if (!conditions[i].equals(missionConditions[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean hasCompletedMission(T team, String missionName) {
        Mission mission = iridiumTeams.getMissions().missions.get(missionName);
        List<String> missions = mission.getMissions();
        for (int missionIndex = 0; missionIndex < mission.getMissions().size(); missionIndex++) {
            String missionRequirement = missions.get(missionIndex).toUpperCase();
            TeamMission teamMission = iridiumTeams.getTeamManager().getTeamMission(team, missionName, missionIndex);
            String[] conditions = missionRequirement.split(":");
            String number = conditions[conditions.length - 1];

            try {
                if (teamMission.getProgress() < Integer.parseInt(number)) return false;
            } catch (NumberFormatException exception) {
                iridiumTeams.getLogger().warning("Unknown format " + missionRequirement);
                iridiumTeams.getLogger().warning(number + " Is not a number");
            }
        }
        return true;
    }
}
