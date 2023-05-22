package com.techmdq.Ultima.handlers;

import com.techmdq.Ultima.statseffects.RangeDamage;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttackEventHandler {

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack heldItem = attacker.getMainHandItem();
            if (!heldItem.isEmpty() && heldItem.getItem() instanceof ProjectileWeaponItem) {
                RangeDamage.applyCustom(heldItem.getItem(),attacker);
            }
        }
    }

}
