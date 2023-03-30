package com.iridium.iridiumteams.listeners;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.dependencies.xseries.particles.XParticle;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import com.iridium.iridiumteams.database.TeamEnhancement;
import com.iridium.iridiumteams.enhancements.Enhancement;
import com.iridium.iridiumteams.enhancements.FarmingEnhancementData;
import lombok.AllArgsConstructor;
import org.bukkit.Effect;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;


@AllArgsConstructor
public class BlockGrowListener<T extends Team, U extends IridiumUser<T>> implements Listener {
    private final IridiumTeams<T, U> iridiumTeams;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void monitorBlockGrow(BlockGrowEvent event) {
        XMaterial material = XMaterial.matchXMaterial(event.getBlock().getType());
        iridiumTeams.getTeamManager().getTeamViaLocation(event.getBlock().getLocation()).ifPresent(team -> {
            iridiumTeams.getMissionManager().handleMissionUpdate(team, event.getBlock().getLocation().getWorld().getEnvironment(), "GROW", material.name(), 1);

            if (event.getNewState().getBlock().getBlockData() instanceof Ageable) {
                Enhancement<FarmingEnhancementData> farmingEnhancement = iridiumTeams.getEnhancements().farmingEnhancement;
                TeamEnhancement teamEnhancement = iridiumTeams.getTeamManager().getTeamEnhancement(team, "farming");
                FarmingEnhancementData data = farmingEnhancement.levels.get(teamEnhancement.getLevel());
                if (teamEnhancement.isActive(farmingEnhancement.type) && data != null) {
                    Ageable ageable = (Ageable) event.getNewState().getBlockData();
                    ageable.setAge(Math.min(ageable.getAge() + data.farmingModifier, ageable.getMaximumAge()));
                    event.getNewState().getBlock().setBlockData(ageable);
                    event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.BONE_MEAL_USE, 0);
                }

            }
        });

    }

}
