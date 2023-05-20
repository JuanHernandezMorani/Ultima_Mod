package com.techmdq.Ultima.client.renderer;

import com.techmdq.Ultima.Ultima;
import com.techmdq.Ultima.client.models.ZombieBossModel;
import com.techmdq.Ultima.entities.ZombieBossEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ZombieBossRenderer extends MobRenderer<ZombieBossEntity, ZombieBossModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Ultima.MODID, "textures/entities/zombieboss.png");

    public ZombieBossRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombieBossModel(context.bakeLayer(ZombieBossModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ZombieBossEntity entity) {
        return ZombieBossRenderer.TEXTURE;
    }
 }
