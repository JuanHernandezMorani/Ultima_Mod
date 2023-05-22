package com.techmdq.Ultima.eventos;

import com.techmdq.Ultima.Ultima;
import com.techmdq.Ultima.client.models.ZombieBossModel;
import com.techmdq.Ultima.client.renderer.ZombieBossRenderer;
import com.techmdq.Ultima.init.ModsInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ultima.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MobsRendererEvents {

    @SubscribeEvent
    public  void entityRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModsInit.ZOMBIEBOSS.get(), ZombieBossRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ZombieBossModel.LAYER_LOCATION, ZombieBossModel::createBodyLayer);
    }
}
