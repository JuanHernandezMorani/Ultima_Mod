package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.techmdq.Ultima.init.ModAttributes.MAGICDEFENSE;
import static net.minecraft.world.entity.ai.attributes.Attributes.*;

public class Defense {

    private final AttributeModifier knockbackResistance = new AttributeModifier("knockback_resistance_value", 0.0001D, AttributeModifier.Operation.ADDITION);
    private final AttributeModifier magicDefense = new AttributeModifier("magic_defense", 0.01D, AttributeModifier.Operation.ADDITION);

    public Defense(@NotNull Entity entity) {
        if (entity instanceof Monster) {
            applyDefenseEffect((Monster) entity, 0.05D, 2.0D);
        } else if (entity instanceof Animal) {
            applyDefenseEffect((Animal) entity, 0.5D, 0.002D);
        } else if (entity instanceof Player) {
            applyDefenseEffect((Player) entity, 0.02D, 0.1D);
        }
    }

    public void applyDefenseEffect(@NotNull LivingEntity entity, double armorValue, double armorToughnessValue) {
        AttributeModifier armor = new AttributeModifier("armor_value", armorValue, AttributeModifier.Operation.ADDITION);
        AttributeModifier armorToughness = new AttributeModifier("armor_toughness_value", armorToughnessValue, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(ARMOR_TOUGHNESS)).addPermanentModifier(armorToughness);
        Objects.requireNonNull(entity.getAttribute(ARMOR)).addPermanentModifier(armor);
        Objects.requireNonNull(entity.getAttribute(KNOCKBACK_RESISTANCE)).addPermanentModifier(knockbackResistance);
        Objects.requireNonNull(entity.getAttribute(MAGICDEFENSE.get())).addPermanentModifier(magicDefense);
    }
}

