package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.ChatType;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ChatCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {
    public ChatCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, T team, String[] args, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (args.length != 1) {
            player.sendMessage(StringUtils.color(syntax.replace("%prefix%", iridiumTeams.getConfiguration().prefix)));
            return false;
        }
        Optional<ChatType> chatType = iridiumTeams.getChatTypes().stream()
                .filter(type -> type.getAliases().stream().anyMatch(s -> s.equalsIgnoreCase(args[0])))
                .findFirst();
        if (!chatType.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().unknownChatType
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                    .replace("%type%", args[0]))
            );
            return false;
        }

        String chat = WordUtils.capitalizeFully(chatType.get().getAliases().stream().max(Comparator.comparing(String::length)).orElse(args[0]));
        user.setChatType(chat);
        player.sendMessage(StringUtils.color(iridiumTeams.getMessages().setChatType
                .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
                .replace("%type%", chat))
        );
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String[] args, IridiumTeams<T, U> iridiumTeams) {
        return iridiumTeams.getChatTypes().stream()
                .flatMap(chatTypes -> chatTypes.getAliases().stream())
                .collect(Collectors.toList());
    }
}
