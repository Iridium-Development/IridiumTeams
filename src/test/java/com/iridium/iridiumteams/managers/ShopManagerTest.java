package com.iridium.iridiumteams.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.configs.Shop;
import com.iridium.testplugin.TestPlugin;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShopManagerTest {
    private ServerMock serverMock;
    private Shop.ShopItem dirtItem;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);

        dirtItem = new Shop.ShopItem(
                "&9&lDirt Block",
                XMaterial.DIRT,
                16,
                0,
                new Shop.Cost(200, new HashMap<>()),
                new Shop.Cost(30, new HashMap<>())
        );
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void ShopManager__Buy__CantAfford() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();

        TestPlugin.getInstance().getShopManager().buy(playerMock, dirtItem, 1);

        playerMock.assertSoundHeard(Sound.BLOCK_ANVIL_LAND);
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotAfford
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void ShopManager__Buy__CanAfford() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TestPlugin.getInstance().getEconomy().depositPlayer(playerMock, 10000);

        TestPlugin.getInstance().getShopManager().buy(playerMock, dirtItem, 64);

        playerMock.assertSoundHeard(Sound.ENTITY_PLAYER_LEVELUP);
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().successfullyBought
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "64")
                .replace("%item%", StringUtils.color(dirtItem.name))
                .replace("%vault_cost%", "800")
        ));
        playerMock.assertNoMoreSaid();

        assertEquals(64, InventoryUtils.getAmount(playerMock.getInventory(), XMaterial.DIRT));
        assertEquals(9200, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
    }

    @Test
    public void ShopManager__Sell() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        playerMock.getInventory().addItem(new ItemStack(Material.DIRT, 64));

        TestPlugin.getInstance().getShopManager().sell(playerMock, dirtItem, 64);

        playerMock.assertSoundHeard(Sound.ENTITY_PLAYER_LEVELUP);
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().successfullySold
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "64")
                .replace("%item%", StringUtils.color(dirtItem.name))
                .replace("%vault_reward%", "120.0")
        ));
        playerMock.assertNoMoreSaid();

        assertEquals(0, InventoryUtils.getAmount(playerMock.getInventory(), XMaterial.DIRT));
        assertEquals(120, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
    }

    @Test
    public void ShopManager__Sell__StackWhenYouDontHaveAStack() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        playerMock.getInventory().addItem(new ItemStack(Material.DIRT, 32));

        TestPlugin.getInstance().getShopManager().sell(playerMock, dirtItem, 64);

        playerMock.assertSoundHeard(Sound.ENTITY_PLAYER_LEVELUP);
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().successfullySold
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "32")
                .replace("%item%", StringUtils.color(dirtItem.name))
                .replace("%vault_reward%", "60.0")
        ));
        playerMock.assertNoMoreSaid();

        assertEquals(0, InventoryUtils.getAmount(playerMock.getInventory(), XMaterial.DIRT));
        assertEquals(60, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
    }

    @Test
    public void ShopManager__Sell__NoneInInventory() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        TestPlugin.getInstance().getEconomy().depositPlayer(playerMock, 10000);

        TestPlugin.getInstance().getShopManager().sell(playerMock, dirtItem, 64);

        playerMock.assertSoundHeard(Sound.BLOCK_ANVIL_LAND);
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noSuchItem
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();

        assertEquals(0, InventoryUtils.getAmount(playerMock.getInventory(), XMaterial.DIRT));
        assertEquals(10000, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
    }
}