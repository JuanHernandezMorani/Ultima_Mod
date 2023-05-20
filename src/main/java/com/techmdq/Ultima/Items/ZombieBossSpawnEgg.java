package com.techmdq.Ultima.Items;

import com.techmdq.Ultima.utils.Summon;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ZombieBossSpawnEgg extends Item {

    public ZombieBossSpawnEgg(Properties props) {
        super(props);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interact) {
        var result = super.use(level, player, interact);
        Summon.summonEntity("ultima:zombieboss", player);
        return result;
    }

}
