package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.techmdq.Ultima.init.ModAttributes.HPREGEN;
import static net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH;

public class Life {

    public Life(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyLifeEffectMonster((Monster) entity, 1.0D, 0.02D);
        } else if (entity instanceof Animal) {
            applyLifeEffectAnimal((Animal) entity, 2.0D, 0.04D);
        } else if (entity instanceof Player) {
            applyLifeEffectPlayer((Player) entity, 1.0D, 0.02D);
        }
    }

    public void applyLifeEffectMonster(@NotNull Monster entity, double hpModifier, double regenModifier) {
        AttributeModifier hp = new AttributeModifier("life", hpModifier, AttributeModifier.Operation.ADDITION);
        AttributeModifier hpRegen = new AttributeModifier("hp_regen", regenModifier, AttributeModifier.Operation.ADDITION);
        applyModifiers(entity, hp, hpRegen);
    }

    public void applyLifeEffectAnimal(@NotNull Animal entity, double hpModifier, double regenModifier) {
        AttributeModifier hp = new AttributeModifier("life", hpModifier, AttributeModifier.Operation.ADDITION);
        AttributeModifier hpRegen = new AttributeModifier("hp_regen", regenModifier, AttributeModifier.Operation.ADDITION);
        applyModifiers(entity, hp, hpRegen);
    }

    public void applyLifeEffectPlayer(@NotNull Player entity, double hpModifier, double regenModifier) {
        AttributeModifier hp = new AttributeModifier("life", hpModifier, AttributeModifier.Operation.ADDITION);
        AttributeModifier hpRegen = new AttributeModifier("hp_regen", regenModifier, AttributeModifier.Operation.ADDITION);
        applyModifiers(entity, hp, hpRegen);
    }

    private void applyModifiers(@NotNull LivingEntity entity, AttributeModifier hpModifier, AttributeModifier regenModifier) {
        Objects.requireNonNull(entity.getAttribute(MAX_HEALTH)).addPermanentModifier(hpModifier);
        Objects.requireNonNull(entity.getAttribute(HPREGEN.get())).addPermanentModifier(regenModifier);
    }
}

