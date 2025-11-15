package com.iridium.iridiumteams.configs;

// Note that when saving this, %user% and %otherUser% will be automatically looked up so the Usernames are always the latest (In case of name changes)
// %user% should always be the person doing the action (the one who issued the command)
public class TeamLogs {
    public String teamCreateLogDescription = "%user% has created your island";
    public String teamInviteLogDescription = "%user% invited %otherUser% to join your island";
    public String teamUnInviteLogDescription = "%user% un-invited %otherUser% to join your island";
    public String teamJoinLogDescription = "%user% joined your island";
    public String teamLeaveLogDescription = "%user% left your island";
    public String teamPromotionLogDescription = "%user% has promoted %otherUser% to %rank%";
    public String teamDemotionLogDescription = "%user% has demoted %otherUser% to %rank%";
    public String teamKickLogDescription = "%user% has kicked %otherUser% from your island";
    public String teamTransferLogDescription = "%user% has transferred island ownership to %otherUser%";
    public String teamBankDepositDescription = "%user% has deposited %amount% %type% to your island bank";
    public String teamBankWithdrawDescription = "%user% has withdrew %amount% %type% from your island bank";
    public String teamUpgradePurchaseDescription = "%user% has purchased %upgrade_type% Upgrade Level %level%";
    public String teamBoosterPurchaseDescription = "%user% has purchased %booster_type% Booster Level %level%";
    public String teamTrustDescription = "%user% has trusted %otherUser% on your island";
    public String teamUnTrustDescription = "%user% has un-trusted %otherUser% on your island";
    public String teamWarpCreateDescription = "%user% has created a new Island warp %warp%";
    public String teamWarpEditDescription = "%user% has edited the Island warp %warp%'s %type%";
    public String teamWarpDeleteDescription = "%user% has deleted the Island warp %warp%";
    public String teamSettingsDescription = "%user% has set the TeamSetting %setting% to %value%";
}
