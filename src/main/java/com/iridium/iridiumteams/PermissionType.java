package com.iridium.iridiumteams;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PermissionType {
    BLOCK_BREAK("blockBreak"),
    BLOCK_PLACE("blockPlace"),
    BUCKET("bucket"),
    CHANGE_PERMISSIONS("changePermissions"),
    CLAIM("claim"),
    DEMOTE("demote"),
    DESCRIPTION("description"),
    DOORS("doors"),
    INVITE("invite"),
    TRUST("trust"),
    KICK("kick"),
    KILL_MOBS("killMobs"),
    OPEN_CONTAINERS("openContainers"),
    PROMOTE("promote"),
    REDSTONE("redstone"),
    RENAME("rename"),
    SETHOME("sethome"),
    MANAGE_WARPS("managewarps"),
    SPAWNERS("spawners"),
    SETTINGS("settings");


    private final String permissionKey;
}
