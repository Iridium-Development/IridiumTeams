package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.TopGUI;
import com.iridium.iridiumteams.sorting.TeamSorting;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TopCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public String adminPermission;

    public TopCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds, String adminPermission) {
        super(args, description, syntax, permission, cooldownInSeconds);
        this.adminPermission = adminPermission;
    }

    @Override
    public boolean execute(CommandSender sender, String[] arguments, IridiumTeams<T, U> iridiumTeams) {

        int listLength = 10;
        boolean showValue = true;
        boolean excludePrivate = !sender.hasPermission(adminPermission);

        if (sender instanceof Player && arguments.length == 0) return sendGUI((Player) sender, iridiumTeams);

        switch (arguments.length) {
            case 3: {
                try {
                    listLength = Integer.parseInt(arguments[2]);
                } catch (NumberFormatException ignored) {}
            }
            case 2: {
                if (arguments[1].equalsIgnoreCase("experience")) showValue = false;
            }
            case 1: {
                if (!arguments[0].equalsIgnoreCase("list")) {
                    sender.sendMessage(StringUtils.color(syntax.replace("prefix", iridiumTeams.getConfiguration().prefix)));
                    return false;
                }
            }
            default: {
                sendList(sender, iridiumTeams, showValue, listLength, excludePrivate);
                return true;
            }
        }
    }

     public boolean sendGUI(Player player, IridiumTeams<T, U> iridiumTeams) {
         player.openInventory(new TopGUI<>(iridiumTeams.getTop().valueTeamSort, player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
         return true;
    }

    public void sendList(CommandSender sender, IridiumTeams<T, U> iridiumTeams, boolean showValue, int listLength, boolean excludePrivate) {

        List<T> teamList;
        if (showValue) teamList = iridiumTeams.getTeamManager().getTeams(iridiumTeams.getTop().valueTeamSort, excludePrivate);
        else teamList = iridiumTeams.getTeamManager().getTeams(iridiumTeams.getTop().experienceTeamSort, excludePrivate);

        String sortingType = iridiumTeams.getMessages().topValue;
        if (!showValue) sortingType = iridiumTeams.getMessages().topExperience;

        sender.sendMessage(StringUtils.color(iridiumTeams.getMessages().topCommandHeader
                .replace("%sortType%", sortingType)));

        for (int i = 0; i < listLength;  i++) {
            if(i ==  teamList.size()) break;
            String teamOwner = iridiumTeams.getTeamManager().getTeamMembers(teamList.get(i)).stream()
                    .filter(user -> user.getUserRank() == Rank.OWNER.getId())
                    .findFirst()
                    .map(U::getName).get();

            double listedValue = teamList.get(i).getValue();
            if (!showValue) {
                listedValue = teamList.get(i).getExperience();
            }

            String color = "&7";
            switch(i) {
                case 0: {
                    color = iridiumTeams.getMessages().topFirstColor;
                    break;
                }
                case 1: {
                    color = iridiumTeams.getMessages().topSecondColor;
                    break;
                }
                case 2: {
                    color = iridiumTeams.getMessages().topThirdColor;
                    break;
                }
            }

            String teamID = "";
            if (!excludePrivate) teamID = "[" + teamList.get(i).getId() + "] ";

            sender.sendMessage(StringUtils.color(color + (i + 1)  + "&7: " + teamID + teamOwner + ": &a" + listedValue));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        if (!commandSender.hasPermission(adminPermission)) return Collections.singletonList("");
        switch(args.length) {
            case 1: {
                return Collections.singletonList("list");
            }
            case 2: {
                return Arrays.asList("value", "experience");
            }
            case 3: {
                return Collections.singletonList("10");
            }
            default: {
                return null;
            }
        }
    }
}
