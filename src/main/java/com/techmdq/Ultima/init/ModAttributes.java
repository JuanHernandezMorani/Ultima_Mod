package com.techmdq.Ultima.init;

import com.techmdq.Ultima.Ultima;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Ultima.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Ultima.MODID);
    public static final RegistryObject<Attribute> HPREGEN = ATTRIBUTES.register("life_regen", () -> new RangedAttribute("Ultima.life_regen", 1.0D, 0.0D, 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute> LIFE = ATTRIBUTES.register("life", () -> new RangedAttribute("Ultima.life", 20.0D, HPREGEN.get().getDefaultValue(), 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute> MPREGEN = ATTRIBUTES.register("mana_regen", () -> new RangedAttribute("Ultima.mana_regen", 5.0D, 0.0D, 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute>  MANA = ATTRIBUTES.register("mana", () -> new RangedAttribute("Ultima.mana", 100.0D, MPREGEN.get().getDefaultValue(), 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute>  MAGIC = ATTRIBUTES.register("magic_damage", () -> new RangedAttribute("Ultima.magic_damage", 2.0D, 0, 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute> DEFENSE = ATTRIBUTES.register("defense", () -> new RangedAttribute("Ultima.defense", 1.0D, 0.0D, 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute> MAGICDEFENSE = ATTRIBUTES.register("magic_defense", () -> new RangedAttribute("Ultima.magic_defense", 1.0D, 0.0D, 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute> STRENGTH = ATTRIBUTES.register("strength", () -> new RangedAttribute("Ultima.strength", 4.0D, 0.0D, 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute> RANGEDAMAGE = ATTRIBUTES.register("range_damage", () -> new RangedAttribute("Ultima.range_damage", 4.0D, 0.0D, 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute> DEXTERITY = ATTRIBUTES.register("dexterity", () -> new RangedAttribute("Ultima.dexterity", 4.0D, 0.0D, 1024.0).setSyncable(true));
    public static final RegistryObject<Attribute> AGILITY = ATTRIBUTES.register("agility", () -> new RangedAttribute("Ultima.agility", 0.1D, 0.0D, 1.0D).setSyncable(true));
    public static final RegistryObject<Attribute> COMMAND = ATTRIBUTES.register("command", () -> new RangedAttribute("Ultima.command", 4.0D, 0.0D, 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute> LUCK = ATTRIBUTES.register("luck", () -> new RangedAttribute("Ultima.luck", 2.0D, 0.0D, 99999999.99D).setSyncable(true));
    public static final RegistryObject<Attribute> INTELLIGENT = ATTRIBUTES.register("intelligent",() -> new RangedAttribute("Ultima.intelligent", 0.0D, 0.0D, 99999999.99D).setSyncable(true));
    public static AttributeSupplier stat;

    public static void StatBuilder(){
        AttributeSupplier.Builder statBuilder = AttributeSupplier.builder();
        statBuilder.add(HPREGEN.get());
        statBuilder.add(MPREGEN.get());
        statBuilder.add(LIFE.get());
        statBuilder.add(MANA.get());
        statBuilder.add(MAGIC.get());
        statBuilder.add(MAGICDEFENSE.get());
        statBuilder.add(DEFENSE.get());
        statBuilder.add(RANGEDAMAGE.get());
        statBuilder.add(STRENGTH.get());
        statBuilder.add(DEXTERITY.get());
        statBuilder.add(AGILITY.get());
        statBuilder.add(COMMAND.get());
        statBuilder.add(LUCK.get());
        statBuilder.add(INTELLIGENT.get());

        stat = statBuilder.build();
    }

    @SubscribeEvent
    public void onEntityTypeAttributeCreation(@NotNull EntityAttributeCreationEvent event) {
        StatBuilder();

        event.put(EntityType.PLAYER, stat);
        event.put(EntityType.ALLAY,stat);
        event.put(EntityType.AXOLOTL,stat);
        event.put(EntityType.BAT, stat);
        event.put(EntityType.BEE,stat);
        event.put(EntityType.BLAZE,stat);
        event.put(EntityType.CAT,stat);
        event.put(EntityType.CAVE_SPIDER,stat);
        event.put(EntityType.CHICKEN,stat);
        event.put(EntityType.COD,stat);
        event.put(EntityType.COW,stat);
        event.put(EntityType.CREEPER,stat);
        event.put(EntityType.DOLPHIN,stat);
        event.put(EntityType.DONKEY,stat);
        event.put(EntityType.DROWNED,stat);
        event.put(EntityType.ELDER_GUARDIAN,stat);
        event.put(EntityType.ENDERMAN,stat);
        event.put(EntityType.ENDERMITE,stat);
        event.put(EntityType.EVOKER,stat);
        event.put(EntityType.ENDER_DRAGON,stat);
        event.put(EntityType.FOX,stat);
        event.put(EntityType.FROG,stat);
        event.put(EntityType.GHAST,stat);
        event.put(EntityType.GIANT,stat);
        event.put(EntityType.GOAT,stat);
        event.put(EntityType.GUARDIAN,stat);
        event.put(EntityType.GLOW_SQUID,stat);
        event.put(EntityType.HOGLIN,stat);
        event.put(EntityType.HUSK,stat);
        event.put(EntityType.HORSE,stat);
        event.put(EntityType.ILLUSIONER,stat);
        event.put(EntityType.IRON_GOLEM,stat);
        event.put(EntityType.LLAMA,stat);
        event.put(EntityType.MAGMA_CUBE,stat);
        event.put(EntityType.MULE,stat);
        event.put(EntityType.MOOSHROOM,stat);
        event.put(EntityType.OCELOT,stat);
        event.put(EntityType.PANDA,stat);
        event.put(EntityType.PIG,stat);
        event.put(EntityType.PARROT,stat);
        event.put(EntityType.PHANTOM,stat);
        event.put(EntityType.PIGLIN,stat);
        event.put(EntityType.PIGLIN_BRUTE,stat);
        event.put(EntityType.PILLAGER,stat);
        event.put(EntityType.POLAR_BEAR,stat);
        event.put(EntityType.PUFFERFISH,stat);
        event.put(EntityType.RABBIT,stat);
        event.put(EntityType.RAVAGER,stat);
        event.put(EntityType.SALMON,stat);
        event.put(EntityType.SHEEP,stat);
        event.put(EntityType.SHULKER,stat);
        event.put(EntityType.SILVERFISH,stat);
        event.put(EntityType.SKELETON,stat);
        event.put(EntityType.SKELETON_HORSE,stat);
        event.put(EntityType.SLIME,stat);
        event.put(EntityType.SNOW_GOLEM,stat);
        event.put(EntityType.SPIDER,stat);
        event.put(EntityType.SQUID,stat);
        event.put(EntityType.STRAY,stat);
        event.put(EntityType.STRIDER,stat);
        event.put(EntityType.TADPOLE,stat);
        event.put(EntityType.TURTLE,stat);
        event.put(EntityType.TRADER_LLAMA,stat);
        event.put(EntityType.TROPICAL_FISH,stat);
        event.put(EntityType.VEX,stat);
        event.put(EntityType.VILLAGER,stat);
        event.put(EntityType.VINDICATOR,stat);
        event.put(EntityType.WARDEN,stat);
        event.put(EntityType.WANDERING_TRADER,stat);
        event.put(EntityType.WITCH,stat);
        event.put(EntityType.WOLF,stat);
        event.put(EntityType.WANDERING_TRADER,stat);
        event.put(EntityType.WITHER,stat);
        event.put(EntityType.WITHER_SKELETON,stat);
        event.put(EntityType.ZOMBIE,stat);
        event.put(EntityType.ZOGLIN,stat);
        event.put(EntityType.ZOMBIE_HORSE,stat);
        event.put(EntityType.ZOMBIE_VILLAGER,stat);
        event.put(EntityType.ZOMBIFIED_PIGLIN,stat);
    }


}