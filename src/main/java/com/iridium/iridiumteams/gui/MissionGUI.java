package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Mission;
import com.iridium.iridiumteams.MissionType;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MissionGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final T team;
    private final MissionType missionType;
    private final IridiumTeams<T, U> iridiumTeams;

    public MissionGUI(T team, MissionType missionType, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().missionGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.team = team;
        this.missionType = missionType;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().missionGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        Map<String, Mission> missions = iridiumTeams.getTeamManager().getTeamMission(team, missionType);
        int index = 0;
        for (Map.Entry<String, Mission> entry : missions.entrySet()) {
            if (iridiumTeams.getMissions().dailySlots.size() <= index) continue;
            int slot = iridiumTeams.getMissions().dailySlots.get(index);

            List<Placeholder> placeholders = IntStream.range(0, entry.getValue().getMissions().size())
                    .boxed()
                    .map(integer -> iridiumTeams.getTeamManager().getTeamMission(team, entry.getKey(), integer))
                    .map(islandMission -> new Placeholder("progress_" + (islandMission.getMissionIndex() + 1), String.valueOf(islandMission.getProgress())))
                    .collect(Collectors.toList());

            inventory.setItem(slot, ItemStackUtils.makeItem(entry.getValue().getItem(), placeholders));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

    }
}
