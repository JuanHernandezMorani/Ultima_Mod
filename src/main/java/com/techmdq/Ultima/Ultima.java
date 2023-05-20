package com.techmdq.Ultima;

import com.techmdq.Ultima.init.InitItems;
import com.techmdq.Ultima.eventos.*;
import net.minecraftforge.eventbus.api.IEventBus;
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
        InitItems.ITEMS.register(bus);
        ENTITIES.register(bus);
        MOBS.register(bus);
        EVENT_BUS.register(new MobsAttrsEvent());
        EVENT_BUS.register(new MobsRendererEvents());
    }
}
