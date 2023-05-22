package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.minecraft.world.entity.ai.attributes.Attributes.*;

public class Agility {

    public Agility(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyAgilityEffectMonster((Monster) entity);
        }else if(entity instanceof Animal){
            applyAgilityEffectAnimal((Animal) entity);
        }else if(entity instanceof Player){
            applyAgilityEffectPlayer((Player) entity);
        }
    }
          
    public void applyAgilityEffectMonster(@NotNull Monster entity) {
        AttributeModifier jump = new AttributeModifier("jump_strength",0.0002D, AttributeModifier.Operation.ADDITION);
        AttributeModifier fly = new AttributeModifier("fly_speed", 0.00002D, AttributeModifier.Operation.ADDITION);
        AttributeModifier move = new AttributeModifier("move_speed", 0.000002D, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(JUMP_STRENGTH)).addPermanentModifier(jump);
        Objects.requireNonNull(entity.getAttribute(FLYING_SPEED)).addPermanentModifier(fly);
        Objects.requireNonNull(entity.getAttribute(MOVEMENT_SPEED)).addPermanentModifier(move);

    }
    public void applyAgilityEffectAnimal(@NotNull Animal entity) {
        AttributeModifier jump = new AttributeModifier("jump_strength",0.00002D, AttributeModifier.Operation.ADDITION);
        AttributeModifier fly = new AttributeModifier("fly_speed", 0.002D, AttributeModifier.Operation.ADDITION);
        AttributeModifier move = new AttributeModifier("move_speed", 0.0002D, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(JUMP_STRENGTH)).addPermanentModifier(jump);
        Objects.requireNonNull(entity.getAttribute(FLYING_SPEED)).addPermanentModifier(fly);
        Objects.requireNonNull(entity.getAttribute(MOVEMENT_SPEED)).addPermanentModifier(move);
    }
    public void applyAgilityEffectPlayer(@NotNull Player entity) {
        AttributeModifier jump = new AttributeModifier("jump_strength",0.0002D, AttributeModifier.Operation.ADDITION);
        AttributeModifier fly = new AttributeModifier("fly_speed", 0.00002D, AttributeModifier.Operation.ADDITION);
        AttributeModifier move = new AttributeModifier("move_speed", 0.000002D, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(JUMP_STRENGTH)).addPermanentModifier(jump);
        Objects.requireNonNull(entity.getAttribute(FLYING_SPEED)).addPermanentModifier(fly);
        Objects.requireNonNull(entity.getAttribute(MOVEMENT_SPEED)).addPermanentModifier(move);
    }
}
