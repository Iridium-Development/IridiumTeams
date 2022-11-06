package com.iridium.iridiumteams.placeholders;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.iridium.iridiumcore.utils.Placeholder;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;

import java.util.List;

public class MVDWPlaceholderAPI<T extends Team, U extends IridiumUser<T>> {

    private final IridiumTeams<T, U> iridiumTeams;
    private final Placeholders<T, U> placeholders;

    public MVDWPlaceholderAPI(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
        this.placeholders = new Placeholders<>(iridiumTeams);
    }

    public void register() {
        List<Placeholder> defaultList = placeholders.getDefaultPlaceholders();

        for (Placeholder placeholder : defaultList) {
            PlaceholderAPI.registerPlaceholder(iridiumTeams, iridiumTeams.getName().toLowerCase() + "_" + placeholder.getKey(), event -> {

                if (event.getPlayer() == null) {
                    return "N/A";
                }
                List<Placeholder> placeholderList = placeholders.getPlaceholders(event.getPlayer());

                return placeholderList.stream().filter(placeholder1 -> placeholder1.getKey().equals(placeholder.getKey())).findFirst().get().getValue();
            });
        }
    }

    public int getPlaceholderCount() {
        return placeholders.getDefaultPlaceholders().size();
    }

}
