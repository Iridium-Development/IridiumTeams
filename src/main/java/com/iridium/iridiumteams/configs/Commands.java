package com.iridium.iridiumteams.configs;

import com.iridium.iridiumteams.commands.*;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;

import java.util.Arrays;
import java.util.Collections;

public class Commands<T extends Team, U extends IridiumUser<T>> {
    public AboutCommand<T, U> aboutCommand;
    public CreateCommand<T, U> createCommand;
    public MembersCommand<T, U> membersCommand;
    public PermissionsCommand<T, U> permissionsCommand;
    public SetPermissionCommand<T, U> setPermissionCommand;
    public SettingsCommand<T, U> settingsCommand;
    public PromoteCommand<T, U> promoteCommand;
    public DemoteCommand<T, U> demoteCommand;
    public HelpCommand<T, U> helpCommand;
    public ReloadCommand<T, U> reloadCommand;
    public InviteCommand<T, U> inviteCommand;
    public UnInviteCommand<T, U> unInviteCommand;
    public InvitesCommand<T, U> invitesCommand;
    public TrustCommand<T, U> trustCommand;
    public UnTrustCommand<T, U> unTrustCommand;
    public TrustsCommand<T, U> trustsCommand;
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
    public BankCommand<T, U> bankCommand;
    public DepositCommand<T, U> depositCommand;
    public WithdrawCommand<T, U> withdrawCommand;
    public ChatCommand<T, U> chatCommand;
    public BoostersCommand<T, U> boostersCommand;
    public UpgradesCommand<T, U> upgradesCommand;
    public FlyCommand<T, U> flyCommand;
    public TopCommand<T, U> topCommand;
    public RecalculateCommand<T, U> recalculateCommand;
    public WarpsCommand<T, U> warpsCommand;
    public WarpCommand<T, U> warpCommand;
    public SetWarpCommand<T, U> setWarpCommand;
    public DeleteWarpCommand<T, U> deleteWarpCommand;
    public EditWarpCommand<T, U> editWarpCommand;
    public MissionsCommand<T, U> missionsCommand;
    public RewardsCommand<T, U> rewardsCommand;
    public ExperienceCommand<T, U> experienceCommand;
    public ShopCommand<T, U> shopCommand;

    public Commands() {
        this("iridiumteams", "Teams", "team");
    }

    public Commands(String permissionBase, String team, String commandBase) {
        aboutCommand = new AboutCommand<>(Collections.singletonList("about"), "View information about the plugin", "%prefix% &7/" + commandBase + " about", "", 0);
        createCommand = new CreateCommand<>(Collections.singletonList("create"), "Create a new" + team, "%prefix% &7/" + commandBase + " create <name>", "", 300);
        membersCommand = new MembersCommand<>(Collections.singletonList("members"), "View your " + team + " members", "%prefix% &7/" + commandBase + " members", "", 0);
        permissionsCommand = new PermissionsCommand<>(Collections.singletonList("permissions"), "Edit your " + team + " permissions", "%prefix% &7/" + commandBase + " permissions", "", 0);
        setPermissionCommand = new SetPermissionCommand<>(Collections.singletonList("setpermission"), "Set your " + team + " permissions", "%prefix% &7/" + commandBase + " setpermission <permission> <rank> (true/false)", "", 0);
        settingsCommand = new SettingsCommand<>(Collections.singletonList("settings"), "Set your " + team + " settings", "%prefix% &7/" + commandBase + " settings <setting> <value>", "", 0);
        promoteCommand = new PromoteCommand<>(Collections.singletonList("promote"), "Promote a member of your " + team, "%prefix% &7/" + commandBase + " promote <player>", "", 0);
        demoteCommand = new DemoteCommand<>(Collections.singletonList("demote"), "Demote a member of your " + team, "%prefix% &7/" + commandBase + " demote <player>", "", 0);
        helpCommand = new HelpCommand<>(Collections.singletonList("help"), "Show all the plugin commands", "%prefix% &7/" + commandBase + " help", "", 0);
        reloadCommand = new ReloadCommand<>(Collections.singletonList("reload"), "Reload the plugin's configurations", "%prefix% &7/" + commandBase + " reload", permissionBase + ".reload", 0);
        inviteCommand = new InviteCommand<>(Collections.singletonList("invite"), "Invite a player to your " + team, "%prefix% &7/" + commandBase + " invite <player>", "", 0);
        unInviteCommand = new UnInviteCommand<>(Collections.singletonList("uninvite"), "Revoke a player's invitation to your " + team, "%prefix% &7/" + commandBase + " uninvite <player>", "", 0);
        invitesCommand = new InvitesCommand<>(Collections.singletonList("invites"), "View all active invites to your " + team, "%prefix% &7/" + commandBase + " invites", "", 0);
        trustCommand = new TrustCommand<>(Collections.singletonList("trust"), "Trust a player in your " + team, "%prefix% &7/" + commandBase + " trust <player>", "", 0);
        unTrustCommand = new UnTrustCommand<>(Collections.singletonList("untrust"), "Revoke a player's trust in your " + team, "%prefix% &7/" + commandBase + " untrust <player>", "", 0);
        trustsCommand = new TrustsCommand<>(Collections.singletonList("trusts"), "View all active trusted members for your " + team, "%prefix% &7/" + commandBase + " trusts", "", 0);
        kickCommand = new KickCommand<>(Collections.singletonList("kick"), "Kick a player from your " + team, "%prefix% &7/" + commandBase + " kick <player>", "", 0);
        leaveCommand = new LeaveCommand<>(Collections.singletonList("leave"), "Leave your current " + team, "%prefix% &7/" + commandBase + " leave", "", 0);
        deleteCommand = new DeleteCommand<>(Collections.singletonList("delete"), "Delete your " + team, "%prefix% &7/" + commandBase + " delete (player)", "", 0, permissionBase + ".delete.others");
        infoCommand = new InfoCommand<>(Arrays.asList("info", "level", "value"), "View information about a " + team, "%prefix% &7/" + commandBase + " info <" + team.toLowerCase() + ">", "", 0);
        descriptionCommand = new DescriptionCommand<>(Collections.singletonList("description"), "Set your " + team + " description.", "%prefix% &7/" + commandBase + " description (" + team + ") <description>", "", 0, permissionBase + ".description.others");
        renameCommand = new RenameCommand<>(Collections.singletonList("rename"), "Rename your " + team, "%prefix% &7/" + commandBase + " rename (" + team + ") <name>", "", 0, permissionBase + ".rename.others");
        setHomeCommand = new SetHomeCommand<>(Collections.singletonList("sethome"), "Set your " + team + "'s home", "%prefix% &7/" + commandBase + " sethome", "", 0);
        homeCommand = new HomeCommand<>(Collections.singletonList("home"), "Teleport to your " + team + "'s home", "%prefix% &7/" + commandBase + " home", "", 0);
        bypassCommand = new BypassCommand<>(Collections.singletonList("bypass"), "Bypass " + team + " restrictions", "%prefix% &7/" + commandBase + " bypass", permissionBase + ".bypass", 0);
        transferCommand = new TransferCommand<>(Collections.singletonList("transfer"), "Transfer " + team + " ownership to another player", "%prefix% &7/" + commandBase + " transfer <player>", "", 0);
        joinCommand = new JoinCommand<>(Collections.singletonList("join"), "Join a " + team, "%prefix% &7/" + commandBase + " join", "", 0);
        bankCommand = new BankCommand<>(Collections.singletonList("bank"), "View your " + team + " bank", "%prefix% &7/" + commandBase + " bank <give/set/remove> <player> <item> <amount>", "", 0, permissionBase + ".bank.modify");
        depositCommand = new DepositCommand<>(Collections.singletonList("deposit"), "Deposit into your " + team + " bank", "%prefix% &7/" + commandBase + " deposit <name> <amount>", "", 0);
        withdrawCommand = new WithdrawCommand<>(Collections.singletonList("withdraw"), "Withdraw from your " + team + " bank", "%prefix% &7/" + commandBase + " withdraw <name> <amount>", "", 0);
        chatCommand = new ChatCommand<>(Arrays.asList("chat", "c"), "Change your " + team + " chat type", "%prefix% &7/" + commandBase + " chat <chattype>", "", 0);
        boostersCommand = new BoostersCommand<>(Collections.singletonList("boosters"), "View your " + team + " Boosters", "%prefix% &7/" + commandBase + " boosters buy <booster>", "", 0);
        upgradesCommand = new UpgradesCommand<>(Collections.singletonList("upgrades"), "View your " + team + " Upgrades", "%prefix% &7/" + commandBase + " upgrades buy <upgrade>", "", 0);
        flyCommand = new FlyCommand<>(Collections.singletonList("fly"), "Toggle your ability to fly", "%prefix% &7/ " + commandBase + " fly", permissionBase + ".fly", 0);
        topCommand = new TopCommand<>(Collections.singletonList("top"), "View the top " + team + "'s", "%prefix% &7/" + commandBase + " top", "", 0);
        recalculateCommand = new RecalculateCommand<>(Collections.singletonList("recalculate"), "Recalculate all " + team + "'s value ", "%prefix% &7/" + commandBase + " recalculate", permissionBase + ".recalculate", 0);
        warpsCommand = new WarpsCommand<>(Collections.singletonList("warps"), "View your " + team + "'s warps", "%prefix% &7/" + commandBase + " warps", "", 0);
        warpCommand = new WarpCommand<>(Collections.singletonList("warp"), "Teleport to your " + team + "'s warps", "%prefix% &7/" + commandBase + " warp <name> (password)", "", 0);
        setWarpCommand = new SetWarpCommand<>(Collections.singletonList("setwarp"), "Create a " + team + " warp", "%prefix% &7/" + commandBase + " setwarp", "", 0);
        deleteWarpCommand = new DeleteWarpCommand<>(Arrays.asList("deletewarp", "delwarp"), "Delete a " + team + " warp", "%prefix% &7/" + commandBase + " deletewarp", "", 0);
        editWarpCommand = new EditWarpCommand<>(Collections.singletonList("editwarp"), "Edit a " + team + " warp", "%prefix% &7/" + commandBase + " editwarp <warp> <icon/description> <value>", "", 0);
        missionsCommand = new MissionsCommand<>(Collections.singletonList("missions"), "View your " + team + " missions", "%prefix% &7/" + commandBase + " missions", "", 0);
        rewardsCommand = new RewardsCommand<>(Collections.singletonList("rewards"), "View your " + team + " rewards", "%prefix% &7/" + commandBase + " rewards", "", 0);
        experienceCommand = new ExperienceCommand<>(Collections.singletonList("experience"), "View your " + team + " experience", "%prefix% &7/" + commandBase + " experience <give/set/remove> <player> <amount>", "", 0, permissionBase + ".experience.modify");
        shopCommand = new ShopCommand<>(Collections.singletonList("shop"), "Open the Shop", "%prefix% &7/" + commandBase + " shop", "", 0);
    }
}
