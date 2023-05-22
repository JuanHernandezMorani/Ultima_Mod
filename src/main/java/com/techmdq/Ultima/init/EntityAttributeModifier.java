package com.techmdq.Ultima.init;

import com.techmdq.Ultima.Ultima;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Ultima.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityAttributeModifier {

    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent.@NotNull CheckSpawn event) {
        EntityType<?> entityType = event.getEntity().getType();

        HashMap<EntityType<?>, double[]> entityAttributes = new HashMap<>();
        entityAttributes.put(EntityType.ALLAY, new double[]{4, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.AXOLOTL, new double[]{4, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.BAT, new double[]{4, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.BEE, new double[]{4, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.BLAZE, new double[]{24, 10, 1, 2, 8, 4, 1, 1, 0});
        entityAttributes.put(EntityType.CAT, new double[]{4, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.CAVE_SPIDER, new double[]{5, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.CHICKEN, new double[]{4, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.COD, new double[]{2, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.COW, new double[]{14, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.DOLPHIN, new double[]{14, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.DONKEY, new double[]{24, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.DROWNED, new double[]{24, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.ELDER_GUARDIAN, new double[]{80, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.ENDERMAN, new double[]{30, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.ENDERMITE, new double[]{6, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.EVOKER, new double[]{12, 10, 1, 2, 4, 4, 1, 1, 10});
        entityAttributes.put(EntityType.ENDER_DRAGON, new double[]{250, 10, 1, 6, 8, 10, 2, 9, 30});
        entityAttributes.put(EntityType.GHAST, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.GIANT, new double[]{200, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.GUARDIAN, new double[]{30, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.HOGLIN, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.HORSE, new double[]{24, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.HUSK, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.ILLUSIONER, new double[]{14, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.IRON_GOLEM, new double[]{50, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.LLAMA, new double[]{14, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.MAGMA_CUBE, new double[]{16, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.MULE, new double[]{24, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.MOOSHROOM, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.OCELOT, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.PANDA, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.PARROT, new double[]{8, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.PHANTOM, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.PIG, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.PIGLIN, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.PIGLIN_BRUTE, new double[]{30, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.PILLAGER, new double[]{14, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.POLAR_BEAR, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.PUFFERFISH, new double[]{4, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.RABBIT, new double[]{4, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.RAVAGER, new double[]{100, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SALMON, new double[]{2, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SHEEP, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SHULKER, new double[]{30, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SILVERFISH, new double[]{8, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SKELETON, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SKELETON_HORSE, new double[]{30, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SLIME, new double[]{16, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SNOW_GOLEM, new double[]{4, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SPIDER, new double[]{12, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.SQUID, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.STRAY, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.STRIDER, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.TRADER_LLAMA, new double[]{14, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.TROPICAL_FISH, new double[]{2, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.TURTLE, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.VEX, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.VILLAGER, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.VINDICATOR, new double[]{12, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.WANDERING_TRADER, new double[]{10, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.WITCH, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.WITHER, new double[]{300, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.WITHER_SKELETON, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.WOLF, new double[]{14, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.ZOGLIN, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.ZOMBIE, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.ZOMBIE_HORSE, new double[]{30, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.ZOMBIFIED_PIGLIN, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});
        entityAttributes.put(EntityType.ZOMBIE_VILLAGER, new double[]{20, 10, 1, 2, 4, 4, 1, 1, 0});

        double[] attributes = entityAttributes.get(entityType);
        if (attributes != null) {
            attMod(event.getEntity(), attributes[0], attributes[1], attributes[2], attributes[3],
                    attributes[4], attributes[5], attributes[6], attributes[7], attributes[8]);
        }
        
    }

    public void attMod(@NotNull LivingEntity entity, double lifeValue, double manaValue, double defenseValue, double strengthValue,
                       double dexterityValue, double agilityValue, double commandValue, double luckValue,
                       double intelligentValue){

        AttributeMap attributeMap = entity.getAttributes();

        modifyAttribute(attributeMap, ModAttributes.LIFE.get(), lifeValue);
        modifyAttribute(attributeMap, ModAttributes.MANA.get(), manaValue);
        modifyAttribute(attributeMap, ModAttributes.DEFENSE.get(), defenseValue);
        modifyAttribute(attributeMap, ModAttributes.STRENGTH.get(), strengthValue);
        modifyAttribute(attributeMap, ModAttributes.DEXTERITY.get(), dexterityValue);
        modifyAttribute(attributeMap, ModAttributes.AGILITY.get(), agilityValue);
        modifyAttribute(attributeMap, ModAttributes.COMMAND.get(), commandValue);
        modifyAttribute(attributeMap, ModAttributes.LUCK.get(), luckValue);
        modifyAttribute(attributeMap, ModAttributes.INTELLIGENT.get(), intelligentValue);


    }
    private void modifyAttribute(@NotNull AttributeMap attributeMap, Attribute attribute, double value) {
        AttributeInstance attributeInstance = attributeMap.getInstance(attribute);
        if (attributeInstance != null) {
            AttributeModifier existingModifier = attributeInstance.getModifier(UUID.fromString(attribute.getDescriptionId()));
            if (existingModifier != null) {
                attributeInstance.removeModifier(existingModifier);
            }
            AttributeModifier newModifier = new AttributeModifier(attribute.getDescriptionId(), value, AttributeModifier.Operation.ADDITION);
            attributeInstance.addPermanentModifier(newModifier);
        }
    }

}






