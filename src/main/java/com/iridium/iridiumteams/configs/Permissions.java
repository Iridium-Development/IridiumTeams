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
    public Permission kick;
    public Permission killMobs;
    public Permission openContainers;
    public Permission promote;
    public Permission redstone;
    public Permission rename;
    public Permission setHome;
    public Permission spawners;
    public Permission unclaim;
    public Permission manageWarps;

    public Permissions() {
        this("Team");
    }

    public Permissions(String team) {
        this.allowed = "&a&lALLOWED";
        this.denied = "&c&lDENIED";
        this.blockBreak = new Permission(new Item(XMaterial.DIAMOND_PICKAXE, 10, 1, "&cBreak Blocks", Arrays.asList("&7Grant the ability to break any blocks in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 1);
        this.blockPlace = new Permission(new Item(XMaterial.COBBLESTONE, 11, 1, "&cPlace Blocks", Arrays.asList("&7Grant the ability to place any blocks in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 1);
        this.bucket = new Permission(new Item(XMaterial.BUCKET, 12, 1, "&cUse Buckets", Arrays.asList("&7Grant the ability to fill and empty buckets in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 1);
        this.changePermissions = new Permission(new Item(XMaterial.SUNFLOWER, 13, 1, "&cChange Permissions", Arrays.asList("&7Grant the ability to edit " + team + " permissions.", "", "&c&lPermission", "%permission%")), 1, 3);
        this.claim = new Permission(new Item(XMaterial.IRON_AXE, 14, 1, "&cClaim Land", Arrays.asList("&7Grant the ability to claim land for your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 2);
        this.demote = new Permission(new Item(XMaterial.PLAYER_HEAD, 15, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmU5YWU3YTRiZTY1ZmNiYWVlNjUxODEzODlhMmY3ZDQ3ZTJlMzI2ZGI1OWVhM2ViNzg5YTkyYzg1ZWE0NiJ9fX0=", 1, "&cDemote Users", Arrays.asList("&7Grant the ability to demote users in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 3);
        this.description = new Permission(new Item(XMaterial.WRITABLE_BOOK, 16, 1, "&cChange Description", Arrays.asList("&7Grant the ability to change your " + team + " description.", "", "&c&lPermission", "%permission%")), 1, 3);
        this.doors = new Permission(new Item(XMaterial.OAK_DOOR, 19, 1, "&cUse Doors", Arrays.asList("&7Grant the ability to use doors or trapdoors in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 1);
        this.invite = new Permission(new Item(XMaterial.DIAMOND, 20, 1, "&cInvite Users", Arrays.asList("&7Grant the ability to invite " + team + " members.", "", "&c&lPermission", "%permission%")), 1, 2);
        this.kick = new Permission(new Item(XMaterial.IRON_BOOTS, 21, 1, "&cKick Users", Arrays.asList("&7Grant the ability to kick " + team + " members.", "", "&c&lPermission", "%permission%")), 1, 2);
        this.killMobs = new Permission(new Item(XMaterial.DIAMOND_SWORD, 22, 1, "&cKill Mobs", Arrays.asList("&7Grant the ability to kill mobs in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 1);
        this.openContainers = new Permission(new Item(XMaterial.CHEST, 23, 1, "&cOpen Containers", Arrays.asList("&7Grant the ability to open containers in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 1);
        this.promote = new Permission(new Item(XMaterial.PLAYER_HEAD, 24, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmFiYWQ5MjRiMjIzNzJiYzk2NmE2ZDUxN2QyZjFiOGI1N2ZkZDI2MmI0ZTA0ZjQ4MzUyZTY4M2ZmZjkyIn19fQ==", 1, "&cPromote Users", Arrays.asList("&7Grant the ability to promote users in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 3);
        this.redstone = new Permission(new Item(XMaterial.REDSTONE, 25, 1, "&cUse Redstone", Arrays.asList("&7Grant the ability to use buttons, levels, or pressure plates in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 1);
        this.rename = new Permission(new Item(XMaterial.PAPER, 28, 1, "&cRename " + team + "", Arrays.asList("&7Grant the ability to rename your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 3);
        this.setHome = new Permission(new Item(XMaterial.WHITE_BED, 29, 1, "&c" + team + " Home", Arrays.asList("&7Grant the ability to change your " + team + " home.", "", "&c&lPermission", "%permission%")), 1, 2);
        this.spawners = new Permission(new Item(XMaterial.SPAWNER, 30, 1, "&cBreak Spawners", Arrays.asList("&7Grant the ability to mine spawners in your " + team + ".", "", "&c&lPermission", "%permission%")), 1, 1);
        this.unclaim = new Permission(new Item(XMaterial.GRASS_BLOCK, 31, 1, "&cUn-Claim Land", Arrays.asList("&7Grant the ability to unclaim your " + team + " land.", "", "&c&lPermission", "%permission%")), 1, 2);
        this.manageWarps = new Permission(new Item(XMaterial.END_PORTAL_FRAME, 32, 1, "&cManage Warps", Arrays.asList("&7Grant the ability to create edit and delete " + team + " Warps.", "", "&c&lPermission", "%permission%")), 1, 2);
    }

}
