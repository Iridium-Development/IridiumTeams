package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Setting;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamSetting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class SettingsGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final IridiumTeams<T, U> iridiumTeams;
    private final T team;

    public SettingsGUI(T team, Player player, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().settingsGUI.background, player, iridiumTeams.getInventories().backButton);
        this.iridiumTeams = iridiumTeams;
        this.team = team;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, iridiumTeams.getInventories().settingsGUI.size, StringUtils.color(iridiumTeams.getInventories().settingsGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        for (Map.Entry<String, Setting> setting : iridiumTeams.getSettingsList().entrySet()) {
            TeamSetting teamSetting = iridiumTeams.getTeamManager().getTeamSetting(team, setting.getKey());
            if (teamSetting == null) continue;

            String teamSettingDisplay = teamSetting.getValue();
            switch(teamSetting.getValue()) {
                case "Enabled": {
                    teamSettingDisplay = iridiumTeams.getMessages().enabledPlaceholder;
                    break;
                }
                case "Disabled": {
                    teamSettingDisplay = iridiumTeams.getMessages().disabledPlaceholder;
                    break;
                }
                case "Private": {
                    teamSettingDisplay = iridiumTeams.getMessages().privatePlaceholder;
                    break;
                }
                case "Public": {
                    teamSettingDisplay = iridiumTeams.getMessages().publicPlaceholder;
                    break;
                }
                case "Server": {
                    teamSettingDisplay = iridiumTeams.getMessages().serverPlaceholder;
                    break;
                }
                case "Sunny": {
                    teamSettingDisplay = iridiumTeams.getMessages().sunnyPlaceholder;
                    break;
                }
                case "Raining": {
                    teamSettingDisplay = iridiumTeams.getMessages().rainingPlaceholder;
                    break;
                }
                case "Sunrise": {
                    teamSettingDisplay = iridiumTeams.getMessages().sunrisePlaceholder;
                    break;
                }
                case "Day": {
                    teamSettingDisplay = iridiumTeams.getMessages().dayPlaceholder;
                    break;
                }
                case "Morning": {
                    teamSettingDisplay = iridiumTeams.getMessages().morningPlaceholder;
                    break;
                }
                case "Noon": {
                    teamSettingDisplay = iridiumTeams.getMessages().noonPlaceholder;
                    break;
                }
                case "Sunset": {
                    teamSettingDisplay = iridiumTeams.getMessages().sunsetPlaceholder;
                    break;
                }
                case "Night": {
                    teamSettingDisplay = iridiumTeams.getMessages().nightPlaceholder;
                    break;
                }
                case "Midnight": {
                    teamSettingDisplay = iridiumTeams.getMessages().midnightPlaceholder;
                    break;
                }
            }

            inventory.setItem(setting.getValue().getItem().slot, ItemStackUtils.makeItem(setting.getValue().getItem(), Collections.singletonList(
                    new Placeholder("value", teamSettingDisplay)
            )));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        for (Map.Entry<String, Setting> setting : iridiumTeams.getSettingsList().entrySet()) {
            if (setting.getValue().getItem().slot != event.getSlot()) continue;

            TeamSetting teamSetting = iridiumTeams.getTeamManager().getTeamSetting(team, setting.getKey());
            if (teamSetting == null) continue;
            int currentIndex = setting.getValue().getValues().indexOf(teamSetting.getValue());
            String newValue = setting.getValue().getValues().get(setting.getValue().getValues().size() > currentIndex + 1 ? currentIndex + 1 : 0);
            iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().settingsCommand, new String[]{setting.getValue().getDisplayName(), newValue});
            return;
        }
    }
}
