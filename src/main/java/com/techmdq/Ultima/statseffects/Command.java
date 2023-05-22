package com.techmdq.Ultima.statseffects;

import com.techmdq.Ultima.init.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import static com.techmdq.Ultima.init.ModAttributes.*;

public class Command {
    @SubscribeEvent
    public void onSpecialSpawn(LivingSpawnEvent.@NotNull SpecialSpawn event) {
        LivingEntity spawnedEntity = event.getEntity();

        if (spawnedEntity != null) {
            LivingEntity summoner = (LivingEntity) Objects.requireNonNull(event.getSpawner()).getSpawnerEntity();

            if (summoner != null && summoner.getAttribute(ModAttributes.COMMAND.get()) != null) {
                double commandLevel = Objects.requireNonNull(summoner.getAttribute(COMMAND.get())).getValue();

                AttributeModifier lifeModifier = new AttributeModifier("command_life", commandLevel, AttributeModifier.Operation.ADDITION);
                AttributeModifier strengthModifier = new AttributeModifier("command_strength", commandLevel * 0.02, AttributeModifier.Operation.ADDITION);
                AttributeModifier intelligenceModifier = new AttributeModifier("command_intelligence", commandLevel * 0.02, AttributeModifier.Operation.ADDITION);
                AttributeModifier defenseModifier = new AttributeModifier("command_defense", commandLevel * 0.15, AttributeModifier.Operation.ADDITION);
                AttributeModifier agilityModifier = new AttributeModifier("command_agility", commandLevel * 0.008, AttributeModifier.Operation.ADDITION);
                AttributeModifier dexterityModifier = new AttributeModifier("command_dexterity", commandLevel * 0.56, AttributeModifier.Operation.ADDITION);

                Objects.requireNonNull(spawnedEntity.getAttribute(ModAttributes.LIFE.get())).addPermanentModifier(lifeModifier);
                Objects.requireNonNull(spawnedEntity.getAttribute(ModAttributes.STRENGTH.get())).addPermanentModifier(strengthModifier);
                Objects.requireNonNull(spawnedEntity.getAttribute(ModAttributes.INTELLIGENT.get())).addPermanentModifier(intelligenceModifier);
                Objects.requireNonNull(spawnedEntity.getAttribute(ModAttributes.DEFENSE.get())).addPermanentModifier(defenseModifier);
                Objects.requireNonNull(spawnedEntity.getAttribute(ModAttributes.AGILITY.get())).addPermanentModifier(agilityModifier);
                Objects.requireNonNull(spawnedEntity.getAttribute(ModAttributes.DEXTERITY.get())).addPermanentModifier(dexterityModifier);
            }
        }
    }

    @SubscribeEvent
    public void onEntityTamed(@NotNull AnimalTameEvent event) {
        var tamer = event.getTamer();
        if (tamer != null) {
            double commandLevel = Objects.requireNonNull(tamer.getAttribute(COMMAND.get())).getValue();

            AttributeModifier lifeModifier = new AttributeModifier("command_life", commandLevel, AttributeModifier.Operation.ADDITION);
            AttributeModifier strengthModifier = new AttributeModifier("command_strength", commandLevel * 0.02, AttributeModifier.Operation.ADDITION);
            AttributeModifier intelligenceModifier = new AttributeModifier("command_intelligence", commandLevel * 0.02, AttributeModifier.Operation.ADDITION);
            AttributeModifier defenseModifier = new AttributeModifier("command_defense", commandLevel * 0.15, AttributeModifier.Operation.ADDITION);
            AttributeModifier agilityModifier = new AttributeModifier("command_agility", commandLevel * 0.008, AttributeModifier.Operation.ADDITION);
            AttributeModifier dexterityModifier = new AttributeModifier("command_dexterity", commandLevel * 0.56, AttributeModifier.Operation.ADDITION);

            Objects.requireNonNull(event.getAnimal().getAttribute(ModAttributes.LIFE.get())).addPermanentModifier(lifeModifier);
            Objects.requireNonNull(event.getAnimal().getAttribute(ModAttributes.STRENGTH.get())).addPermanentModifier(strengthModifier);
            Objects.requireNonNull(event.getAnimal().getAttribute(ModAttributes.INTELLIGENT.get())).addPermanentModifier(intelligenceModifier);
            Objects.requireNonNull(event.getAnimal().getAttribute(ModAttributes.DEFENSE.get())).addPermanentModifier(defenseModifier);
            Objects.requireNonNull(event.getAnimal().getAttribute(ModAttributes.AGILITY.get())).addPermanentModifier(agilityModifier);
            Objects.requireNonNull(event.getAnimal().getAttribute(ModAttributes.DEXTERITY.get())).addPermanentModifier(dexterityModifier);
        }
    }
}
