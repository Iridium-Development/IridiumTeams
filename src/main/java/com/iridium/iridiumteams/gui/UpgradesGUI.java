package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.EnhancementData;
import com.iridium.iridiumteams.enhancements.EnhancementType;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class UpgradesGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {

    private final T team;
    private final IridiumTeams<T, U> iridiumTeams;
    private final Map<Integer, String> upgrades = new HashMap<>();

    public UpgradesGUI(T team, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().upgradesGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.team = team;
        this.iridiumTeams = iridiumTeams;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().upgradesGUI;
        Inventory inventory = Bukkit.createInventory(this, noItemGUI.size, StringUtils.color(noItemGUI.title));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        upgrades.clear();
        for (Map.Entry<String, Enhancement<?>> enhancementEntry : iridiumTeams.getEnhancementList().entrySet()) {
            if (enhancementEntry.getValue().type != EnhancementType.UPGRADE) continue;
            upgrades.put(enhancementEntry.getValue().item.slot, enhancementEntry.getKey());
            TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(team, enhancementEntry.getKey());
            EnhancementData currentData = enhancementEntry.getValue().levels.get(teamEnhancement.getLevel());
            EnhancementData nextData = enhancementEntry.getValue().levels.get(teamEnhancement.getLevel() + 1);
            int seconds = Math.max((int) (teamEnhancement.getRemainingTime() % 60), 0);
            int minutes = Math.max((int) ((teamEnhancement.getRemainingTime() % 3600) / 60), 0);
            int hours = Math.max((int) (teamEnhancement.getRemainingTime() / 3600), 0);
            String nextLevel = nextData == null ? "N/A" : String.valueOf(teamEnhancement.getLevel() + 1);
            String cost = nextData == null ? "N/A" : String.valueOf(nextData.money);
            List<Placeholder> placeholders = currentData == null ? new ArrayList<>() : new ArrayList<>(currentData.getPlaceholders());
            placeholders.addAll(Arrays.asList(
                    new Placeholder("timeremaining_hours", String.valueOf(hours)),
                    new Placeholder("timeremaining_minutes", String.valueOf(minutes)),
                    new Placeholder("timeremaining_seconds", String.valueOf(seconds)),
                    new Placeholder("current_level", String.valueOf(teamEnhancement.getLevel())),
                    new Placeholder("next_level", nextLevel),
                    new Placeholder("cost", cost)
            ));
            inventory.setItem(enhancementEntry.getValue().item.slot, ItemStackUtils.makeItem(enhancementEntry.getValue().item, placeholders));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        if (!upgrades.containsKey(event.getSlot())) return;
        String upgrade = upgrades.get(event.getSlot());
        iridiumTeams.getCommandManager().executeCommand(event.getWhoClicked(), iridiumTeams.getCommands().upgradesCommand, new String[]{"buy", upgrade});
    }
}
