package com.iridium.iridiumteams.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

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
                1,
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

        TestPlugin.getInstance().getShopManager().buy(playerMock, dirtItem, 1);

        playerMock.assertSoundHeard(Sound.ENTITY_PLAYER_LEVELUP);
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().successfullyBought
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
                .replace("%amount%", "1")
                .replace("%item%", StringUtils.color(dirtItem.name))
                .replace("%vault_cost%", "200.0")
        ));
        playerMock.assertNoMoreSaid();
        int dirtBlocks = Arrays.stream(playerMock.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(itemStack -> itemStack.getType() == Material.DIRT)
                .mapToInt(ItemStack::getAmount)
                .sum();

        assertEquals(1, dirtBlocks);
        assertEquals(9800, TestPlugin.getInstance().getEconomy().getBalance(playerMock));
    }
}