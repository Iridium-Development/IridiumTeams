package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor
public class HelpCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public HelpCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        List<Command<T, U>> availableCommands = iridiumTeams.getCommandManager().getCommands().stream()
                .filter(command -> sender.hasPermission(command.permission) || command.permission.isEmpty())
                .collect(Collectors.toList());

        int page = 1;
        int maxPage = (int) Math.ceil(availableCommands.size() / 8.0);

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

        // Prepare the footer
        TextComponent footerText = new TextComponent(StringUtils.color(iridiumTeams.getMessages().helpCommandFooter
                .replace("%page%", String.valueOf(page))
                .replace("%max_page%", String.valueOf(maxPage))
        ));
        TextComponent previousButton = new TextComponent(StringUtils.color(iridiumTeams.getMessages().helpCommandPreviousPage));
        TextComponent nextButton = new TextComponent(StringUtils.color(iridiumTeams.getMessages().helpCommandNextPage));
        if (page != 1) {
            previousButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + iridiumTeams.getCommandManager().getCommand() + " help " + (page - 1)));
            previousButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringUtils.color(iridiumTeams.getMessages().helpCommandPreviousPageHover)).create()));
        }
        if (page != maxPage) {
            nextButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + iridiumTeams.getCommandManager().getCommand() + " help " + (page + 1)));
            nextButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringUtils.color(iridiumTeams.getMessages().helpCommandNextPageHover)).create()));
        }

        // Send all messages
        sender.sendMessage(StringUtils.color(StringUtils.getCenteredMessage(iridiumTeams.getMessages().helpCommandHeader, iridiumTeams.getMessages().helpCommandFiller)));
        availableCommands.stream()
                .skip((page - 1) * 8L)
                .limit(8)
                .map(command -> StringUtils.color(iridiumTeams.getMessages().helpCommandMessage
                        .replace("%command%", command.aliases.get(0))
                        .replace("%description%", command.description)))
                .forEach(sender::sendMessage);

        if (sender instanceof Player) {
            ((Player) sender).spigot().sendMessage(previousButton, footerText, nextButton);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        int availableCommandAmount = (int) iridiumTeams.getCommandManager().getCommands().stream()
                .filter(command -> commandSender.hasPermission(command.permission) || command.permission.isEmpty())
                .count();

        // Return all numbers from 1 to the max page
        return IntStream.rangeClosed(1, (int) Math.ceil(availableCommandAmount / 8.0))
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

}
