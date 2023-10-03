package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.Shop;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ShopCategoryGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {
    private final IridiumTeams<T, U> iridiumTeams;
    @Getter
    private final String categoryName;
    private final Shop.ShopCategory shopCategory;
    @Getter
    private int page;

    public ShopCategoryGUI(String categoryName, Inventory previousInventory, int page, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().shopCategoryGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.iridiumTeams = iridiumTeams;
        this.categoryName = categoryName;
        this.shopCategory = iridiumTeams.getShop().categories.get(categoryName);
        this.page = page;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        NoItemGUI noItemGUI = iridiumTeams.getInventories().shopOverviewGUI;
        Inventory inventory = Bukkit.createInventory(this, shopCategory.inventorySize, StringUtils.color(noItemGUI.title.replace("%category_name%", categoryName)));
        addContent(inventory);
        return inventory;
    }

    @Override
    public void addContent(Inventory inventory) {
        super.addContent(inventory);

        if (!iridiumTeams.getShop().items.containsKey(categoryName)) {
            iridiumTeams.getLogger().warning("Shop Category " + categoryName + " Is not configured with any items!");
            return;
        }

        for (Shop.ShopItem shopItem : iridiumTeams.getShop().items.get(categoryName)) {
            if (shopItem.page != this.page) continue;
            ItemStack itemStack = shopItem.type.parseItem();
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemStack.setAmount(shopItem.defaultAmount);
            itemMeta.setDisplayName(StringUtils.color(shopItem.name));
            itemMeta.setLore(getShopLore(shopItem));

            itemStack.setItemMeta(itemMeta);
            inventory.setItem(shopItem.slot, itemStack);
        }

        inventory.setItem(inventory.getSize() - 3, ItemStackUtils.makeItem(this.iridiumTeams.getInventories().nextPage));
        inventory.setItem(inventory.getSize() - 7, ItemStackUtils.makeItem(this.iridiumTeams.getInventories().previousPage));
    }

    private List<Placeholder> getShopLorePlaceholders(Shop.ShopItem item) {
        List<Placeholder> placeholders = new ArrayList<>(Arrays.asList(
                new Placeholder("amount", iridiumTeams.getShopManager().formatPrice(item.defaultAmount)),
                new Placeholder("vault_cost", iridiumTeams.getShopManager().formatPrice(item.buyCost.money)),
                new Placeholder("vault_reward", iridiumTeams.getShopManager().formatPrice(item.sellCost.money))
        ));
        for (Map.Entry<String, Double> bankItem : item.buyCost.bankItems.entrySet()) {
            placeholders.add(new Placeholder(bankItem.getKey() + "_cost", iridiumTeams.getShopManager().formatPrice(bankItem.getValue())));
        }
        for (Map.Entry<String, Double> bankItem : item.sellCost.bankItems.entrySet()) {
            placeholders.add(new Placeholder(bankItem.getKey() + "_reward", iridiumTeams.getShopManager().formatPrice(bankItem.getValue())));
        }
        return placeholders;
    }

    private List<String> getShopLore(Shop.ShopItem item) {
        List<String> lore = item.lore == null ? new ArrayList<>() : new ArrayList<>(StringUtils.color(item.lore));
        List<Placeholder> placeholders = getShopLorePlaceholders(item);

        if (item.buyCost.canPurchase()) {
            lore.add(iridiumTeams.getShop().buyPriceLore);
        } else {
            lore.add(iridiumTeams.getShop().notPurchasableLore);
        }

        if (item.sellCost.canPurchase()) {
            lore.add(iridiumTeams.getShop().sellRewardLore);
        } else {
            lore.add(iridiumTeams.getShop().notSellableLore);
        }

        lore.addAll(iridiumTeams.getShop().shopItemLore);

        return StringUtils.color(StringUtils.processMultiplePlaceholders(lore, placeholders));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);

        if (event.getSlot() == event.getInventory().getSize() - 3 && doesNextPageExist()) {
            this.page++;
            addContent(event.getInventory());
            return;
        }

        if (event.getSlot() == event.getInventory().getSize() - 7 && doesPreviousPageExist()) {
            this.page--;
            addContent(event.getInventory());
            return;
        }

        Optional<Shop.ShopItem> shopItem = iridiumTeams.getShop().items.get(categoryName).stream()
                .filter(item -> item.slot == event.getSlot())
                .filter(item -> item.page == this.page)
                .findAny();

        if (!shopItem.isPresent()) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        int amount = event.isShiftClick() ? shopItem.get().type.parseItem().getMaxStackSize() : shopItem.get().defaultAmount;
        if (event.isLeftClick() && shopItem.get().buyCost.canPurchase()) {
            iridiumTeams.getShopManager().buy(player, shopItem.get(), amount);
        } else if (event.isRightClick() && shopItem.get().sellCost.canPurchase()) {
            iridiumTeams.getShopManager().sell(player, shopItem.get(), amount);
        } else {
            iridiumTeams.getShop().failSound.play(player);
        }
    }

    private boolean doesNextPageExist() {
        return iridiumTeams.getShop().items.get(categoryName).stream().anyMatch(item -> item.page == this.page + 1);
    }

    private boolean doesPreviousPageExist() {
        return iridiumTeams.getShop().items.get(categoryName).stream().anyMatch(item -> item.page == this.page - 1);
    }
}
