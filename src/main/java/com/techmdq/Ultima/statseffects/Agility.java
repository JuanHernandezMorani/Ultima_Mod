package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
            applyAgilityEffect( (LivingEntity) entity, 0.0002D, 0.00002D, 0.000002D);
        } else if (entity instanceof Animal) {
            applyAgilityEffect( (LivingEntity)entity, 0.00002D, 0.002D, 0.0002D);
        } else if (entity instanceof Player) {
            applyAgilityEffect( (LivingEntity)entity, 0.0002D, 0.00002D, 0.000002D);
        }
    }

    public void applyAgilityEffect(@NotNull LivingEntity entity, double jumpStrength, double flySpeed, double moveSpeed) {
        AttributeModifier jump = new AttributeModifier("jump_strength", jumpStrength, AttributeModifier.Operation.ADDITION);
        AttributeModifier fly = new AttributeModifier("fly_speed", flySpeed, AttributeModifier.Operation.ADDITION);
        AttributeModifier move = new AttributeModifier("move_speed", moveSpeed, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(JUMP_STRENGTH)).addPermanentModifier(jump);
        Objects.requireNonNull(entity.getAttribute(FLYING_SPEED)).addPermanentModifier(fly);
        Objects.requireNonNull(entity.getAttribute(MOVEMENT_SPEED)).addPermanentModifier(move);
    }
}