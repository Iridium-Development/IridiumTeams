package com.iridium.iridiumteams.configs;

import com.iridium.iridiumteams.commands.*;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;

public class Commands<T extends Team, U extends IridiumUser<T>> {
    public AboutCommand<T, U> aboutCommand = new AboutCommand<>();
    public CreateCommand<T, U> createCommand = new CreateCommand<>();
    public MembersCommand<T, U> membersCommand = new MembersCommand<>();
    public PermissionsCommand<T, U> permissionsCommand = new PermissionsCommand<>();
    public SetPermissionCommand<T, U> setPermissionCommand = new SetPermissionCommand<>();
    public PromoteCommand<T, U> promoteCommand = new PromoteCommand<>();
    public DemoteCommand<T, U> demoteCommand = new DemoteCommand<>();
    public HelpCommand<T, U> helpCommand = new HelpCommand<>();
    public ReloadCommand<T, U> reloadCommand = new ReloadCommand<>();
    public InviteCommand<T, U> inviteCommand = new InviteCommand<>();
    public UnInviteCommand<T, U> unInviteCommand = new UnInviteCommand<>();
    public InvitesCommand<T, U> invitesCommand = new InvitesCommand<>();
    public KickCommand<T, U> kickCommand = new KickCommand<>();
    public LeaveCommand<T, U> leaveCommand = new LeaveCommand<>();
    public DeleteCommand<T, U> deleteCommand = new DeleteCommand<>();
    public InfoCommand<T, U> infoCommand = new InfoCommand<>();
    public DescriptionCommand<T, U> descriptionCommand = new DescriptionCommand<>();
    public RenameCommand<T, U> renameCommand = new RenameCommand<>();
    public SetHomeCommand<T, U> setHomeCommand = new SetHomeCommand<>();
    public HomeCommand<T, U> homeCommand = new HomeCommand<>();
    public BypassCommand<T, U> bypassCommand = new BypassCommand<>();
    public TransferCommand<T, U> transferCommand = new TransferCommand<>();
}
