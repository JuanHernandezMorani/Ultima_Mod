package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE;
import static net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK;

public class Strength {
    private static final AttributeModifier ATTACK_KNOCKBACK_MODIFIER = new AttributeModifier("attack_knockback", 0.00001D, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier ATTACK_DAMAGE_MODIFIER = new AttributeModifier("attack_damage", 0.02D, AttributeModifier.Operation.ADDITION);

    public Strength(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyStrengthEffectMonster((Monster) entity);
        } else if (entity instanceof Animal) {
            applyStrengthEffectAnimal((Animal) entity);
        } else if (entity instanceof Player) {
            applyStrengthEffectPlayer((Player) entity);
        }
    }

    public void applyStrengthEffectMonster(@NotNull Monster entity) {
        applyModifiers(entity);
    }

    public void applyStrengthEffectAnimal(@NotNull Animal entity) {
        applyModifiers(entity);
    }

    public void applyStrengthEffectPlayer(@NotNull Player entity) {
        applyModifiers(entity);
    }

    private void applyModifiers(@NotNull LivingEntity entity) {
        Objects.requireNonNull(entity.getAttribute(ATTACK_KNOCKBACK)).addPermanentModifier(ATTACK_KNOCKBACK_MODIFIER);
        Objects.requireNonNull(entity.getAttribute(ATTACK_DAMAGE)).addPermanentModifier(ATTACK_DAMAGE_MODIFIER);
    }
}
