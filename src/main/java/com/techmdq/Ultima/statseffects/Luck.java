package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.minecraft.world.entity.ai.attributes.Attributes.LUCK;

public class Luck {
    private static final AttributeModifier LUCK_MODIFIER = new AttributeModifier("luck", 0.01D, AttributeModifier.Operation.ADDITION);

    public Luck(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyLuckEffectMonster((Monster) entity);
        } else if (entity instanceof Animal) {
            applyLuckEffectAnimal((Animal) entity);
        } else if (entity instanceof Player) {
            applyLuckEffectPlayer((Player) entity);
        }
    }

    public void applyLuckEffectMonster(@NotNull Monster entity) {
        applyModifier(entity);
    }

    public void applyLuckEffectAnimal(@NotNull Animal entity) {
        applyModifier(entity);
    }

    public void applyLuckEffectPlayer(@NotNull Player entity) {
        applyModifier(entity);
    }

    private void applyModifier(@NotNull LivingEntity entity) {
        Objects.requireNonNull(entity.getAttribute(LUCK)).addPermanentModifier(LUCK_MODIFIER);
    }
}

