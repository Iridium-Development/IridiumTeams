package com.iridium.iridiumteams.gui;

import com.iridium.iridiumcore.gui.BackGUI;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.configs.Shop;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShopCategoryGUI<T extends Team, U extends IridiumUser<T>> extends BackGUI {
    private final IridiumTeams<T, U> iridiumTeams;
    @Getter
    private final String categoryName;
    private final Shop.ShopCategory shopCategory;

    public ShopCategoryGUI(String categoryName, Inventory previousInventory, IridiumTeams<T, U> iridiumTeams) {
        super(iridiumTeams.getInventories().shopCategoryGUI.background, previousInventory, iridiumTeams.getInventories().backButton);
        this.iridiumTeams = iridiumTeams;
        this.categoryName = categoryName;
        this.shopCategory = iridiumTeams.getShop().categories.get(categoryName);
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
            ItemStack itemStack = shopItem.type.parseItem();
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemStack.setAmount(shopItem.defaultAmount);
            itemMeta.setDisplayName(StringUtils.color(shopItem.name));

            List<String> lore = shopItem.lore == null ? new ArrayList<>() : new ArrayList<>(StringUtils.color(shopItem.lore));
            addShopLore(lore, shopItem);

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            inventory.setItem(shopItem.slot, itemStack);
        }
    }

    private void addShopLore(List<String> lore, Shop.ShopItem item) {
        if (item.buyCost.canPurchase()) {
            lore.add(StringUtils.color(iridiumTeams.getShop().buyPriceLore
                    .replace("%amount%", String.valueOf(item.defaultAmount))
                    .replace("%buy_price_vault%", formatPrice(item.buyCost.money))
            ));
        } else {
            lore.add(StringUtils.color(iridiumTeams.getShop().notPurchasableLore));
        }

        if (item.sellCost.canPurchase()) {
            lore.add(StringUtils.color(iridiumTeams.getShop().sellRewardLore
                    .replace("%amount%", String.valueOf(item.defaultAmount))
                    .replace("%sell_reward_vault%", formatPrice(item.sellCost.money))
            ));
        } else {
            lore.add(StringUtils.color(iridiumTeams.getShop().notSellableLore));
        }

        iridiumTeams.getShop().shopItemLore.stream()
                .map(StringUtils::color)
                .forEach(line -> lore.add(line.replace("%amount%", String.valueOf(item.defaultAmount))));
    }

    private String formatPrice(double value) {
        if (iridiumTeams.getShop().abbreviatePrices) {
            return iridiumTeams.getConfiguration().numberFormatter.format(value);
        } else {
            return String.valueOf(value);
        }
    }
}
