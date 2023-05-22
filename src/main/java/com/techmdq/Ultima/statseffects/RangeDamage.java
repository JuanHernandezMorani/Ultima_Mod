package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.techmdq.Ultima.init.ModAttributes.RANGEDAMAGE;

public class RangeDamage {
    private final double damage;
    private final double projectileSpeed;
    private final double damageMultiplier;

    RangeDamage(double damage, double projectileSpeed, double damageMultiplier) {
        this.damage = damage;
        this.projectileSpeed = projectileSpeed;
        this.damageMultiplier = damageMultiplier;
    }

    public double getDamage() {
        return damage;
    }

    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public static RangeDamage custom(double damage, double projectileSpeed, double adjustingMultiplier) {
        return new RangeDamage(damage, projectileSpeed, adjustingMultiplier);
    }

    public static void applyCustom(Item item, @Nullable LivingEntity entity) {
        if (entity == null) return;

        double adjustedDamage;
        if (item instanceof BowItem) {
            adjustedDamage = 1 + Objects.requireNonNull(entity.getAttribute(RANGEDAMAGE.get())).getValue();
            custom(adjustedDamage, 3.0, 1.1);
        } else if (item instanceof CrossbowItem) {
            adjustedDamage = 1 + Objects.requireNonNull(entity.getAttribute(RANGEDAMAGE.get())).getValue();
            custom(adjustedDamage, 3.15, 1.6);
        }
    }
}
