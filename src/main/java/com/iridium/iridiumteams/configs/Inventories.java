package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.fasterxml.annotation.JsonIgnore;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.configs.inventories.*;
import com.iridium.iridiumteams.missions.MissionType;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Inventories {
    @JsonIgnore
    private final Background background1 = new Background(ImmutableMap.<Integer, Item>builder().build());
    @JsonIgnore
    private final Background background2 = new Background(ImmutableMap.<Integer, Item>builder().put(9, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(10, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(11, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(12, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(13, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(14, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(15, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(16, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).put(17, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList())).build());
    public Item backButton;
    public SingleItemGUI rewardsGUI;
    public SingleItemGUI membersGUI;
    public SingleItemGUI invitesGUI;
    public SingleItemGUI trustsGUI;
    public NoItemGUI ranksGUI;
    public NoItemGUI permissionsGUI;
    public NoItemGUI settingsGUI;
    public NoItemGUI bankGUI;
    public Map<MissionType, NoItemGUI> missionGUI;
    public TopGUIConfig topGUI;
    public NoItemGUI boostersGUI;
    public NoItemGUI upgradesGUI;
    public NoItemGUI shopOverviewGUI;
    public NoItemGUI shopCategoryGUI;

    public SingleItemGUI warpsGUI;

    public MissionTypeSelectorInventoryConfig missionTypeSelectorGUI;
    public ConfirmationInventoryConfig confirmationGUI;
    public Item nextPage;
    public Item previousPage;

    public Inventories() {
        this("Team", "&c");
    }

    public Inventories(String team, String color) {
        backButton = new Item(XMaterial.NETHER_STAR, -5, 1, "&c&lBack", Collections.emptyList());
        rewardsGUI = new SingleItemGUI(54, "&7" + team + " Rewards", background1, new Item(XMaterial.SUNFLOWER, 53, 1, color + "&lClaim All!", Collections.emptyList()));

        membersGUI = new SingleItemGUI(0, "&7" + team + " Members", background1, new Item(XMaterial.PLAYER_HEAD, 0, 1, color + "&l%player_name%", "%player_name%", Arrays.asList(
                color + "Joined: &7%player_join%",
                color + "Rank: &7%player_rank%",
                "",
                color + "&l[!] &7Right Click to promote",
                color + "&l[!] &7Left click to demote/kick"
        )));

        invitesGUI = new SingleItemGUI(0, "&7" + team + " Invites", background1, new Item(XMaterial.PLAYER_HEAD, 0, 1, color + "&l%player_name%", "%player_name%", Arrays.asList(
                color + "Invited: &7%invite_time%",
                "",
                color + "&l[!] &7Left click to uninvite"
        )));

        trustsGUI = new SingleItemGUI(0, "&7" + team + " Trusts", background1, new Item(XMaterial.PLAYER_HEAD, 0, 1, color + "&l%player_name%", "%player_name%", Arrays.asList(
                color + "Trusted At: &7%trusted_time%",
                color + "Trusted By: &7%truster%",
                "",
                color + "&l[!] &7Left click to un-trust"
        )));
        ranksGUI = new NoItemGUI(27, "&7" + team + " Permissions", background1);
        permissionsGUI = new NoItemGUI(54, "&7" + team + " Permissions", background1);
        settingsGUI = new NoItemGUI(36, "&7" + team + " Settings", background1);
        bankGUI = new NoItemGUI(27, "&7" + team + " Bank", background2);
        missionGUI = new ImmutableMap.Builder<MissionType, NoItemGUI>()
                .put(MissionType.DAILY, new NoItemGUI(27, "&7Daily " + team + " Missions", background2))
                .put(MissionType.WEEKLY, new NoItemGUI(27, "&7Weekly " + team + " Missions", background2))
                .put(MissionType.INFINITE, new NoItemGUI(27, "&7" + team + " Missions", background2))
                .put(MissionType.ONCE, new NoItemGUI(45, "&7" + team + " Missions", background1))
                .build();
        boostersGUI = new NoItemGUI(27, "&7" + team + " Boosters", background2);
        upgradesGUI = new NoItemGUI(27, "&7" + team + " Upgrades", background2);
        shopOverviewGUI = new NoItemGUI(36, "&7Island Shop", background1);
        shopCategoryGUI = new NoItemGUI(0, "&7Island Shop | %category_name%", background1);
        warpsGUI = new SingleItemGUI(27, "&7" + team + " Warps", background2, new Item(
                XMaterial.GREEN_STAINED_GLASS_PANE, 1, "&b&l%warp_name%",
                Arrays.asList(
                        color + "Description: &7%warp_description%",
                        color + "Created By: &7%warp_creator%",
                        color + "Date: &7%warp_create_time%",
                        "",
                        "&b&l[!] &bLeft Click to Teleport",
                        "&b&l[!] &bRight Click to Delete"
                )));
        topGUI = new TopGUIConfig(54, "&7Top " + team, background1, new Item(XMaterial.PLAYER_HEAD, 1, color + "&l" + team + " Owner: &f%" + team.toLowerCase() + "_owner%", "%" + team.toLowerCase() + "_owner%", Arrays.asList(
                "",
                color + team + " Name: &7%" + team.toLowerCase() + "_name%",
                color + team + " Value: &7%" + team.toLowerCase() + "_value% (#%" + team.toLowerCase() + "_value_rank%)",
                color + team + " Experience: &7%" + team.toLowerCase() + "_experience% (#%" + team.toLowerCase() + "_experience_rank%)",
                color + "Netherite Blocks: &7%" + team.toLowerCase() + "_netherite_block_amount%",
                color + "Emerald Blocks: &7%" + team.toLowerCase() + "_emerald_block_amount%",
                color + "Diamond Blocks: &7%" + team.toLowerCase() + "_diamond_block_amount%",
                color + "Gold Blocks: &7%" + team.toLowerCase() + "_gold_block_amount%",
                color + "Iron Blocks: &7%" + team.toLowerCase() + "_iron_block_amount%",
                color + "Hopper Blocks: &7%" + team.toLowerCase() + "_hopper_amount%",
                color + "Beacon Blocks: &7%" + team.toLowerCase() + "_beacon_amount%"
        )), new Item(XMaterial.BARRIER, 1, " ", Collections.emptyList()));

        missionTypeSelectorGUI = new MissionTypeSelectorInventoryConfig(27, "&7" + team + " Missions", background2,
                new MissionTypeSelectorInventoryConfig.MissionTypeItem(new Item(XMaterial.IRON_INGOT, 11, 1, color + "&lDaily Missions", Collections.emptyList()), true),
                new MissionTypeSelectorInventoryConfig.MissionTypeItem(new Item(XMaterial.GOLD_INGOT, 13, 1, color + "&lWeekly Missions", Collections.emptyList()), true),
                new MissionTypeSelectorInventoryConfig.MissionTypeItem(new Item(XMaterial.IRON_INGOT, 11, 1, color + "&lInstant Missions", Collections.emptyList()), false),
                new MissionTypeSelectorInventoryConfig.MissionTypeItem(new Item(XMaterial.DIAMOND, 15, 1, color + "&lOne Time Missions", Collections.emptyList()), true)
        );

        confirmationGUI = new ConfirmationInventoryConfig(27, "&7Are you sure?", background2, new Item(XMaterial.GREEN_STAINED_GLASS_PANE, 15, 1, "&a&lYes", Collections.emptyList()), new Item(XMaterial.RED_STAINED_GLASS_PANE, 11, 1, color + "&lNo", Collections.emptyList()));

        nextPage = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&a&lNext Page", Collections.emptyList());
        previousPage = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, color + "&lPrevious Page", Collections.emptyList());
    }
}
