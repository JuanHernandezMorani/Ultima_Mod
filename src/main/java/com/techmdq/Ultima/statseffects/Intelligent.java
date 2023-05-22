package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
        } else if (entity instanceof Animal) {
            applyIntelligentEffectAnimal((Animal) entity);
        } else if (entity instanceof Player) {
            applyIntelligentEffectPlayer((Player) entity);
        }
    }

    public static double getRandomDouble(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    AttributeModifier magicDefense = new AttributeModifier("magic_defense", 0.02D, AttributeModifier.Operation.ADDITION);

    private void applyIntelligentEffect(@NotNull LivingEntity entity, double mpValue, double mpRegenValue, double minMagicDamage, double maxMagicDamage) {
        AttributeModifier mp = new AttributeModifier("mana", mpValue, AttributeModifier.Operation.ADDITION);
        AttributeModifier mpRegen = new AttributeModifier("mp_regen", mpRegenValue, AttributeModifier.Operation.ADDITION);
        AttributeModifier magicDamage = new AttributeModifier("magic_damage", getRandomDouble(minMagicDamage, maxMagicDamage), AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(MPREGEN.get())).addPermanentModifier(mpRegen);
        Objects.requireNonNull(entity.getAttribute(MANA.get())).addPermanentModifier(mp);
        Objects.requireNonNull(entity.getAttribute(MAGIC.get())).addPermanentModifier(magicDamage);
        Objects.requireNonNull(entity.getAttribute(MAGICDEFENSE.get())).addPermanentModifier(magicDefense);
    }

    public void applyIntelligentEffectMonster(@NotNull Monster entity) {
        applyIntelligentEffect(entity, 10.0D, 0.28D, 0.2D, 1.2D);
    }

    public void applyIntelligentEffectAnimal(@NotNull Animal entity) {
        applyIntelligentEffect(entity, 1.0D, 0.25D, 0.8D, 4.8D);
    }

    public void applyIntelligentEffectPlayer(@NotNull Player entity) {
        applyIntelligentEffect(entity, 10.0D, 0.2D, 0.1D, 1.5D);
    }
}

