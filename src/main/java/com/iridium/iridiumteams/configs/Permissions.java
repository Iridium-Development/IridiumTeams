package com.iridium.iridiumteams.configs;

import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumteams.Permission;

import java.util.Arrays;

public class Permissions {

    public String allowed;
    public String denied;
    public Permission blockBreak;
    public Permission blockPlace;
    public Permission bucket;
    public Permission changePermissions;
    public Permission claim;
    public Permission demote;
    public Permission description;
    public Permission doors;
    public Permission invite;
    public Permission trust;
    public Permission kick;
    public Permission killMobs;
    public Permission openContainers;
    public Permission promote;
    public Permission redstone;
    public Permission rename;
    public Permission setHome;
    public Permission spawners;
    public Permission settings;
    public Permission manageWarps;

    public Permissions() {
        this("Team", "&c");
    }

    public Permissions(String team, String color) {
        this.allowed = "&a&lALLOWED";
        this.denied = "&c&lDENIED";
        this.blockBreak = new Permission(new Item(XMaterial.DIAMOND_PICKAXE, 10, 1, "" + color + "Break Blocks", Arrays.asList("&7Grant the ability to break any blocks in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 1);
        this.blockPlace = new Permission(new Item(XMaterial.COBBLESTONE, 11, 1, "" + color + "Place Blocks", Arrays.asList("&7Grant the ability to place any blocks in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 1);
        this.bucket = new Permission(new Item(XMaterial.BUCKET, 12, 1, "" + color + "Use Buckets", Arrays.asList("&7Grant the ability to fill and empty buckets in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 1);
        this.changePermissions = new Permission(new Item(XMaterial.SUNFLOWER, 13, 1, "" + color + "Change Permissions", Arrays.asList("&7Grant the ability to edit " + team + " permissions.", "", "" + color + "&lPermission", "%permission%")), 1, 3);
        this.claim = new Permission(new Item(XMaterial.IRON_AXE, 14, 1, "" + color + "Claim Land", Arrays.asList("&7Grant the ability to claim land for your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 2);
        this.demote = new Permission(new Item(XMaterial.PLAYER_HEAD, 15, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmU5YWU3YTRiZTY1ZmNiYWVlNjUxODEzODlhMmY3ZDQ3ZTJlMzI2ZGI1OWVhM2ViNzg5YTkyYzg1ZWE0NiJ9fX0=", 1, "" + color + "Demote Users", Arrays.asList("&7Grant the ability to demote users in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 3);
        this.description = new Permission(new Item(XMaterial.WRITABLE_BOOK, 16, 1, "" + color + "Change Description", Arrays.asList("&7Grant the ability to change your " + team + " description.", "", "" + color + "&lPermission", "%permission%")), 1, 3);
        this.doors = new Permission(new Item(XMaterial.OAK_DOOR, 19, 1, "" + color + "Use Doors", Arrays.asList("&7Grant the ability to use doors or trapdoors in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 1);
        this.invite = new Permission(new Item(XMaterial.DIAMOND, 20, 1, "" + color + "Invite Users", Arrays.asList("&7Grant the ability to invite " + team + " members.", "", "" + color + "&lPermission", "%permission%")), 1, 2);
        this.trust = new Permission(new Item(XMaterial.EMERALD, 21, 1, "" + color + "Trust Users", Arrays.asList("&7Grant the ability to trust members in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 2);
        this.kick = new Permission(new Item(XMaterial.IRON_BOOTS, 22, 1, "" + color + "Kick Users", Arrays.asList("&7Grant the ability to kick " + team + " members.", "", "" + color + "&lPermission", "%permission%")), 1, 2);
        this.killMobs = new Permission(new Item(XMaterial.DIAMOND_SWORD, 23, 1, "" + color + "Kill Mobs", Arrays.asList("&7Grant the ability to kill mobs in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 1);
        this.openContainers = new Permission(new Item(XMaterial.CHEST, 24, 1, "" + color + "Open Containers", Arrays.asList("&7Grant the ability to open containers in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 1);
        this.promote = new Permission(new Item(XMaterial.PLAYER_HEAD, 25, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmFiYWQ5MjRiMjIzNzJiYzk2NmE2ZDUxN2QyZjFiOGI1N2ZkZDI2MmI0ZTA0ZjQ4MzUyZTY4M2ZmZjkyIn19fQ==", 1, "" + color + "Promote Users", Arrays.asList("&7Grant the ability to promote users in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 3);
        this.redstone = new Permission(new Item(XMaterial.REDSTONE, 28, 1, "" + color + "Use Redstone", Arrays.asList("&7Grant the ability to use buttons, levels, or pressure plates in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 1);
        this.rename = new Permission(new Item(XMaterial.PAPER, 29, 1, "" + color + "Rename " + team + "", Arrays.asList("&7Grant the ability to rename your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 3);
        this.setHome = new Permission(new Item(XMaterial.WHITE_BED, 30, 1, "" + color + "" + team + " Home", Arrays.asList("&7Grant the ability to change your " + team + " home.", "", "" + color + "&lPermission", "%permission%")), 1, 2);
        this.spawners = new Permission(new Item(XMaterial.SPAWNER, 31, 1, "" + color + "Break Spawners", Arrays.asList("&7Grant the ability to mine spawners in your " + team + ".", "", "" + color + "&lPermission", "%permission%")), 1, 1);
        this.settings = new Permission(new Item(XMaterial.GUNPOWDER, 32, 1, "" + color + "Change Settings", Arrays.asList("&7Grant the ability to change your " + team + " settings.", "", "" + color + "&lPermission", "%permission%")), 1, 3);
        this.manageWarps = new Permission(new Item(XMaterial.END_PORTAL_FRAME, 33, 1, "" + color + "Manage Warps", Arrays.asList("&7Grant the ability to create edit and delete " + team + " Warps.", "", "" + color + "&lPermission", "%permission%")), 1, 2);
    }

}
