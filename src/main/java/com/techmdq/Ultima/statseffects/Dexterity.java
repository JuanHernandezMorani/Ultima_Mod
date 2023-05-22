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

import static com.techmdq.Ultima.init.ModAttributes.RANGEDAMAGE;
import static net.minecraft.world.entity.ai.attributes.Attributes.*;

public class Dexterity {

    public Dexterity(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyDexterityEffectMonster((Monster) entity);
        } else if (entity instanceof Animal) {
            applyDexterityEffectAnimal((Animal) entity);
        } else if (entity instanceof Player) {
            applyDexterityEffectPlayer((Player) entity);
        }
    }

    public static double getRandomDouble(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    private void applyDexterityEffect(@NotNull LivingEntity entity, double rangeDamageValue, double followRangeValue) {
        AttributeModifier rDamage = new AttributeModifier("range_damage", rangeDamageValue, AttributeModifier.Operation.ADDITION);
        AttributeModifier aSpeed = new AttributeModifier("attack_speed", 5.0E-5, AttributeModifier.Operation.ADDITION);
        AttributeModifier fRange = new AttributeModifier("follow_range", followRangeValue, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(RANGEDAMAGE.get())).addPermanentModifier(rDamage);
        Objects.requireNonNull(entity.getAttribute(ATTACK_SPEED)).addPermanentModifier(aSpeed);
        Objects.requireNonNull(entity.getAttribute(FOLLOW_RANGE)).addPermanentModifier(fRange);
    }

    public void applyDexterityEffectMonster(@NotNull Monster entity) {
        applyDexterityEffect(entity, getRandomDouble(0.2D, 1.0D), 0.00009D);
    }

    public void applyDexterityEffectAnimal(@NotNull Animal entity) {
        applyDexterityEffect(entity, getRandomDouble(0.2D, 1.0D), 0.001D);
    }

    public void applyDexterityEffectPlayer(@NotNull Player entity) {
        applyDexterityEffect(entity, getRandomDouble(0.2D, 2.0D), 0.0D);
    }
}
