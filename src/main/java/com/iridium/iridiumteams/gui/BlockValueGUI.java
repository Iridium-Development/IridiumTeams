package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.gui.PagedGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.BlockValues;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class BlockValueGUI<T extends Team, U extends IridiumUser<T>> extends PagedGUI<BlockValues.ValuableBlock> {

    private final T team;
    private final IridiumTeams<T, U> iridiumTeams;
    @Getter
    private final BlockValueType blockValueType;

    public BlockValueGUI(T team, BlockValueType blockValueType, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(
                1,
                iridiumTeams.getInventories().blockValueGUI.size,
                iridiumTeams.getInventories().blockValueGUI.background,
                iridiumTeams.getInventories().previousPage,
                iridiumTeams.getInventories().nextPage,
                previousInventory,
                iridiumTeams.getInventories().backButton
        );
        this.team = team;
        this.iridiumTeams = iridiumTeams;
        this.blockValueType = blockValueType;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        int maxPages = getPageObjects().size() / (getSize() - 9);
        if (getPageObjects().size() % (getSize() - 9) > 0) maxPages++;

        String guiTitle = "";
        if (blockValueType == BlockValueType.BLOCK) guiTitle = iridiumTeams.getMessages().blockName;
        if (blockValueType == BlockValueType.SPAWNER) guiTitle = iridiumTeams.getMessages().spawnerName;

        NoItemGUI noItemGUI = iridiumTeams.getInventories().blockValueGUI;
        Inventory inventory = Bukkit.createInventory(this, getSize(), StringUtils.color(noItemGUI.title
                .replace("%blockType%", guiTitle)
                .replace("%page%", String.valueOf(getPage()))
                .replace("%max_pages%", String.valueOf(maxPages))
        ));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        if (blockValueType == BlockValueType.BLOCK) {
            for (Map.Entry<XMaterial, BlockValues.ValuableBlock> entry : iridiumTeams.getBlockValues().blockValues.entrySet().stream().filter(entry -> entry.getValue().page == getPage()).collect(Collectors.toList())) {

                List<String> lore = new ArrayList<>();
                lore.add(iridiumTeams.getBlockValues().valueLore
                        .replace("%block_value%", String.valueOf(entry.getValue().value))
                );
                lore.add(iridiumTeams.getBlockValues().teamValueLore
                        .replace("%total_blocks%", String.valueOf(iridiumTeams.getTeamManager().getTeamBlock(team, entry.getKey()).getAmount()))
                        .replace("%total_block_value%", String.valueOf(iridiumTeams.getTeamManager().getTeamBlock(team, entry.getKey()).getAmount() * entry.getValue().value))
                );

                inventory.setItem(entry.getValue().slot, ItemStackUtils.makeItem(entry.getKey(), 1, entry.getValue().name, lore));
            }
        }

        if (blockValueType == BlockValueType.SPAWNER) {
            for (Map.Entry<EntityType, BlockValues.ValuableBlock> entry : iridiumTeams.getBlockValues().spawnerValues.entrySet().stream().filter(entry -> entry.getValue().page == getPage()).collect(Collectors.toList())) {

                List<String> lore = new ArrayList<>();
                lore.add(iridiumTeams.getBlockValues().valueLore
                        .replace("%block_value%", String.valueOf(entry.getValue().value))
                );
                lore.add(iridiumTeams.getBlockValues().teamValueLore
                        .replace("%total_blocks%", String.valueOf(iridiumTeams.getTeamManager().getTeamSpawners(team, entry.getKey()).getAmount()))
                        .replace("%total_block_value%", String.valueOf(iridiumTeams.getTeamManager().getTeamSpawners(team, entry.getKey()).getAmount() * entry.getValue().value))
                );

                String itemName = entry.getKey().name().toUpperCase() + "_SPAWN_EGG";
                XMaterial item = XMaterial.matchXMaterial(itemName).orElse(XMaterial.SPAWNER);

                inventory.setItem(entry.getValue().slot, ItemStackUtils.makeItem(item, 1, entry.getValue().name, lore));
            }
        }
    }

    @Override
    public Collection<BlockValues.ValuableBlock> getPageObjects() {
        if (blockValueType == BlockValueType.BLOCK) return iridiumTeams.getBlockValues().blockValues.values();
        else return iridiumTeams.getBlockValues().spawnerValues.values();
    }

    @Override
    public ItemStack getItemStack(BlockValues.ValuableBlock valuableBlock) {
        return null;
    }

    public enum BlockValueType {
        BLOCK, SPAWNER;

        public static BlockValueType getType(String type) {
            return Arrays.stream(BlockValueType.values()).filter(blockValueType -> blockValueType.name().equalsIgnoreCase(type)).findFirst().orElse(null);
        }
    }

}
