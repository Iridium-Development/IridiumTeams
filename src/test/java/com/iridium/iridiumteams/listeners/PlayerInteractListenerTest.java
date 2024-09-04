package com.iridium.iridiumteams.listeners;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.PermissionType;
import com.iridium.iridiumteams.SettingType;
import com.iridium.iridiumteams.TeamBuilder;
import com.iridium.iridiumteams.UserBuilder;
import com.iridium.testplugin.TestPlugin;
import com.iridium.testplugin.TestTeam;
import com.iridium.testplugin.managers.TeamManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerInteractListenerTest {

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
    public void onSignChange__Cannot_Edit_Sign() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.INTERACT, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock sign = new BlockMock(Material.OAK_SIGN);

        SignChangeEvent signChangeEvent = new SignChangeEvent(
                sign,
                playerMock,
                new ArrayList<Component>()
        );

        serverMock.getPluginManager().callEvent(signChangeEvent);

        assertTrue(signChangeEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotInteract
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onSignChange__Can_Edit_Sign() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.INTERACT, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock sign = new BlockMock(Material.OAK_SIGN);

        SignChangeEvent signChangeEvent = new SignChangeEvent(
                sign,
                playerMock,
                new ArrayList<Component>()
        );

        serverMock.getPluginManager().callEvent(signChangeEvent);

        assertFalse(signChangeEvent.isCancelled());
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteractEntityEvent__Cannot_Interact() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.SPAWNERS, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        Entity entity = playerMock.getWorld().spawnEntity(playerMock.getLocation(), EntityType.ITEM_FRAME);

        PlayerInteractEntityEvent playerInteractEntityEvent = new PlayerInteractEntityEvent(
                playerMock,
                entity
        );

        serverMock.getPluginManager().callEvent(playerInteractEntityEvent);

        assertTrue(playerInteractEntityEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotInteract
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteractEntityEvent__Can_Interact() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.SPAWNERS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        Entity entity = playerMock.getWorld().spawnEntity(playerMock.getLocation(), EntityType.ITEM_FRAME);

        PlayerInteractEntityEvent playerInteractEntityEvent = new PlayerInteractEntityEvent(
                playerMock,
                entity
        );

        serverMock.getPluginManager().callEvent(playerInteractEntityEvent);

        assertFalse(playerInteractEntityEvent.isCancelled());
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Cannot_Open_Containers() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.OPEN_CONTAINERS, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock block = new BlockMock(Material.CHEST);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                new ItemStack(Material.AIR),
                block,
                BlockFace.EAST
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertTrue(playerInteractEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotOpenContainers
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Can_Open_Containers() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.OPEN_CONTAINERS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock block = new BlockMock(Material.CHEST);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                new ItemStack(Material.AIR),
                block,
                BlockFace.EAST
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertFalse(playerInteractEvent.isCancelled());
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Cannot_Open_Doors() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.DOORS, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock block = new BlockMock(Material.OAK_DOOR);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                new ItemStack(Material.AIR),
                block,
                BlockFace.EAST
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertTrue(playerInteractEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotOpenDoors
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Can_Open_Doors() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.DOORS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock block = new BlockMock(Material.OAK_DOOR);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                new ItemStack(Material.AIR),
                block,
                BlockFace.EAST
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertFalse(playerInteractEvent.isCancelled());
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Cannot_Trigger_Redstone() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.REDSTONE, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock block = new BlockMock(Material.LEVER);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                new ItemStack(Material.AIR),
                block,
                BlockFace.EAST
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertTrue(playerInteractEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotTriggerRedstone
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Can_Trigger_Redstone() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.REDSTONE, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock block = new BlockMock(Material.LEVER);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                new ItemStack(Material.AIR),
                block,
                BlockFace.EAST
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertFalse(playerInteractEvent.isCancelled());
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Cannot_Trample_Crops() {
        TestTeam team = new TeamBuilder().withSetting(SettingType.CROP_TRAMPLE, "Disabled").build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock block = new BlockMock(Material.FARMLAND);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.PHYSICAL,
                new ItemStack(Material.AIR),
                block,
                BlockFace.UP
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertTrue(playerInteractEvent.isCancelled());
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Can_Trample_Crops() {
        TestTeam team = new TeamBuilder().withSetting(SettingType.CROP_TRAMPLE, "Enabled").build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        BlockMock block = new BlockMock(Material.FARMLAND);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.PHYSICAL,
                new ItemStack(Material.AIR),
                block,
                BlockFace.UP
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertFalse(playerInteractEvent.isCancelled());
        playerMock.assertNoMoreSaid();
    }

    /**@Test
    public void onPlayerInteract__Cannot_Change_Spawners() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.SPAWNERS, false).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        CreatureSpawnerMock spawnerMock = new CreatureSpawnerMock();
        BlockMock blockMock = new BlockMock(Material.SPAWNER);
        blockMock.setState(spawnerMock);

        ItemStack itemStack = new ItemStack(Material.PIG_SPAWN_EGG);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                itemStack,
                blockMock,
                BlockFace.EAST
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertTrue(playerInteractEvent.isCancelled());
        playerMock.assertSaid(StringUtils.color(TestPlugin.getInstance().getMessages().cannotBreakSpawners
                .replace("%prefix%", TestPlugin.getInstance().getConfiguration().prefix)));
        playerMock.assertNoMoreSaid();
    }

    @Test
    public void onPlayerInteract__Can_Change_Spawners() {
        TestTeam team = new TeamBuilder().withPermission(1, PermissionType.SPAWNERS, true).build();
        PlayerMock playerMock = new UserBuilder(serverMock).withTeam(team).build();
        TeamManager.teamViaLocation = Optional.of(team);
        CreatureSpawnerMock spawnerMock = new CreatureSpawnerMock();
        BlockMock blockMock = new BlockMock(Material.SPAWNER);
        blockMock.setState(spawnerMock);

        ItemStack itemStack = new ItemStack(Material.PIG_SPAWN_EGG);
        playerMock.getInventory().setItemInMainHand(itemStack);

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                playerMock,
                Action.RIGHT_CLICK_BLOCK,
                itemStack,
                blockMock,
                BlockFace.EAST
        );

        serverMock.getPluginManager().callEvent(playerInteractEvent);

        assertFalse(playerInteractEvent.isCancelled());
        playerMock.assertNoMoreSaid();
    }*/
}