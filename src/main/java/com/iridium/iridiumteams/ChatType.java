package com.iridium.iridiumteams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AllArgsConstructor
@Getter
public class ChatType {
    private final List<String> aliases;
    private final PlayerChat playerChat;

    public interface PlayerChat {
        @Nullable List<Player> getPlayers(Player player);
    }

}

