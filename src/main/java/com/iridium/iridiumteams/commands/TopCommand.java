package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.Placeholder;
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

import java.util.ArrayList;
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
        TeamSorting<T> sortingType = iridiumTeams.getSortingTypes().get(0);
        boolean excludePrivate = !sender.hasPermission(adminPermission);

        if (sender instanceof Player && arguments.length == 0) return sendGUI((Player) sender, iridiumTeams);

        switch (arguments.length) {
            case 3: {
                try {
                    listLength = Math.min(Integer.parseInt(arguments[2]), 100);
                } catch (NumberFormatException ignored) {}
            }
            case 2: {
                for(TeamSorting<T> pluginSortingType : iridiumTeams.getSortingTypes()) {
                    if (arguments[1].equalsIgnoreCase(pluginSortingType.getName())) sortingType = pluginSortingType;
                }
            }
            case 1: {
                if (!arguments[0].equalsIgnoreCase("list")) {
                    sender.sendMessage(StringUtils.color(syntax.replace("prefix", iridiumTeams.getConfiguration().prefix)));
                    return false;
                }
            }
            default: {
                sendList(sender, iridiumTeams, sortingType, listLength, excludePrivate);
                return true;
            }
        }
    }

     public boolean sendGUI(Player player, IridiumTeams<T, U> iridiumTeams) {
         player.openInventory(new TopGUI<>(iridiumTeams.getTop().valueTeamSort, player, iridiumTeams).getInventory());
         return true;
    }

    public void sendList(CommandSender sender, IridiumTeams<T, U> iridiumTeams, TeamSorting<T> sortingType, int listLength, boolean excludePrivate) {
        List<T> teamList = iridiumTeams.getTeamManager().getTeams(sortingType, excludePrivate);

        sender.sendMessage(StringUtils.color(StringUtils.getCenteredMessage(iridiumTeams.getMessages().topCommandHeader.replace("%sort_type%", sortingType.getName()), iridiumTeams.getMessages().topCommandFiller)));

        for (int i = 0; i < listLength;  i++) {
            if(i == sortingType.getSortedTeams(iridiumTeams).size()) break;
            T team = teamList.get(i);
            List<Placeholder> placeholders = iridiumTeams.getTeamsPlaceholderBuilder().getPlaceholders(team);
            placeholders.add(new Placeholder("value", iridiumTeams.getConfiguration().numberFormatter.format(sortingType.getValue(team))));
            placeholders.add(new Placeholder("rank", String.valueOf(i+1)));

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
            placeholders.add(new Placeholder("color", color));


            sender.sendMessage(StringUtils.color(StringUtils.processMultiplePlaceholders(iridiumTeams.getMessages().topCommandMessage, placeholders)));
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
                return iridiumTeams.getSortingTypes().stream().map(TeamSorting::getName).collect(Collectors.toList());
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
