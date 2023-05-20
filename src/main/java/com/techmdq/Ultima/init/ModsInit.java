package com.techmdq.Ultima.init;

import com.techmdq.Ultima.Ultima;
import com.techmdq.Ultima.entities.ZombieBossEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModsInit {

    public static final DeferredRegister<EntityType<?>> MOBS =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Ultima.MODID);

    public static final RegistryObject<EntityType<ZombieBossEntity>> ZOMBIEBOSS =
            MOBS.register("zombieboss",
                    () -> EntityType.Builder.of(ZombieBossEntity::new, MobCategory.MONSTER)
                            .build(Ultima.MODID + ":zombieboss"));

}
