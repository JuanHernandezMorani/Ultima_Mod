package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.minecraft.world.entity.ai.attributes.Attributes.LUCK;

public class Luck {
    
    public Luck(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyLuckEffectMonster((Monster) entity);
        }else if(entity instanceof Animal){
            applyLuckEffectAnimal((Animal) entity);
        }else if(entity instanceof Player){
            applyLuckEffectPlayer((Player) entity);
        }
    }
    AttributeModifier luck = new AttributeModifier("luck", 0.01D, AttributeModifier.Operation.ADDITION);
    public void applyLuckEffectMonster(@NotNull Monster entity) {
        Objects.requireNonNull(entity.getAttribute(LUCK)).addPermanentModifier(luck);
    }
    public void applyLuckEffectAnimal(@NotNull Animal entity) {
        Objects.requireNonNull(entity.getAttribute(LUCK)).addPermanentModifier(luck);
    }
    public void applyLuckEffectPlayer(@NotNull Player entity) {
        Objects.requireNonNull(entity.getAttribute(LUCK)).addPermanentModifier(luck);
    }
}
