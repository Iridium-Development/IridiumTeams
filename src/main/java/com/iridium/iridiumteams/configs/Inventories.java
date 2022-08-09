package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.fasterxml.annotation.JsonIgnore;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.configs.inventories.ConfirmationInventoryConfig;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.configs.inventories.SingleItemGUI;
import com.iridium.iridiumteams.configs.inventories.TopGUIConfig;

import java.util.Arrays;
import java.util.Collections;

public class Inventories {
    @JsonIgnore
    private final Background background1 = new Background(ImmutableMap.<Integer, Item>builder().build());
    @JsonIgnore
    private final Background background2 = new Background(ImmutableMap.<Integer, Item>builder().put(9, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(10, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(11, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(12, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(13, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(14, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(15, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(16, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(17, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).build());
    public Item backButton;
    public SingleItemGUI membersGUI;
    public SingleItemGUI invitesGUI;
    public NoItemGUI ranksGUI;
    public NoItemGUI permissionsGUI;
    public NoItemGUI bankGUI;
    public TopGUIConfig topGUI;
    public NoItemGUI boostersGUI;
    public NoItemGUI upgradesGUI;

    public SingleItemGUI warpsGUI;
    public ConfirmationInventoryConfig confirmationGUI;
    public Item nextPage;
    public Item previousPage;

    public Inventories() {
        this("Team", "&c");
    }

    public Inventories(String team, String color) {
        backButton = new Item(XMaterial.NETHER_STAR, -5, 1, "&c&lBack", Collections.emptyList());

        membersGUI = new SingleItemGUI(0, "&7" + team + " Members", background1, new Item(XMaterial.PLAYER_HEAD, 0, 1, color + "&l%player_name%", "%player_name%", Arrays.asList("&7Joined: %player_join%", "&7Rank: %player_rank%", "", color + "&l[!] &7Right Click to promote", color + "&l[!] &7Left click to demote/kick")));

        invitesGUI = new SingleItemGUI(0, "&7" + team + " Invites", background1, new Item(XMaterial.PLAYER_HEAD, 0, 1, color + "&l%player_name%", "%player_name%", Arrays.asList("", color + "&l[!] &7Left click to uninvite")));
        ranksGUI = new NoItemGUI(27, "&7" + team + " Permissions", background1);
        permissionsGUI = new NoItemGUI(54, "&7" + team + " Permissions", background1);
        bankGUI = new NoItemGUI(27, "&7" + team + " Bank", background2);
        boostersGUI = new NoItemGUI(27, "&7" + team + " Boosters", background2);
        upgradesGUI = new NoItemGUI(27, "&7" + team + " Upgrades", background2);
        warpsGUI = new SingleItemGUI(27, "&7" + team + " Warps", background2, new Item(
                XMaterial.GREEN_STAINED_GLASS_PANE, 1, "&b&l%warp_name%",
                Arrays.asList(
                        "&7Description: %warp_description%",
                        "&7Created By: %warp_creator%",
                        "&7Date: %warp_create_time%",
                        "",
                        "&b&l[!] &bLeft Click to Teleport",
                        "&b&l[!] &bRight Click to Delete"
                )));
        topGUI = new TopGUIConfig(54, "&7Top " + team, background1, new Item(XMaterial.PLAYER_HEAD, 1, color + "&l" + team + " Owner: &f%" + team.toLowerCase() + "_owner%", "%" + team.toLowerCase() + "_owner%", Arrays.asList(
                "",
                color + "&l * &7" + team + " Name: " + color + "%" + team.toLowerCase() + "_name%",
                color + "&l * &7" + team + " Value: " + color + "%" + team.toLowerCase() + "_value% (#%" + team.toLowerCase() + "_value_rank%)",
                color + "&l * &7" + team + " Experience: " + color + "%" + team.toLowerCase() + "_experience% (#%" + team.toLowerCase() + "_experience_rank%)",
                color + "&l * &7Netherite Blocks: " + color + "%NETHERITE_BLOCK_AMOUNT%",
                color + "&l * &7Emerald Blocks: " + color + "%EMERALD_BLOCK_AMOUNT%",
                color + "&l * &7Diamond Blocks: " + color + "%DIAMOND_BLOCK_AMOUNT%",
                color + "&l * &7Gold Blocks: " + color + "%GOLD_BLOCK_AMOUNT%",
                color + "&l * &7Iron Blocks: " + color + "%IRON_BLOCK_AMOUNT%",
                color + "&l * &7Hopper Blocks: " + color + "%HOPPER_AMOUNT%",
                color + "&l * &7Beacon Blocks: " + color + "%BEACON_AMOUNT%"
        )), new Item(XMaterial.BARRIER, 1, " ", Collections.emptyList()));

        confirmationGUI = new ConfirmationInventoryConfig(27, "&7Are you sure?", background2, new Item(XMaterial.GREEN_STAINED_GLASS_PANE, 15, 1, "&a&lYes", Collections.emptyList()), new Item(XMaterial.RED_STAINED_GLASS_PANE, 11, 1, color + "&lNo", Collections.emptyList()));

        nextPage = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&a&lNext Page", Collections.emptyList());
        previousPage = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, color + "&lPrevious Page", Collections.emptyList());
    }
}
