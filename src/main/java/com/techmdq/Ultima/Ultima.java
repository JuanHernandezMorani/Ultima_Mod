package com.techmdq.Ultima;

import com.techmdq.Ultima.handlers.AttackEventHandler;
import com.techmdq.Ultima.init.EntityAttributeModifier;
import com.techmdq.Ultima.init.InitItems;
import com.techmdq.Ultima.eventos.*;
import com.techmdq.Ultima.init.ModAttributes;
import com.techmdq.Ultima.statseffects.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.techmdq.Ultima.init.EntityInit.ENTITIES;
import static com.techmdq.Ultima.init.ModsInit.MOBS;

@Mod(Ultima.MODID)
public class Ultima {
    public static final String MODID = "ultima";

    public Ultima() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(new ModAttributes());
        bus.register(new EntityAttributeModifier());
        InitItems.ITEMS.register(bus);
        ENTITIES.register(bus);
        MOBS.register(bus);
        bus.register(new MobsAttrsEvent());
        bus.register(new MobsRendererEvents());
        bus.register(new Command());
        bus.register(new RegistrationHandler());
        bus.register(new AttackEventHandler());
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public void onEntityJoinWorld(EntityJoinLevelEvent event) {
            if (!event.getLevel().isClientSide && event.getEntity() instanceof LivingEntity livingEntity) {
                applyStatsEffects(livingEntity);
            }
        }

        private static void applyStatsEffects(LivingEntity entity) {
            new Agility(entity);
            new Defense(entity);
            new Dexterity(entity);
            new Intelligent(entity);
            new Life(entity);
            new Luck(entity);
            new Strength(entity);
        }
    }
}
