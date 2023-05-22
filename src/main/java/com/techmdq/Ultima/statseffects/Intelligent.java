package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

import static com.techmdq.Ultima.init.ModAttributes.*;

public class Intelligent {

    public Intelligent(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyIntelligentEffectMonster((Monster) entity);
        }else if(entity instanceof Animal){
            applyIntelligentEffectAnimal((Animal) entity);
        }else if(entity instanceof Player){
            applyIntelligentEffectPlayer((Player) entity);
        }
    }
    public static double getRandomDouble(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
    AttributeModifier magicDefense = new AttributeModifier("magic_defense", 0.02D, AttributeModifier.Operation.ADDITION);

    public void applyIntelligentEffectMonster(@NotNull Monster entity) {
        AttributeModifier mp = new AttributeModifier("mana",10.0D, AttributeModifier.Operation.ADDITION);
        AttributeModifier mpRegen = new AttributeModifier("mp_regen", 0.2D, AttributeModifier.Operation.ADDITION);
        AttributeModifier magicDamage = new AttributeModifier("magic_damage", getRandomDouble(0.2, 1.2), AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(MPREGEN.get())).addPermanentModifier(mpRegen);
        Objects.requireNonNull(entity.getAttribute(MANA.get())).addPermanentModifier(mp);
        Objects.requireNonNull(entity.getAttribute(MAGIC.get())).addPermanentModifier(magicDamage);
        Objects.requireNonNull(entity.getAttribute(MAGICDEFENSE.get())).addPermanentModifier(magicDefense);
    }
    public void applyIntelligentEffectAnimal(@NotNull Animal entity) {
        AttributeModifier mp = new AttributeModifier("mana",1.0D, AttributeModifier.Operation.ADDITION);
        AttributeModifier mpRegen = new AttributeModifier("mp_regen", 0.2D, AttributeModifier.Operation.ADDITION);
        AttributeModifier magicDamage = new AttributeModifier("magic_damage", getRandomDouble(0.8,4.8), AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(MPREGEN.get())).addPermanentModifier(mpRegen);
        Objects.requireNonNull(entity.getAttribute(MANA.get())).addPermanentModifier(mp);
        Objects.requireNonNull(entity.getAttribute(MAGIC.get())).addPermanentModifier(magicDamage);
        Objects.requireNonNull(entity.getAttribute(MAGICDEFENSE.get())).addPermanentModifier(magicDefense);
    }
    public void applyIntelligentEffectPlayer(@NotNull Player entity) {
        AttributeModifier mp = new AttributeModifier("mana",10.0D, AttributeModifier.Operation.ADDITION);
        AttributeModifier mpRegen = new AttributeModifier("mp_regen", 0.2D, AttributeModifier.Operation.ADDITION);
        AttributeModifier magicDamage = new AttributeModifier("magic_damage", getRandomDouble(0.1, 1.5), AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(MPREGEN.get())).addPermanentModifier(mpRegen);
        Objects.requireNonNull(entity.getAttribute(MANA.get())).addPermanentModifier(mp);
        Objects.requireNonNull(entity.getAttribute(MAGIC.get())).addPermanentModifier(magicDamage);
        Objects.requireNonNull(entity.getAttribute(MAGICDEFENSE.get())).addPermanentModifier(magicDefense);
    }
}
