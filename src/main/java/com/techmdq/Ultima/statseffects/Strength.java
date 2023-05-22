package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

import static net.minecraft.world.entity.ai.attributes.Attributes.*;

public class Strength {
    
    public Strength(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyStrengthEffectMonster((Monster) entity);
        }else if(entity instanceof Animal){
            applyStrengthEffectAnimal((Animal) entity);
        }else if(entity instanceof Player){
            applyStrengthEffectPlayer((Player) entity);
        }
    }
    AttributeModifier aKnockback = new AttributeModifier("attack_knockback",0.00001D, AttributeModifier.Operation.ADDITION);
    AttributeModifier aDamage = new AttributeModifier("attack_damage", 0.02D, AttributeModifier.Operation.ADDITION);

    public void applyStrengthEffectMonster(@NotNull Monster entity) {
        Objects.requireNonNull(entity.getAttribute(ATTACK_KNOCKBACK)).addPermanentModifier(aKnockback);
        Objects.requireNonNull(entity.getAttribute(ATTACK_DAMAGE)).addPermanentModifier(aDamage);
    }
    public void applyStrengthEffectAnimal(@NotNull Animal entity) {
        Objects.requireNonNull(entity.getAttribute(ATTACK_KNOCKBACK)).addPermanentModifier(aKnockback);
        Objects.requireNonNull(entity.getAttribute(ATTACK_DAMAGE)).addPermanentModifier(aDamage);
    }
    public void applyStrengthEffectPlayer(@NotNull Player entity) {
        Objects.requireNonNull(entity.getAttribute(ATTACK_KNOCKBACK)).addPermanentModifier(aKnockback);
        Objects.requireNonNull(entity.getAttribute(ATTACK_DAMAGE)).addPermanentModifier(aDamage);
    }
}
