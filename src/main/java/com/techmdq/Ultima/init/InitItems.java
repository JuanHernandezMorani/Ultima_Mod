package com.techmdq.Ultima.init;

import com.techmdq.Ultima.Ultima;
import com.techmdq.Ultima.Items.ZombieBossSpawnEgg;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class InitItems {
     public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
       ForgeRegistries.ITEMS, Ultima.MODID
     );

     public static final RegistryObject<Item> ZOMBIEBOSSSPAWNEGG = ITEMS.register(
       "zombie_boss_spawn_egg", () -> new ZombieBossSpawnEgg(
               new Item.Properties().tab(CreativeModeTab.TAB_MISC)
                       .fireResistant()
                       .rarity(Rarity.EPIC)
             )
     );

}
