package com.iridium.iridiumteams.configs;

import com.google.common.collect.ImmutableMap;
import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.fasterxml.annotation.JsonIgnore;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.configs.inventories.ConfirmationInventoryConfig;
import com.iridium.iridiumteams.configs.inventories.NoItemGUI;
import com.iridium.iridiumteams.configs.inventories.SingleItemGUI;

import java.util.Arrays;
import java.util.Collections;

public class Inventories {
    @JsonIgnore
    private final Background background1 = new Background(ImmutableMap.<Integer, Item>builder().build());
    @JsonIgnore
    private final Background background2 = new Background(ImmutableMap.<Integer, Item>builder()
            .put(9, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
            .put(10, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
            .put(11, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
            .put(12, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
            .put(13, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
            .put(14, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
            .put(15, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
            .put(16, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
            .put(17, new Item(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, 1, " ", Collections.emptyList()))
            .build());

    public SingleItemGUI membersGUI;
    public SingleItemGUI invitesGUI;
    public NoItemGUI ranksGUI;
    public NoItemGUI permissionsGUI;
    public ConfirmationInventoryConfig confirmationGUI;
    public Item nextPage;
    public Item previousPage;

    public Inventories() {
        this("Team", "&c");
    }

    public Inventories(String team, String color) {
        membersGUI = new SingleItemGUI(0, "&7" + team + " Members", background1, new Item(XMaterial.PLAYER_HEAD, 0, 1, color + "&l%player_name%", "%player_name%", Arrays.asList(
                "&7Joined: %player_join%",
                "&7Rank: %player_rank%",
                "",
                color + "&l[!] &7Right Click to promote",
                color + "&l[!] &7Left click to demote/kick"
        )));

        invitesGUI = new SingleItemGUI(0, "&7" + team + " Invites", background1, new Item(XMaterial.PLAYER_HEAD, 0, 1, color + "&l%player_name%", "%player_name%", Arrays.asList(
                "",
                color + "&l[!] &7Left click to uninvite"
        )));
        ranksGUI = new NoItemGUI(27, "&7" + team + " Permissions", background1);
        permissionsGUI = new NoItemGUI(54, "&7" + team + " Permissions", background1);

        confirmationGUI = new ConfirmationInventoryConfig(27, "&7Are you sure?", background2, new Item(XMaterial.GREEN_STAINED_GLASS_PANE, 15, 1, "&a&lYes", Collections.emptyList()), new Item(XMaterial.RED_STAINED_GLASS_PANE, 11, 1, color + "&lNo", Collections.emptyList()));

        nextPage = new Item(XMaterial.LIME_STAINED_GLASS_PANE, 1, "&a&lNext Page", Collections.emptyList());
        previousPage = new Item(XMaterial.RED_STAINED_GLASS_PANE, 1, color + "&lPrevious Page", Collections.emptyList());
    }
}
