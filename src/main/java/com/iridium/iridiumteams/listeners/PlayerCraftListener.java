package com.iridium.iridiumteams.listeners;

import com.cryptomorin.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class PlayerCraftListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorPlayerCraft(CraftItemEvent event) {
        int amount = event.isShiftClick() ? Arrays.stream(event.getInventory().getMatrix())
                .filter(Objects::nonNull)
                .map(ItemStack::getAmount)
                .sorted()
                .findFirst()
                .orElse(1) * event.getRecipe().getResult().getAmount() : event.getRecipe().getResult().getAmount();

        Player player = (Player) event.getWhoClicked();
        U user = iridiumTeams.getUserManager().getUser(player);
        XMaterial material = XMaterial.matchXMaterial(event.getRecipe().getResult().getType());

        iridiumTeams.getTeamManager().getTeamViaID(user.getTeamID()).ifPresent(team -> {
            iridiumTeams.getMissionManager().handleMissionUpdate(team, event.getWhoClicked().getLocation().getWorld(), "CRAFT", material.name(), amount);
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (iridiumTeams.getTeamManager().isBankItem(item)) {
                event.getInventory().setResult(null);
                return;
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onCrafterCraft(CrafterCraftEvent event) {
        Location loc = event.getBlock().getLocation();

        Optional<T> teamOpt = iridiumTeams.getTeamManager().getTeamViaLocation(loc);
        if (teamOpt.isEmpty()) return;

        ItemStack result = event.getResult();
        if (result == null || result.getType().isAir()) return;

        if (isBankItem(event.getRecipe())) {
            event.setCancelled(true);
            return;
        }

        T team = teamOpt.get();
        XMaterial material = XMaterial.matchXMaterial(result.getType());

        iridiumTeams.getMissionManager().handleMissionUpdate(
                team,
                loc.getWorld(),
                "CRAFT",
                material.name(),
                result.getAmount()
        );
    }

    private boolean isBankItem(Recipe recipe) {
        List<ItemStack> ingredients;
        if (recipe instanceof ShapedRecipe shaped) {
            ingredients = new ArrayList<>(shaped.getIngredientMap().values());
        } else if (recipe instanceof ShapelessRecipe shapeless) {
            ingredients = shapeless.getIngredientList();
        } else {
            return false;
        }

        for (ItemStack item : ingredients) {
            if (item != null && iridiumTeams.getTeamManager().isBankItem(item)) {
                return true;
            }
        }
        return false;
    }
}