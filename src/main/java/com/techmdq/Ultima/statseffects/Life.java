package com.techmdq.Ultima.statseffects;

import net.minecraft.world.entity.Entity;
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
            applyLifeEffectMonster((Monster) entity);
        }else if(entity instanceof Animal){
            applyLifeEffectAnimal((Animal) entity);
        }else if(entity instanceof Player){
            applyLifeEffectPlayer((Player) entity);
        }
    }

    public void applyLifeEffectMonster(@NotNull Monster entity) {
        AttributeModifier hp = new AttributeModifier("life",1.0D, AttributeModifier.Operation.ADDITION);
        AttributeModifier hpRegen = new AttributeModifier("hp_regen", 0.02D, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(HPREGEN.get())).addPermanentModifier(hpRegen);
        Objects.requireNonNull(entity.getAttribute(MAX_HEALTH)).addPermanentModifier(hp);
    }
    public void applyLifeEffectAnimal(@NotNull Animal entity) {
        AttributeModifier hp = new AttributeModifier("life",2.0D, AttributeModifier.Operation.ADDITION);
        AttributeModifier hpRegen = new AttributeModifier("hp_regen", 0.04D, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(HPREGEN.get())).addPermanentModifier(hpRegen);
        Objects.requireNonNull(entity.getAttribute(MAX_HEALTH)).addPermanentModifier(hp);
    }
    public void applyLifeEffectPlayer(@NotNull Player entity) {
        AttributeModifier hp = new AttributeModifier("life",1.0D, AttributeModifier.Operation.ADDITION);
        AttributeModifier hpRegen = new AttributeModifier("hp_regen", 0.02D, AttributeModifier.Operation.ADDITION);
        Objects.requireNonNull(entity.getAttribute(HPREGEN.get())).addPermanentModifier(hpRegen);
        Objects.requireNonNull(entity.getAttribute(MAX_HEALTH)).addPermanentModifier(hp);
    }
}
