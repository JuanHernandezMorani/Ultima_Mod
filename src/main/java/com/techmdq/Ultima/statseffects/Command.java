package com.techmdq.Ultima.statseffects;

import com.techmdq.Ultima.init.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.util.Objects;


public class Command {
    private static final AttributeModifier LIFE_MODIFIER = new AttributeModifier("command_life", 0, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier STRENGTH_MODIFIER = new AttributeModifier("command_strength", 0, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier INTELLIGENCE_MODIFIER = new AttributeModifier("command_intelligence", 0, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier DEFENSE_MODIFIER = new AttributeModifier("command_defense", 0, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier AGILITY_MODIFIER = new AttributeModifier("command_agility", 0, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier DEXTERITY_MODIFIER = new AttributeModifier("command_dexterity", 0, AttributeModifier.Operation.ADDITION);

    @SubscribeEvent
    public void onSpecialSpawn(LivingSpawnEvent.SpecialSpawn event) {
        LivingEntity spawnedEntity = event.getEntity();

        if (spawnedEntity != null) {
            LivingEntity summoner = (LivingEntity) Objects.requireNonNull(event.getSpawner()).getSpawnerEntity();

            if (summoner != null) {
                AttributeInstance commandAttribute = summoner.getAttribute(ModAttributes.COMMAND.get());

                if (commandAttribute != null) {
                    double commandLevel = commandAttribute.getValue();

                    LIFE_MODIFIER.save().putDouble("commandLevel",(commandLevel));
                    STRENGTH_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.02));
                    INTELLIGENCE_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.02));
                    DEFENSE_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.15));
                    AGILITY_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.008));
                    DEXTERITY_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.56));

                    applyModifiers(spawnedEntity);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityTamed(AnimalTameEvent event) {
        LivingEntity animal = event.getAnimal();
        LivingEntity tamer = event.getTamer();

        if (animal != null && tamer != null) {
            AttributeInstance commandAttribute = tamer.getAttribute(ModAttributes.COMMAND.get());

            if (commandAttribute != null) {
                double commandLevel = commandAttribute.getValue();

                LIFE_MODIFIER.save().putDouble("commandLevel",commandLevel);
                STRENGTH_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.02));
                INTELLIGENCE_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.02));
                DEFENSE_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.15));
                AGILITY_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.008));
                DEXTERITY_MODIFIER.save().putDouble("commandLevel",(commandLevel * 0.56));

                applyModifiers(animal);
            }
        }
    }

    private void applyModifiers(LivingEntity entity) {
        AttributeInstance lifeAttribute = entity.getAttribute(ModAttributes.LIFE.get());
        AttributeInstance strengthAttribute = entity.getAttribute(ModAttributes.STRENGTH.get());
        AttributeInstance intelligenceAttribute = entity.getAttribute(ModAttributes.INTELLIGENT.get());
        AttributeInstance defenseAttribute = entity.getAttribute(ModAttributes.DEFENSE.get());
        AttributeInstance agilityAttribute = entity.getAttribute(ModAttributes.AGILITY.get());
        AttributeInstance dexterityAttribute = entity.getAttribute(ModAttributes.DEXTERITY.get());

        if (lifeAttribute != null) {
            lifeAttribute.addPermanentModifier(LIFE_MODIFIER);
        }

        if (strengthAttribute != null) {
            strengthAttribute.addPermanentModifier(STRENGTH_MODIFIER);
        }

        if (intelligenceAttribute != null) {
            intelligenceAttribute.addPermanentModifier(INTELLIGENCE_MODIFIER);
        }

        if (defenseAttribute != null) {
            defenseAttribute.addPermanentModifier(DEFENSE_MODIFIER);
        }

        if (agilityAttribute != null) {
            agilityAttribute.addPermanentModifier(AGILITY_MODIFIER);
        }

        if (dexterityAttribute != null) {
            dexterityAttribute.addPermanentModifier(DEXTERITY_MODIFIER);
        }
    }
}
