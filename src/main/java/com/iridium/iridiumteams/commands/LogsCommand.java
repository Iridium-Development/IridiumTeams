package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamLog;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class LogsCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public LogsCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        // TODO figure out how to set these in a nice way?
        String actionFilter = null;
        String userFilter = null;

        iridiumTeams.getTeamManager().getTeamLogsMaxPage(team, 8, null, null).thenAccept(maxPage -> Bukkit.getScheduler().runTask(iridiumTeams, () -> {
            int page = 1;

            // Read optional page argument
            if (args.length != 0) {
                String pageArgument = args[0];
                if (pageArgument.matches("[0-9]+")) {
                    page = Integer.parseInt(pageArgument);
                }
            }

            // Correct requested page if it's out of bounds
            if (page > maxPage) {
                page = maxPage;
            } else if (page < 1) {
                page = 1;
            }

            int finalPage = page;
            iridiumTeams.getTeamManager().getTeamLogs(team, 8, page, null, null).thenAccept(teamLogs -> Bukkit.getScheduler().runTask(iridiumTeams, () -> {
                List<TeamLog> processedTeamLogs = iridiumTeams.getTeamManager().processTeamLogs(teamLogs);
                Player player = user.getPlayer();
                // Prepare the footer
                TextComponent footerText = new TextComponent(StringUtils.color(iridiumTeams.getTeamLogs().teamLogFooter
                        .replace("%page%", String.valueOf(finalPage))
                        .replace("%max_page%", String.valueOf(maxPage))
                ));
                TextComponent previousButton = new TextComponent(StringUtils.color(iridiumTeams.getTeamLogs().teamLogPreviousPage));
                TextComponent nextButton = new TextComponent(StringUtils.color(iridiumTeams.getTeamLogs().teamLogNextPage));
                if (finalPage != 1) {
                    previousButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + iridiumTeams.getCommandManager().getCommand() + " logs " + (finalPage - 1)));
                    previousButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringUtils.color(iridiumTeams.getTeamLogs().teamLogPreviousPageHover)).create()));
                }
                if (finalPage != maxPage) {
                    nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + iridiumTeams.getCommandManager().getCommand() + " logs " + (finalPage + 1)));
                    nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringUtils.color(iridiumTeams.getTeamLogs().teamLogNextPageHover)).create()));
                }

                // Send all messages
                player.sendMessage(StringUtils.color(StringUtils.getCenteredMessage(iridiumTeams.getTeamLogs().teamLogHeader, iridiumTeams.getTeamLogs().teamLogFiller)));
                processedTeamLogs.stream()
                        .map(teamLog -> StringUtils.color(iridiumTeams.getTeamLogs().teamLogMessage
                                .replace("%date%", teamLog.getTime().format(DateTimeFormatter.ofPattern(iridiumTeams.getTeamLogs().teamLogDateTimeFormat)))
                                .replace("%description%", teamLog.getDescription())))
                        .forEach(player::sendMessage);

                player.spigot().sendMessage(previousButton, footerText, nextButton);
            }));
        }));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return new ArrayList<>();
    }

}
