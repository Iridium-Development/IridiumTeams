package com.iridium.iridiumteams.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.iridiumteams.gui.ShopCategoryGUI;
import com.iridium.iridiumteams.gui.ShopOverviewGUI;
import com.iridium.testplugin.TestPlugin;
import org.bukkit.inventory.InventoryHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopCommandTest {

    private ServerMock serverMock;

    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(TestPlugin.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void executeShopCommand__Success() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test shop");
        assertTrue(playerMock.getOpenInventory().getTopInventory().getHolder() instanceof ShopOverviewGUI<?,?>);
    }

    @Test
    public void executeShopCommand__InvalidCategory() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test shop invalid");
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().noShopCategory
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)
        ));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void executeShopCommand__ValidCategory() {
        PlayerMock playerMock = new UserBuilder(serverMock).build();
        serverMock.dispatchCommand(playerMock, "test shop mob drops");
        InventoryHolder inventoryHolder = playerMock.getOpenInventory().getTopInventory().getHolder();
        assertTrue(inventoryHolder instanceof ShopCategoryGUI<?,?>);
        ShopCategoryGUI shopCategoryGUI = (ShopCategoryGUI) inventoryHolder;
        assertEquals("Mob Drops", shopCategoryGUI.getCategoryName());
    }

}