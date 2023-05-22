package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.techmdq.Ultima.init.ModAttributes.MAGICDEFENSE;
import static net.minecraft.world.entity.ai.attributes.Attributes.*;

public class Defense {
    
    public Defense(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyDefenseEffectMonster((Monster) entity);
        }else if(entity instanceof Animal){
            applyDefenseEffectAnimal((Animal) entity);
        }else if(entity instanceof Player){
            applyDefenseEffectPlayer((Player) entity);
        }
    }
    AttributeModifier magicDefense = new AttributeModifier("magic_defense", 0.01D, AttributeModifier.Operation.ADDITION);
    public void applyDefenseEffectMonster(@NotNull Monster entity) {
        AttributeModifier rChance = new AttributeModifier("reinforcements_chance",0.001D, AttributeModifier.Operation.ADDITION);
        AttributeModifier armor = new AttributeModifier("armor_value",0.05D, AttributeModifier.Operation.ADDITION);
        AttributeModifier armorT = new AttributeModifier("armor_toughness_value",2.0D, AttributeModifier.Operation.ADDITION);
        AttributeModifier kResistance = new AttributeModifier("knockback_resistance_value",0.0001D, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(ARMOR_TOUGHNESS)).addPermanentModifier(armorT);
        Objects.requireNonNull(entity.getAttribute(ARMOR)).addPermanentModifier(armor);
        Objects.requireNonNull(entity.getAttribute(KNOCKBACK_RESISTANCE)).addPermanentModifier(kResistance);
        Objects.requireNonNull(entity.getAttribute(SPAWN_REINFORCEMENTS_CHANCE)).addPermanentModifier(rChance);
        Objects.requireNonNull(entity.getAttribute(MAGICDEFENSE.get())).addPermanentModifier(magicDefense);
    }
    public void applyDefenseEffectAnimal(@NotNull Animal entity) {
        AttributeModifier armor = new AttributeModifier("armor_value",0.5D, AttributeModifier.Operation.ADDITION);
        AttributeModifier armorT = new AttributeModifier("armor_toughness_value",0.002D, AttributeModifier.Operation.ADDITION);
        AttributeModifier kResistance = new AttributeModifier("knockback_resistance_value",0.0001D, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(ARMOR_TOUGHNESS)).addPermanentModifier(armorT);
        Objects.requireNonNull(entity.getAttribute(ARMOR)).addPermanentModifier(armor);
        Objects.requireNonNull(entity.getAttribute(KNOCKBACK_RESISTANCE)).addPermanentModifier(kResistance);
        Objects.requireNonNull(entity.getAttribute(MAGICDEFENSE.get())).addPermanentModifier(magicDefense);
    }
    public void applyDefenseEffectPlayer(@NotNull Player entity) {
        AttributeModifier armor = new AttributeModifier("armor_value",0.02D, AttributeModifier.Operation.ADDITION);
        AttributeModifier armorT = new AttributeModifier("armor_toughness_value",0.1D, AttributeModifier.Operation.ADDITION);
        AttributeModifier kResistance = new AttributeModifier("knockback_resistance_value",0.001D, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(ARMOR_TOUGHNESS)).addPermanentModifier(armorT);
        Objects.requireNonNull(entity.getAttribute(ARMOR)).addPermanentModifier(armor);
        Objects.requireNonNull(entity.getAttribute(KNOCKBACK_RESISTANCE)).addPermanentModifier(kResistance);
        Objects.requireNonNull(entity.getAttribute(MAGICDEFENSE.get())).addPermanentModifier(magicDefense);
    }

}
