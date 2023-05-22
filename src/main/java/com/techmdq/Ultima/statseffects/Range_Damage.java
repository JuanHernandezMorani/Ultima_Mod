package com.techmdq.Ultima.statseffects;

import com.techmdq.Ultima.init.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.techmdq.Ultima.init.ModAttributes.RANGEDAMAGE;

public class Range_Damage {
    private final double damage;
    private final double projectileSpeed;
    private final double damageMultiplier;

    private Range_Damage(double damage, double projectileSpeed, double damageMultiplier) {
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

    public static final Range_Damage BOW = new Range_Damage(0, 3.0D, 1.1D);
    public static final Range_Damage CROSSBOW = new Range_Damage(0, 3.15D, 1.6D);

    public static void custom(double damage, double projectileSpeed, double adjustingMultiplier) {
        new Range_Damage(damage, projectileSpeed, adjustingMultiplier);
    }

    public static void from(Item item, @Nullable LivingEntity entity) {
        if (item instanceof BowItem && entity != null) {
            double adjustedDamage = BOW.getDamage() + Objects.requireNonNull(entity.getAttribute(RANGEDAMAGE.get())).getValue();
            Range_Damage.custom(adjustedDamage, BOW.getProjectileSpeed(), BOW.getDamageMultiplier());
            return;
        }
        if (item instanceof CrossbowItem && entity != null) {
            double adjustedDamage = CROSSBOW.getDamage() + Objects.requireNonNull(entity.getAttribute(ModAttributes.RANGEDAMAGE.get())).getValue();
            Range_Damage.custom(adjustedDamage, CROSSBOW.getProjectileSpeed(), CROSSBOW.getDamageMultiplier());
        }
    }
}
