package com.iridium.iridiumteams.configs;

import com.iridium.iridiumteams.commands.*;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;

import java.util.Collections;

public class Commands<T extends Team, U extends IridiumUser<T>> {
    public AboutCommand<T, U> aboutCommand;
    public CreateCommand<T, U> createCommand;
    public MembersCommand<T, U> membersCommand;
    public PermissionsCommand<T, U> permissionsCommand;
    public SetPermissionCommand<T, U> setPermissionCommand;
    public PromoteCommand<T, U> promoteCommand;
    public DemoteCommand<T, U> demoteCommand;
    public HelpCommand<T, U> helpCommand;
    public ReloadCommand<T, U> reloadCommand;
    public InviteCommand<T, U> inviteCommand;
    public UnInviteCommand<T, U> unInviteCommand;
    public InvitesCommand<T, U> invitesCommand;
    public KickCommand<T, U> kickCommand;
    public LeaveCommand<T, U> leaveCommand;
    public DeleteCommand<T, U> deleteCommand;
    public InfoCommand<T, U> infoCommand;
    public DescriptionCommand<T, U> descriptionCommand;
    public RenameCommand<T, U> renameCommand;
    public SetHomeCommand<T, U> setHomeCommand;
    public HomeCommand<T, U> homeCommand;
    public BypassCommand<T, U> bypassCommand;
    public TransferCommand<T, U> transferCommand;
    public JoinCommand<T, U> joinCommand;

    public Commands() {
        this("iridiumteams", "Teams", "team");
    }

    public Commands(String permissionBase, String team, String commandBase) {
        aboutCommand = new AboutCommand<>(Collections.singletonList("about"), "View information about the plugin", "%prefix% /" + commandBase + " about", "");
        createCommand = new CreateCommand<>(Collections.singletonList("create"), "Create a new" + team, "%prefix% /" + commandBase + " create <name>", "");
        membersCommand = new MembersCommand<>(Collections.singletonList("members"), "View your " + team + " members", "%prefix% /" + commandBase + " members", "");
        permissionsCommand = new PermissionsCommand<>(Collections.singletonList("permissions"), "Edit your " + team + " permissions", "%prefix% /" + commandBase + " permissions", "");
        setPermissionCommand = new SetPermissionCommand<>(Collections.singletonList("setpermission"), "Set your " + team + " permissions", "%prefix% /" + commandBase + " setpermission <permission> <rank> (true/false)", "");
        promoteCommand = new PromoteCommand<>(Collections.singletonList("promote"), "Promote a member of your " + team, "%prefix% /" + commandBase + " promote <player>", "");
        demoteCommand = new DemoteCommand<>(Collections.singletonList("demote"), "Demote a member of your " + team, "%prefix% /" + commandBase + " demote <player>", "");
        helpCommand = new HelpCommand<>(Collections.singletonList("help"), "Show all the plugin commands", "%prefix% /" + commandBase + " help", "");
        reloadCommand = new ReloadCommand<>(Collections.singletonList("reload"), "Reload the plugin's configurations", "%prefix% /" + commandBase + " reload", permissionBase + ".reload");
        inviteCommand = new InviteCommand<>(Collections.singletonList("invite"), "Invite a player to your " + team, "%prefix% /" + commandBase + " invite <player>", "");
        unInviteCommand = new UnInviteCommand<>(Collections.singletonList("uninvite"), "Revoke a player's invitation to your " + team, "%prefix% /" + commandBase + " uninvite <player>", "");
        invitesCommand = new InvitesCommand<>(Collections.singletonList("invites"), "View all active invites to your " + team, "%prefix% /" + commandBase + " invites", "");
        kickCommand = new KickCommand<>(Collections.singletonList("kick"), "Kick a player from your " + team, "%prefix% /" + commandBase + " kick <player>", "");
        leaveCommand = new LeaveCommand<>(Collections.singletonList("leave"), "Leave your current " + team, "%prefix% /" + commandBase + " leave", "");
        deleteCommand = new DeleteCommand<>(Collections.singletonList("delete"), "Delete your " + team, "%prefix% /" + commandBase + " delete", "");
        infoCommand = new InfoCommand<>(Collections.singletonList("info"), "View information about a " + team, "%prefix% /" + commandBase + " info <" + team.toLowerCase() + ">", "");
        descriptionCommand = new DescriptionCommand<>(Collections.singletonList("description"), "Edit your " + team + " description.", "%prefix% /" + commandBase + " description", "");
        renameCommand = new RenameCommand<>(Collections.singletonList("rename"), "Rename your " + team, "%prefix% /" + commandBase + " rename <name>", "");
        setHomeCommand = new SetHomeCommand<>(Collections.singletonList("sethome"), "Set your " + team + "'s home", "%prefix% /" + commandBase + " sethome", "");
        homeCommand = new HomeCommand<>(Collections.singletonList("home"), "Teleport to your " + team + "'s home", "%prefix% /" + commandBase + " home", "");
        bypassCommand = new BypassCommand<>(Collections.singletonList("bypass"), "Bypass " + team + " restrictions", "%prefix% /" + commandBase + " bypass", permissionBase + ".bypass");
        transferCommand = new TransferCommand<>(Collections.singletonList("transfer"), "Transfer " + team + " ownership to another player", "%prefix% /" + commandBase + " transfer <player>", "");
        joinCommand = new JoinCommand<>(Collections.singletonList("join"), "Join a " + team, "%prefix% /" + commandBase + " join", "");
    }
}
