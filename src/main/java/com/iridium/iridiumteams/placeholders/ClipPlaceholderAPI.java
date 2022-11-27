package com.iridium.iridiumteams.placeholders;

import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.List;

public class ClipPlaceholderAPI<T extends Team, U extends IridiumUser<T>> extends PlaceholderExpansion {

    private final IridiumTeams<T, U> iridiumTeams;
    private final Placeholders<T, U> placeholders;

    public ClipPlaceholderAPI(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
        this.placeholders = new Placeholders<>(iridiumTeams);
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return iridiumTeams.getName().toLowerCase();
    }

    @Override
    public String getAuthor() {
        return "Peaches_MLG";
    }

    @Override
    public String getVersion() {
        return iridiumTeams.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String placeholderKey) {
        List<Placeholder> placeholderList = placeholders.getPlaceholders(player);

        for (Placeholder placeholder : placeholderList) {
            if (formatPlaceholderKey(placeholder.getKey()).equalsIgnoreCase(placeholderKey)) return placeholder.getValue();
        }

        return null;
    }

    public int getPlaceholderCount(){
        return placeholders.getDefaultPlaceholders().size();
    }

    private String formatPlaceholderKey(String key){
        return key.replace("%", "");
    }
}
