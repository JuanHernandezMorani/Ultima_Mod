package com.techmdq.Ultima.eventos;

import com.techmdq.Ultima.Ultima;
import com.techmdq.Ultima.entities.ZombieBossEntity;
import com.techmdq.Ultima.init.ModsInit;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ultima.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobsAttrsEvent {

    @SubscribeEvent
    public void entityAttributes(EntityAttributeCreationEvent event){
        event.put(ModsInit.ZOMBIEBOSS.get(), ZombieBossEntity.createAttributes().build());
    }
}
