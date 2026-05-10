package com.iridium.iridiumteams.support;

import com.cryptomorin.xseries.XMaterial;
import com.iridium.iridiumteams.IridiumTeams;
import com.iridium.iridiumteams.database.IridiumUser;
import com.iridium.iridiumteams.database.Team;
import dev.rosewood.rosestacker.api.RoseStackerAPI;
import dev.rosewood.rosestacker.stack.StackedBlock;
import dev.rosewood.rosestacker.stack.StackedSpawner;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import java.util.*;
import java.util.stream.Collectors;

public class RoseStackerSupport<T extends Team, U extends IridiumUser<T>> implements StackerSupport<T>, SpawnerSupport<T> {

    private final IridiumTeams<T, U> iridiumTeams;

    public RoseStackerSupport(IridiumTeams<T, U> iridiumTeams) {
        this.iridiumTeams = iridiumTeams;
    }

    @Override
    public String supportProvider() {
        return "RoseStacker";
    }

    @Override
    public boolean isStackedBlock(Block block) {
        return RoseStackerAPI.getInstance().isBlockStacked(block);
    }

    @Override
    public boolean isStackedSpawner(Block block) {
        return RoseStackerAPI.getInstance().isSpawnerStacked(block);
    }

    private Optional<StackedBlock> getStackedBlock(Block block) {
        return Optional.ofNullable(RoseStackerAPI.getInstance().getStackedBlock(block));
    }

    private Optional<StackedSpawner> getStackedSpawner(Block block) {
        return Optional.ofNullable(RoseStackerAPI.getInstance().getStackedSpawner(block));
    }

    private List<StackedBlock> getStackedBlocks(List<Block> blocks) {
        List<StackedBlock> stackedBlocks = new ArrayList<>(Collections.emptyList());
        for(Block block : blocks) {
            getStackedBlock(block).ifPresent(stackedBlocks::add);
        }
        return stackedBlocks;
    }

    private List<StackedSpawner> getStackedSpawners(List<CreatureSpawner> spawners) {
        List<StackedSpawner> stackedSpawners = new ArrayList<>(Collections.emptyList());
        for(CreatureSpawner spawner : spawners) {
            getStackedSpawner(spawner.getBlock()).ifPresent(stackedSpawners::add);
        }
        return stackedSpawners;
    }

    @Override
    public int getStackAmount(Block block) {
        return getStackedBlock(block).map(StackedBlock::getStackSize).orElse(0);
    }

    @Override
    public int getStackAmount(CreatureSpawner spawner) {
        return getStackedSpawner(spawner.getBlock()).map(StackedSpawner::getStackSize).orElse(0);
    }

    @Override
    public int getSpawnAmount(CreatureSpawner spawner) {
        Optional<StackedSpawner> stackedSpawner = getStackedSpawner(spawner.getBlock());
        return stackedSpawner.map(StackedSpawner::getStackSize).orElse(0) * stackedSpawner.map(s -> s.getSpawner().getSpawnCount()).orElse(0);
    }

    @Override
    public Map<XMaterial, Integer> getBlocksStacked(Chunk chunk, T team) {
        HashMap<XMaterial, Integer> hashMap = new HashMap<>();

        RoseStackerAPI.getInstance().getStackedBlocks(Collections.singletonList(chunk)).forEach(stackedBlock -> {
            if(!iridiumTeams.getTeamManager().isInTeam(team, stackedBlock.getLocation())) return;

            XMaterial xMaterial = XMaterial.matchXMaterial(stackedBlock.getStackSettings().getType());
            hashMap.put(xMaterial, hashMap.getOrDefault(xMaterial, 0) + stackedBlock.getStackSize());
        });

        return hashMap;
    }

    @Override
    public List<CreatureSpawner> getSpawnersStacked(Chunk chunk) {
        return RoseStackerAPI.getInstance().getStackedSpawners(Collections.singletonList(chunk)).stream().map(StackedSpawner::getSpawner).collect(Collectors.toList());
    }

    @Override
    public int getExtraBlocks(T team, XMaterial material, List<Block> blocks) {

        int stackedBlocks = 0;
        for (StackedBlock stackedBlock : getStackedBlocks(blocks)) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, stackedBlock.getLocation())) continue;
            if (material != XMaterial.matchXMaterial(stackedBlock.getBlock().getType())) continue;
            stackedBlocks += (stackedBlock.getStackSize() - 1);
        }

        return stackedBlocks;
    }

    @Override
    public int getExtraSpawners(T team, EntityType entityType, List<CreatureSpawner> spawners) {

        int stackedSpawners = 0;
        for (StackedSpawner stackedSpawner : getStackedSpawners(spawners)) {
            if (!iridiumTeams.getTeamManager().isInTeam(team, stackedSpawner.getLocation())) continue;
            if (stackedSpawner.getSpawner().getSpawnedType() != entityType) continue;
            stackedSpawners += (stackedSpawner.getStackSize() - 1);
        }

        return stackedSpawners;
    }
}