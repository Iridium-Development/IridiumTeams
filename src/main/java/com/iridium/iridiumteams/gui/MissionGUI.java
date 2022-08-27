package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamMission;
import com.iridium.iridiumteams.missions.Mission;
import com.iridium.iridiumteams.missions.MissionData;
import com.iridium.iridiumteams.missions.MissionType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MissionGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final T team;
    @Getter
    private final MissionType missionType;
    private final IridiumTeams<T, U> iridiumTeams;

    public MissionGUI(T team, MissionType missionType, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().missionGUI.get(missionType).background, previousInventory, iridiumTeams.getInventories().backButton);
        this.team = team;
        this.missionType = missionType;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().missionGUI.get(missionType);
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        List<TeamMission> teamMissions = iridiumTeams.getTeamManager().getTeamMissions(team);
        for (Map.Entry<String, Mission> entry : iridiumTeams.getMissions().missions.entrySet()) {
            if (entry.getValue().getMissionType() != missionType) continue;
            int level = teamMissions.stream().filter(m -> m.getMissionName().equals(entry.getKey())).map(TeamMission::getMissionLevel).findFirst().orElse(1);
            MissionData missionData = entry.getValue().getMissionData().get(level);
            if (missionData.getItem().slot == null) continue;
            inventory.setItem(missionData.getItem().slot, getItem(entry.getKey()));
        }

        List<String> missions = iridiumTeams.getTeamManager().getTeamMission(team, missionType);
        int index = 0;
        for (String missionName : missions) {
            if (iridiumTeams.getMissions().dailySlots.size() <= index) continue;
            int slot = iridiumTeams.getMissions().dailySlots.get(index);
            inventory.setItem(slot, getItem(missionName));
            index++;
        }
    }

    private ItemStack getItem(String missionName) {
        TeamMission teamMission = iridiumTeams.getTeamManager().getTeamMission(team, missionName);
        Mission mission = iridiumTeams.getMissions().missions.get(missionName);
        MissionData missionData = mission.getMissionData().get(teamMission.getMissionLevel());

        List<Placeholder> placeholders = IntStream.range(0, missionData.getMissions().size())
                .boxed()
                .map(integer -> iridiumTeams.getTeamManager().getTeamMissionData(teamMission, integer))
                .map(islandMission -> new Placeholder("progress_" + (islandMission.getMissionIndex() + 1), String.valueOf(islandMission.getProgress())))
                .collect(Collectors.toList());

        int seconds = Math.max((int) (teamMission.getRemainingTime() % 60), 0);
        int minutes = Math.max((int) ((teamMission.getRemainingTime() % 3600) / 60), 0);
        int hours = Math.max((int) (teamMission.getRemainingTime() / 3600), 0);
        placeholders.add(new Placeholder("timeremaining_hours", String.valueOf(hours)));
        placeholders.add(new Placeholder("timeremaining_minutes", String.valueOf(minutes)));
        placeholders.add(new Placeholder("timeremaining_seconds", String.valueOf(seconds)));
        return ItemStackUtils.makeItem(missionData.getItem(), placeholders);
    }
}
