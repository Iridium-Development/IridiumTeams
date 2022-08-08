package com.iridium.iridiumteams.utils;

import com.iridium.iridiumcore.dependencies.xseries.XMaterial;
import com.iridium.iridiumcore.multiversion.MultiVersion;
import com.iridium.iridiumteams.IridiumTeams;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocationUtils {
    @Setter
    private static boolean isSafeTesting = true;

    private static final List<Material> unsafeBlocks = Stream.of(
            XMaterial.END_PORTAL,
            XMaterial.WATER,
            XMaterial.LAVA
    ).map(XMaterial::parseMaterial).collect(Collectors.toList());

    public static boolean isSafe(@NotNull Location location, IridiumTeams<?, ?> iridiumTeams) {
        if (iridiumTeams.isTesting()) {
            boolean safe = isSafeTesting;
            isSafeTesting = true;
            return safe;
        }
        Block block = location.getBlock();
        Block above = location.clone().add(0, 1, 0).getBlock();
        Block below = location.clone().subtract(0, 1, 0).getBlock();
        MultiVersion multiVersion = iridiumTeams.getMultiVersion();
        return multiVersion.isPassable(block) && multiVersion.isPassable(above) && !multiVersion.isPassable(below) && !unsafeBlocks.contains(below.getType()) && !unsafeBlocks.contains(block.getType()) && !unsafeBlocks.contains(above.getType());
    }

    public static int getMinHeight(World world) {
        return XMaterial.getVersion() >= 17 ? world.getMinHeight() : 0;
    }
}
