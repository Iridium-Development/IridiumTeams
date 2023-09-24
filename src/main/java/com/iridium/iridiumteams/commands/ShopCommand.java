package com.iridium.iridiumteams.commands;

import com.iridium.iridiumcore.utils.StringUtils;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.gui.ShopCategoryGUI;
import com.iridium.iridiumteams.gui.ShopOverviewGUI;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class ShopCommand<T extends Team, U extends IridiumUser<T>> extends Command<T, U> {

    public ShopCommand(List<String> args, String description, String syntax, String permission, long cooldownInSeconds) {
        super(args, description, syntax, permission, cooldownInSeconds);
    }

    @Override
    public boolean execute(U user, String[] arguments, IridiumTeams<T, U> iridiumTeams) {
        Player player = user.getPlayer();
        if (arguments.length == 0) {
            player.openInventory(new ShopOverviewGUI<>(player.getOpenInventory().getTopInventory(), iridiumTeams).getInventory());
            return true;
        }

        Optional<String> categoryName = getCategoryName(String.join(" ", arguments), iridiumTeams);

        if (!categoryName.isPresent()) {
            player.sendMessage(StringUtils.color(iridiumTeams.getMessages().noShopCategory
                    .replace("%prefix%", iridiumTeams.getConfiguration().prefix)
            ));
            return false;
        }

        player.openInventory(new ShopCategoryGUI<>(categoryName.get(), player.getOpenInventory().getTopInventory(), 1, iridiumTeams).getInventory());
        return true;
    }

    private Optional<String> getCategoryName(String name, IridiumTeams<T, U> iridiumTeams) {
        for (String category : iridiumTeams.getShop().categories.keySet()) {
            if (category.equalsIgnoreCase(name)) {
                return Optional.of(category);
            }
        }
        return Optional.empty();
    }
}
