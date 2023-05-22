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
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod(Ultima.MODID)
public class Ultima {
    public static final String MODID = "ultima";
    public Ultima(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EVENT_BUS.register(new ModAttributes());
        EVENT_BUS.register(new EntityAttributeModifier());
        InitItems.ITEMS.register(bus);
        ENTITIES.register(bus);
        MOBS.register(bus);
        EVENT_BUS.register(new MobsAttrsEvent());
        EVENT_BUS.register(new MobsRendererEvents());
        EVENT_BUS.register(new Command());
        EVENT_BUS.register(new RegistrationHandler());
        EVENT_BUS.register(new AttackEventHandler());
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public void onEntityJoinWorld(EntityJoinLevelEvent event) {
            if (!event.getLevel().isClientSide) {
                if (event.getEntity() instanceof LivingEntity) {
                    new Agility(event.getEntity());
                    new Defense(event.getEntity());
                    new Dexterity(event.getEntity());
                    new Intelligent(event.getEntity());
                    new Life(event.getEntity());
                    new Luck(event.getEntity());
                    new Strength(event.getEntity());
                }
            }
        }
    }
}
