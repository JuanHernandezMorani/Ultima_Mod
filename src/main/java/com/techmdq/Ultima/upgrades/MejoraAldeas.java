package com.techmdq.Ultima.upgrades;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CartographyTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.eventbus.api.Event;
import java.util.List;
import java.util.Objects;


@Mod("ultima")
public class MejoraAldeas {
    public MejoraAldeas() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getEntity();
        if(player == null) {
            return;
        }

        Level world = player.level;

        BlockState clickedBlockState = world.getBlockState(event.getPos());
        Block clickedBlock = clickedBlockState.getBlock();

        if(clickedBlock instanceof CartographyTableBlock){

            BlockPos blockPos = event.getPos();

            List<Villager> villagers = player.level.getEntitiesOfClass(Villager.class, new AABB(blockPos), entity -> true);

            Villager villager = villagers.stream()
                    .filter(entity -> entity.blockPosition().equals(blockPos))
                    .findFirst()
                    .orElse(null);

            if(villager != null){
                ItemStack heldItem = player.getMainHandItem();
                if (heldItem.getItem() == Items.GOLD_INGOT && !world.isClientSide()) {
                    improveVillage(villager, world, (ServerPlayer) player);
                    heldItem.shrink(1);
                    event.setCanceled(true);
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }


    }

    private void improveVillage(Villager villager, Level world, ServerPlayer player) {
        CompoundTag data = villager.getPersistentData();
        int villageRadius = data.getInt("village_radius");
        int villageX = villager.getVillagerData().getLevel();
        BlockPos center = villager.blockPosition();

        long seed = Objects.requireNonNull(world.getServer()).getWorldData().worldGenSettings().seed();
        int villageZ = (int) (seed % 500);

        for (int x = villageX - villageRadius; x <= villageX + villageRadius; x++) {
            for (int z = villageZ - villageRadius; z <= villageZ + villageRadius; z++) {
                BlockPos pos = new BlockPos(x * 16, center.getY(), z * 16);
                BlockState blockState = world.getBlockState(pos);
                Block block = blockState.getBlock();

                if (block == Blocks.COMPOSTER) {
                    world.setBlockAndUpdate(pos, Blocks.CARTOGRAPHY_TABLE.defaultBlockState());
                } else if (block == Blocks.CARTOGRAPHY_TABLE) {
                    world.setBlockAndUpdate(pos, Blocks.SMITHING_TABLE.defaultBlockState());
                } else if (block == Blocks.SMITHING_TABLE) {
                    world.setBlockAndUpdate(pos, Blocks.GRINDSTONE.defaultBlockState());
                } else if (block == Blocks.GRINDSTONE) {
                    world.setBlockAndUpdate(pos, Blocks.BOOKSHELF.defaultBlockState());
                } else if (block == Blocks.BOOKSHELF) {
                    world.setBlockAndUpdate(pos, Blocks.CARTOGRAPHY_TABLE.defaultBlockState());
                }
            }
        }
        villageRadius++;
        data.putInt("village_radius", villageRadius);
        player.displayClientMessage(Component.literal("¡La aldea ha sido mejorada!"), true);
    }
}
