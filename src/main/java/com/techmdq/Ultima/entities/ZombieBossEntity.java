package com.techmdq.Ultima.entities;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = "ultima", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ZombieBossEntity extends Zombie {

    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = (p_31504_) -> p_31504_.getMobType() != MobType.UNDEAD && p_31504_.attackable();
    BossEvent.BossBarColor barColor;
    private static final int DIAMOND_COUNT = 32;
    private static final int IRON_INGOT_COUNT = 32;
    private static final int GOLD_INGOT_COUNT = 32;
    private static final int OBSIDIAN_COUNT = 16;
    private static double playerCount = 1.0D;

    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(Component.literal("[Zombie Boss] Swyx, el gran comandante caído"), ServerBossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
    private final ServerBossEvent.BossBarColor nextColor;
    private boolean revived = false;
    AttributeModifier healthModifier = new AttributeModifier("HealthModifier", 5, AttributeModifier.Operation.ADDITION);
    AttributeModifier movementSpeedModifier = new AttributeModifier("MovementSpeedModifier", 0.2, AttributeModifier.Operation.ADDITION);
    AttributeModifier attackSpeedModifier = new AttributeModifier("AttackSpeedModifier", 1, AttributeModifier.Operation.ADDITION);
    AttributeModifier attackDamageModifier = new AttributeModifier("AttackDamageModifier", 6, AttributeModifier.Operation.ADDITION);
    AttributeModifier armorToughnessModifier = new AttributeModifier("ArmorToughnessModifier", 3, AttributeModifier.Operation.ADDITION);
    AttributeModifier armorModifier = new AttributeModifier("ArmorModifier", 5, AttributeModifier.Operation.ADDITION);
    private static final double MAX_HEALTH = 120.0D*playerCount;
    private boolean isAttacking;
    public ZombieBossEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.bossEvent.setPlayBossMusic(true);
        this.bossEvent.shouldPlayBossMusic();
        this.bossEvent.setProgress(1.0F);
        this.shouldDespawnInPeaceful();
        this.xpReward = 12000;
        playerCount = getConnectedPlayerCount();

        AttributeSupplier.Builder attributeBuilder = createAttributes();
        attributeBuilder.add(Attributes.MAX_HEALTH, (120.0D * playerCount))
                .add(Attributes.MOVEMENT_SPEED, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.ARMOR, 25.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 5.0D)
                .add(Attributes.ATTACK_SPEED, 0.6D);
        AttributeSupplier attributes = attributeBuilder.build();
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(attributes.getValue(Attributes.MAX_HEALTH));
        Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(attributes.getValue(Attributes.MOVEMENT_SPEED));
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(attributes.getValue(Attributes.ATTACK_DAMAGE));
        Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(attributes.getValue(Attributes.ARMOR));
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_KNOCKBACK)).setBaseValue(attributes.getValue(Attributes.ATTACK_KNOCKBACK));
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_SPEED)).setBaseValue(attributes.getValue(Attributes.ATTACK_SPEED));
        this.addRandomEquipment();
        this.nextColor = ServerBossEvent.BossBarColor.YELLOW;
        this.barColor = ServerBossEvent.BossBarColor.GREEN;
        this.bossEvent.setColor(barColor);
        MinecraftForge.EVENT_BUS.register(this);
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.0D, true) {
            @Override
            public void start() {
                super.start();
                setAttacking(true);
            }

            @Override
            public void stop() {
                super.stop();
                setAttacking(false);
            }
        };
        this.goalSelector.addGoal(8, meleeAttackGoal);
        this.goalSelector.addGoal(9, meleeAttackGoal);
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
    }
    private void addRandomEquipment() {
        this.setItemSlot(EquipmentSlot.MAINHAND, addEnchantments(new ItemStack(Items.NETHERITE_SWORD),"MAINHAND"));
        this.setItemSlot(EquipmentSlot.OFFHAND, addEnchantments(new ItemStack(Items.SHIELD),"OFFHAND"));
        this.setItemSlot(EquipmentSlot.HEAD, addEnchantments(new ItemStack(Items.NETHERITE_HELMET),"HEAD"));
        this.setItemSlot(EquipmentSlot.CHEST, addEnchantments(new ItemStack(Items.NETHERITE_CHESTPLATE),"CHEST"));
        this.setItemSlot(EquipmentSlot.LEGS, addEnchantments(new ItemStack(Items.NETHERITE_LEGGINGS),"LEGS"));
        this.setItemSlot(EquipmentSlot.FEET, addEnchantments(new ItemStack(Items.NETHERITE_BOOTS),"FEET"));
    }
    public void setCustomName(@Nullable Component p_31476_) {
        super.setCustomName(p_31476_);
        this.bossEvent.setName(this.getDisplayName());
    }
    public void startSeenByPlayer(@NotNull ServerPlayer p_31483_) {
        super.startSeenByPlayer(p_31483_);
        this.bossEvent.addPlayer(p_31483_);
    }
    public void stopSeenByPlayer(@NotNull ServerPlayer p_31488_) {
        super.stopSeenByPlayer(p_31488_);
        this.bossEvent.removePlayer(p_31488_);
    }
    public boolean canChangeDimensions() {
        return true;
    }
    @Contract("_, _ -> param1")
    private ItemStack addEnchantments(ItemStack itemStack, @NotNull String slot) {
        switch (slot) {
            case "MAINHAND" -> {
                itemStack.enchant(Enchantments.SHARPNESS, Enchantments.SHARPNESS.getMaxLevel());
                itemStack.enchant(Enchantments.UNBREAKING, Enchantments.UNBREAKING.getMaxLevel());
                itemStack.enchant(Enchantments.FIRE_ASPECT, Enchantments.FIRE_ASPECT.getMaxLevel());
            }
            case "OFFHAND" -> {
                itemStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.ALL_DAMAGE_PROTECTION.getMaxLevel());
                itemStack.enchant(Enchantments.FIRE_PROTECTION, Enchantments.FIRE_PROTECTION.getMaxLevel());
                itemStack.enchant(Enchantments.PROJECTILE_PROTECTION, Enchantments.PROJECTILE_PROTECTION.getMaxLevel());
                itemStack.enchant(Enchantments.BLAST_PROTECTION, Enchantments.BLAST_PROTECTION.getMaxLevel());
                itemStack.enchant(Enchantments.THORNS, Enchantments.THORNS.getMaxLevel());
            }
            case "HEAD", "CHEST", "LEGS", "FEET" -> {
                itemStack.enchant(Enchantments.THORNS, Enchantments.THORNS.getMaxLevel());
                itemStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.ALL_DAMAGE_PROTECTION.getMaxLevel());
                itemStack.enchant(Enchantments.FIRE_PROTECTION, Enchantments.FIRE_PROTECTION.getMaxLevel());
                itemStack.enchant(Enchantments.PROJECTILE_PROTECTION, Enchantments.PROJECTILE_PROTECTION.getMaxLevel());
                itemStack.enchant(Enchantments.BLAST_PROTECTION, Enchantments.BLAST_PROTECTION.getMaxLevel());
            }
            default -> {
            }
        }

        return itemStack;
    }
    @Override
    protected boolean shouldDropLoot() {
        return true;
    }
    public boolean isAttacking() {
        return isAttacking;
    }
    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
    public ItemStack dropCustomDeathLoot() {
        List<ItemStack> customLoots = new ArrayList<>();

        if (this.random.nextFloat() <= 0.33F) {
            ItemStack helmet = new ItemStack(Items.NETHERITE_HELMET);
            int count = 0;

            if (this.random.nextFloat() <= 0.6F) {
                if (this.random.nextFloat() <= 0.1667F) {
                    helmet.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.HEAD);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    helmet.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.HEAD);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    helmet.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.HEAD);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    helmet.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.HEAD);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    helmet.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.HEAD);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    helmet.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.HEAD);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    helmet.enchant(Enchantments.BLAST_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    helmet.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    helmet.enchant(Enchantments.FIRE_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    helmet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.05F) {
                    this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 1));
                    count++;
                }
                if (this.random.nextFloat() <= 0.05F) {
                    this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 1));
                    count++;
                }
            } else {
                count = -1;
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 1));
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 1));
                helmet.enchant(Enchantments.FIRE_PROTECTION, 10);
                helmet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                helmet.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                helmet.enchant(Enchantments.BLAST_PROTECTION, 10);
                helmet.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.HEAD);
                helmet.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.HEAD);
                helmet.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.HEAD);
                helmet.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.HEAD);
                helmet.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.HEAD);
                helmet.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.HEAD);
            }

            helmet.enchant(Enchantments.UNBREAKING, 50);

            String itemName;
            if (count == -1) {
                itemName = "§eCasco del comandante zombie(✵✵✵✵✵)";
            } else if (count > 2 && count < 5) {
                itemName = "§eCasco del comandante zombie(✵)";
            } else if (count >= 5 && count < 8) {
                itemName = "§eCasco del comandante zombie(✵✵)";
            } else if (count >= 9 && count < 11) {
                itemName = "§eCasco del comandante zombie(✵✵✵)";
            } else if (count >= 11) {
                itemName = "§eCasco del comandante zombie(✵✵✵✵)";
            } else {
                itemName = "§eCasco del comandante zombie";
            }

            setItemNameAndLore(helmet, itemName,
                    "§7La legendaria armadura que utilizó un gran comandante",
                    "§7antes de sucumbir ante la infección");

            customLoots.add(helmet);
        }

        if (this.random.nextFloat() <= 0.25F) {
            ItemStack chestplate = new ItemStack(Items.NETHERITE_CHESTPLATE);
            int count = 0;

            if (this.random.nextFloat() <= 0.6F) {
                if (this.random.nextFloat() <= 0.1667F) {
                    chestplate.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.CHEST);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    chestplate.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.CHEST);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    chestplate.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.CHEST);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    chestplate.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.CHEST);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    chestplate.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.CHEST);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    chestplate.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.CHEST);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    chestplate.enchant(Enchantments.BLAST_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    chestplate.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    chestplate.enchant(Enchantments.FIRE_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    chestplate.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.05F) {
                    this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Integer.MAX_VALUE, 3));
                    count++;
                }
                if (this.random.nextFloat() <= 0.05F) {
                    this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 3));
                    count++;
                }
            } else {
                count = -1;
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 3));
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Integer.MAX_VALUE, 3));
                chestplate.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                chestplate.enchant(Enchantments.FIRE_PROTECTION, 10);
                chestplate.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                chestplate.enchant(Enchantments.BLAST_PROTECTION, 10);
                chestplate.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.CHEST);
                chestplate.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.CHEST);
                chestplate.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.CHEST);
                chestplate.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.CHEST);
                chestplate.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.CHEST);
                chestplate.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.CHEST);
            }

            chestplate.enchant(Enchantments.UNBREAKING, 50);

            String itemName;
            if (count == -1) {
                itemName = "§ePechera del comandante zombie(✵✵✵✵✵)";
            } else if (count > 2 && count < 5) {
                itemName = "§ePechera del comandante zombie(✵)";
            } else if (count >= 5 && count < 8) {
                itemName = "§ePechera del comandante zombie(✵✵)";
            } else if (count >= 9 && count < 11) {
                itemName = "§ePechera del comandante zombie(✵✵✵)";
            } else if (count >= 11) {
                itemName = "§ePechera del comandante zombie(✵✵✵✵)";
            } else {
                itemName = "§ePechera del comandante zombie";
            }

            setItemNameAndLore(chestplate, itemName,
                    "§7La legendaria armadura que utilizó un gran comandante",
                    "§7antes de sucumbir ante la infección");

            customLoots.add(chestplate);
        }

        if (this.random.nextFloat() <= 0.5F) {
            ItemStack leggings = new ItemStack(Items.NETHERITE_LEGGINGS);
            int count = 0;

            if (this.random.nextFloat() <= 0.6F) {
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    leggings.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    leggings.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    leggings.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    leggings.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    leggings.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    leggings.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1F) {
                    count++;
                    leggings.enchant(Enchantments.BLAST_PROTECTION, 10);
                }
                if (this.random.nextFloat() <= 0.1F) {
                    count++;
                    leggings.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                }
                if (this.random.nextFloat() <= 0.1F) {
                    count++;
                    leggings.enchant(Enchantments.FIRE_PROTECTION, 10);
                }
                if (this.random.nextFloat() <= 0.1F) {
                    count++;
                    leggings.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                }
                if (this.random.nextFloat() <= 0.05F) {
                    count++;
                    this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
                }
                if (this.random.nextFloat() <= 0.05F) {
                    count++;
                    this.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, Integer.MAX_VALUE, 1));
                }
            } else {
                count = -1;
                this.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, Integer.MAX_VALUE, 1));
                this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
                leggings.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                leggings.enchant(Enchantments.FIRE_PROTECTION, 10);
                leggings.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                leggings.enchant(Enchantments.BLAST_PROTECTION, 10);
                leggings.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.CHEST);
                leggings.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.CHEST);
                leggings.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.CHEST);
                leggings.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.CHEST);
                leggings.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.CHEST);
                leggings.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.CHEST);
            }

            leggings.enchant(Enchantments.UNBREAKING, 50);

            String itemName;
            if (count == -1) {
                itemName = "§ePantalon del comandante zombie(✵✵✵✵✵)";
            } else if (count > 2 && count < 5) {
                itemName = "§ePantalon del comandante zombie(✵)";
            } else if (count >= 5 && count < 8) {
                itemName = "§ePantalon del comandante zombie(✵✵)";
            } else if (count >= 9 && count < 11) {
                itemName = "§ePantalon del comandante zombie(✵✵✵)";
            } else if (count >= 11) {
                itemName = "§ePantalon del comandante zombie(✵✵✵✵)";
            } else {
                itemName = "§ePantalon del comandante zombie";
            }

            setItemNameAndLore(leggings, itemName,
                    "§7La legendaria armadura que utilizó un gran comandante",
                    "§7antes de sucumbir ante la infección");

            customLoots.add(leggings);
        }

        if (this.random.nextFloat() <= 0.83F) {
            ItemStack boots = new ItemStack(Items.NETHERITE_BOOTS);
            int count = 0;

            if (this.random.nextFloat() <= 0.6F) {
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    boots.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    boots.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    boots.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    boots.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    boots.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    count++;
                    boots.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.CHEST);
                }
                if (this.random.nextFloat() <= 0.1F) {
                    count++;
                    boots.enchant(Enchantments.BLAST_PROTECTION, 10);
                }
                if (this.random.nextFloat() <= 0.1F) {
                    count++;
                    boots.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                }
                if (this.random.nextFloat() <= 0.1F) {
                    count++;
                    boots.enchant(Enchantments.FIRE_PROTECTION, 10);
                }
                if (this.random.nextFloat() <= 0.1F) {
                    count++;
                    boots.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                }
                if (this.random.nextFloat() <= 0.05F) {
                    count++;
                    this.addEffect(new MobEffectInstance(MobEffects.JUMP, Integer.MAX_VALUE, 2));
                }
                if (this.random.nextFloat() <= 0.05F) {
                    count++;
                    this.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE, 1));
                }
            } else {
                count = -1;
                this.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, Integer.MAX_VALUE, 1));
                this.addEffect(new MobEffectInstance(MobEffects.JUMP, Integer.MAX_VALUE, 2));
                boots.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                boots.enchant(Enchantments.FIRE_PROTECTION, 10);
                boots.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                boots.enchant(Enchantments.BLAST_PROTECTION, 10);
                boots.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.CHEST);
                boots.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.CHEST);
                boots.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.CHEST);
                boots.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.CHEST);
                boots.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.CHEST);
                boots.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.CHEST);
            }

            boots.enchant(Enchantments.UNBREAKING, 50);

            String itemName;
            if (count == -1) {
                itemName = "§eBotas del comandante zombie(✵✵✵✵✵)";
            } else if (count > 2 && count < 5) {
                itemName = "§eBotas del comandante zombie(✵)";
            } else if (count >= 5 && count < 8) {
                itemName = "§eBotas del comandante zombie(✵✵)";
            } else if (count >= 9 && count < 11) {
                itemName = "§eBotas del comandante zombie(✵✵✵)";
            } else if (count >= 11) {
                itemName = "§eBotas del comandante zombie(✵✵✵✵)";
            } else {
                itemName = "§eBotas del comandante zombie";
            }

            setItemNameAndLore(boots, itemName,
                    "§7La legendaria armadura que utilizó un gran comandante",
                    "§7antes de sucumbir ante la infección");

            customLoots.add(boots);
        }

        if (this.random.nextFloat() <= 0.424F) {
            ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
            int count = 0;

            if (this.random.nextFloat() <= 0.6F) {
                if (this.random.nextFloat() <= 0.1667F) {
                    sword.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.MAINHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    sword.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.MAINHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    sword.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.MAINHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    sword.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.MAINHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    sword.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.MAINHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    sword.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.MAINHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    sword.enchant(Enchantments.MOB_LOOTING, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    sword.enchant(Enchantments.BANE_OF_ARTHROPODS, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    sword.enchant(Enchantments.SMITE, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    sword.enchant(Enchantments.SHARPNESS, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.05F) {
                    this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Integer.MAX_VALUE, 3));
                    count++;
                }
                if (this.random.nextFloat() <= 0.05F) {
                    this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 3));
                    count++;
                }
            } else {
                count = -1;
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 3));
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Integer.MAX_VALUE, 3));
                sword.enchant(Enchantments.SHARPNESS, 10);
                sword.enchant(Enchantments.SMITE, 10);
                sword.enchant(Enchantments.BANE_OF_ARTHROPODS, 10);
                sword.enchant(Enchantments.MOB_LOOTING, 10);
                sword.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.MAINHAND);
                sword.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.MAINHAND);
                sword.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.MAINHAND);
                sword.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.MAINHAND);
                sword.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.MAINHAND);
                sword.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.MAINHAND);
            }

            sword.enchant(Enchantments.UNBREAKING, 50);

            String itemName;
            if (count == -1) {
                itemName = "§eLa espada del comandante zombie (✵✵✵✵✵)";
            } else if (count > 2 && count < 5) {
                itemName = "§eLa espada del comandante zombie(✵)";
            } else if (count >= 5 && count < 8) {
                itemName = "§eLa espada del comandante zombie(✵✵)";
            } else if (count >= 9 && count < 11) {
                itemName = "§eLa espada del comandante zombie(✵✵✵)";
            } else if (count >= 11) {
                itemName = "§eLa espada del comandante zombie(✵✵✵✵)";
            } else {
                itemName = "§eLa espada del comandante zombie";
            }

            setItemNameAndLore(sword, itemName,
                    "§7La gran espada del comandante, cuenta la leyenda, que se la dio el mismísimo NOTCH",
                    "§7para enfrentarse al temible Herobrine hace mucho tiempo atrás.");

            customLoots.add(sword);
        }

        if (this.random.nextFloat() <= 0.65F) {
            ItemStack shield = new ItemStack(Items.SHIELD);

            int count = 0;

            if (this.random.nextFloat() <= 0.6F) {
                if (this.random.nextFloat() <= 0.1667F) {
                    shield.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.OFFHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    shield.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.OFFHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    shield.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.OFFHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    shield.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.OFFHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    shield.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.OFFHAND);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1667F) {
                    shield.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.CHEST);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    shield.enchant(Enchantments.BLAST_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    shield.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    shield.enchant(Enchantments.FIRE_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.1F) {
                    shield.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                    count++;
                }
                if (this.random.nextFloat() <= 0.05F) {
                    this.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, Integer.MAX_VALUE, 3));
                    count++;
                }
                if (this.random.nextFloat() <= 0.05F) {
                    this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Integer.MAX_VALUE, 1));
                    count++;
                }
            } else {
                count = -1;
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Integer.MAX_VALUE, 1));
                this.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, Integer.MAX_VALUE, 3));
                shield.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
                shield.enchant(Enchantments.FIRE_PROTECTION, 10);
                shield.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
                shield.enchant(Enchantments.BLAST_PROTECTION, 10);
                shield.addAttributeModifier(Attributes.ARMOR, armorModifier, EquipmentSlot.OFFHAND);
                shield.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, armorToughnessModifier, EquipmentSlot.OFFHAND);
                shield.addAttributeModifier(Attributes.ATTACK_DAMAGE, attackDamageModifier, EquipmentSlot.OFFHAND);
                shield.addAttributeModifier(Attributes.ATTACK_SPEED, attackSpeedModifier, EquipmentSlot.OFFHAND);
                shield.addAttributeModifier(Attributes.MOVEMENT_SPEED, movementSpeedModifier, EquipmentSlot.OFFHAND);
                shield.addAttributeModifier(Attributes.MAX_HEALTH, healthModifier, EquipmentSlot.OFFHAND);
            }

            shield.enchant(Enchantments.UNBREAKING, 50);

            String itemName;
            if (count == -1) {
                itemName = "§eEl gran escudo del comandante zombie(✵✵✵✵✵)";
            } else if (count > 2 && count < 5) {
                itemName = "§eEl gran escudo del comandante zombie(✵)";
            } else if (count >= 5 && count < 8) {
                itemName = "§eEl gran escudo del comandante zombie(✵✵)";
            } else if (count >= 9 && count < 11) {
                itemName = "§eEl gran escudo del comandante zombie(✵✵✵)";
            } else if (count >= 11) {
                itemName = "§eEl gran escudo del comandante zombie(✵✵✵✵)";
            } else {
                itemName = "§eEl gran escudo del comandante zombie";
            }

            setItemNameAndLore(shield, itemName,
                    "§7El mejor escudo creado por una raza desconocida en tiempos antiguos,",
                    "§7tan fuerte que resistió ataques del Wither, y se dice que de otro ente aun más fuerte...");

            customLoots.add(shield);
        }


        ItemStack faro = new ItemStack(Items.BEACON);
        ItemStack nethB = new ItemStack(Blocks.NETHERITE_BLOCK);
        ItemStack estrella = new ItemStack(Items.NETHER_STAR);

        customLoots.add(faro);
        customLoots.add(nethB);
        customLoots.add(estrella);

        return customLoots.get(new Random().nextInt(customLoots.size()));
    }
    private void setItemNameAndLore(@NotNull ItemStack itemStack, String itemName, String @NotNull ... lore) {
        CompoundTag displayTag = itemStack.getOrCreateTagElement("display");


        Component iQualityComponent = Component.literal("§b[Boss-Item]").withStyle(ChatFormatting.AQUA);
        displayTag.putString("Name", Component.Serializer.toJson(iQualityComponent));

        Component iNameComponent = Component.literal(itemName).withStyle(ChatFormatting.GOLD);
        displayTag.putString("Name", Component.Serializer.toJson(iNameComponent));

        ListTag loreTag = new ListTag();
        for (String line : lore) {
            Component loreComponent = Component.literal(line).withStyle(ChatFormatting.LIGHT_PURPLE);
            StringTag loreStringTag = StringTag.valueOf(Component.Serializer.toJson(loreComponent));
            loreTag.add(loreStringTag);
        }
        displayTag.put("Lore", loreTag);
    }
    @Override
    public boolean shouldDropExperience() {
        return true;
    }
    @Override
    public boolean isPersistenceRequired() {
        return true;
    }
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 16) {
            this.spawnParticles();
        } else {
            super.handleEntityEvent(id);
        }
    }
    private void spawnParticles() {
        if (this.level instanceof ServerLevel serverWorld) {
            for (int i = 0; i < 5; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                serverWorld.sendParticles((ParticleOptions) ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getRandomY(), this.getZ(), 1, d0, d1, d2, 0.1D);
            }
        }
    }
    public double getConnectedPlayerCount() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            return server.getPlayerCount();
        }
        return 1;
    }
    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, (120.0D * playerCount))
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ARMOR, 25.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 5.0D)
                .add(Attributes.ATTACK_SPEED, 0.3D);
    }
    public boolean checkSpawnObstruction(@NotNull LevelReader levelReader) {
        return levelReader.isUnobstructed(this);
    }
    public static void onLivingDeath(@NotNull DamageSource event) {
        if (event.getEntity() instanceof ZombieBossEntity bossEntity) {
            bossEntity.bossEvent.removeAllPlayers();
        }
    }
    private void dropChest(NonNullList<ItemStack> chest) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        this.level.setBlockAndUpdate(new BlockPos(x, y, z), Blocks.CHEST.defaultBlockState());
        BlockEntity blockEntity = this.level.getBlockEntity(new BlockPos(x, y, z));
        if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
            for (int slot = 0; slot < chest.size(); slot++) {
                ItemStack stack = chest.get(slot);
                if (!stack.isEmpty()) {
                    chestBlockEntity.setItem(slot, stack);
                }
            }
        }
    }
    private @NotNull NonNullList<ItemStack> createChestContents() {
        NonNullList<ItemStack> chest = NonNullList.create();
        chest.add(new ItemStack(Items.DIAMOND, DIAMOND_COUNT));
        chest.add(new ItemStack(Items.IRON_INGOT, IRON_INGOT_COUNT));
        chest.add(new ItemStack(Items.GOLD_INGOT, GOLD_INGOT_COUNT));
        chest.add(new ItemStack(Blocks.OBSIDIAN, OBSIDIAN_COUNT));
        chest.add(generateUniqueItem());
        chest.add(generateUniqueItem());
        chest.add(generateUniqueItem());
        chest.add(generateUniqueItem());

        return chest;
    }
    private @NotNull ItemStack generateUniqueItem(Item item, String name, Map<Enchantment, Integer> enchantments) {
        ItemStack uniqueItem = new ItemStack(item);
        EnchantmentHelper.setEnchantments(enchantments, uniqueItem);
        CompoundTag itemTag = uniqueItem.getOrCreateTag();
        CompoundTag displayTag = itemTag.getCompound("display");
        displayTag.putString("Name", "{\"text\":\"" + name + "\"}");
        itemTag.put("display", displayTag);
        uniqueItem.setTag(itemTag);
        uniqueItem.getOrCreateTag().putBoolean("Unbreakable", true);
        return uniqueItem;
    }
    private ItemStack generateUniqueItem() {
        List<ItemStack> uniqueItems = new ArrayList<>();
        Map<Enchantment, Integer> axeEnchantments = Map.of(
                Enchantments.UNBREAKING, Enchantments.UNBREAKING.getMaxLevel(),
                Enchantments.SHARPNESS, Enchantments.SHARPNESS.getMaxLevel(),
                Enchantments.BLOCK_EFFICIENCY, Enchantments.BLOCK_EFFICIENCY.getMaxLevel(),
                Enchantments.BLOCK_FORTUNE, Enchantments.BLOCK_FORTUNE.getMaxLevel(),
                Enchantments.MENDING, Enchantments.MENDING.getMaxLevel(),
                Enchantments.VANISHING_CURSE, Enchantments.VANISHING_CURSE.getMaxLevel()
        );
        ItemStack netherAxe = generateUniqueItem(Items.NETHERITE_AXE, "Hacha del general zombie", axeEnchantments);
        uniqueItems.add(netherAxe);
        Map<Enchantment, Integer> helmetEnchantments = Map.of(
                Enchantments.UNBREAKING, Enchantments.UNBREAKING.getMaxLevel(),
                Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.ALL_DAMAGE_PROTECTION.getMaxLevel(),
                Enchantments.FIRE_PROTECTION, Enchantments.FIRE_PROTECTION.getMaxLevel(),
                Enchantments.PROJECTILE_PROTECTION, Enchantments.PROJECTILE_PROTECTION.getMaxLevel(),
                Enchantments.BLAST_PROTECTION, Enchantments.BLAST_PROTECTION.getMaxLevel(),
                Enchantments.AQUA_AFFINITY, Enchantments.AQUA_AFFINITY.getMaxLevel(),
                Enchantments.RESPIRATION, Enchantments.RESPIRATION.getMaxLevel(),
                Enchantments.MENDING, Enchantments.MENDING.getMaxLevel(),
                Enchantments.VANISHING_CURSE, Enchantments.VANISHING_CURSE.getMaxLevel()
        );
        ItemStack netherHelmet = generateUniqueItem(Items.NETHERITE_HELMET, "Casco del general zombie", helmetEnchantments);
        uniqueItems.add(netherHelmet);
        Map<Enchantment, Integer> chestplateEnchantments = Map.of(
                Enchantments.UNBREAKING, Enchantments.UNBREAKING.getMaxLevel(),
                Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.ALL_DAMAGE_PROTECTION.getMaxLevel(),
                Enchantments.FIRE_PROTECTION, Enchantments.FIRE_PROTECTION.getMaxLevel(),
                Enchantments.PROJECTILE_PROTECTION, Enchantments.PROJECTILE_PROTECTION.getMaxLevel(),
                Enchantments.BLAST_PROTECTION, Enchantments.BLAST_PROTECTION.getMaxLevel(),
                Enchantments.THORNS, Enchantments.THORNS.getMaxLevel(),
                Enchantments.MENDING, Enchantments.MENDING.getMaxLevel(),
                Enchantments.VANISHING_CURSE, Enchantments.VANISHING_CURSE.getMaxLevel()
        );
        ItemStack netherChestplate = generateUniqueItem(Items.NETHERITE_CHESTPLATE, "Pechera del general zombie", chestplateEnchantments);
        uniqueItems.add(netherChestplate);
        Map<Enchantment, Integer> leggingsEnchantments = Map.of(
                Enchantments.UNBREAKING, Enchantments.UNBREAKING.getMaxLevel(),
                Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.ALL_DAMAGE_PROTECTION.getMaxLevel(),
                Enchantments.FIRE_PROTECTION, Enchantments.FIRE_PROTECTION.getMaxLevel(),
                Enchantments.PROJECTILE_PROTECTION, Enchantments.PROJECTILE_PROTECTION.getMaxLevel(),
                Enchantments.BLAST_PROTECTION, Enchantments.BLAST_PROTECTION.getMaxLevel(),
                Enchantments.THORNS, Enchantments.THORNS.getMaxLevel(),
                Enchantments.MENDING, Enchantments.MENDING.getMaxLevel(),
                Enchantments.VANISHING_CURSE, Enchantments.VANISHING_CURSE.getMaxLevel()
        );
        ItemStack netherLeggings = generateUniqueItem(Items.NETHERITE_LEGGINGS, "Pantalón del general zombie", leggingsEnchantments);
        uniqueItems.add(netherLeggings);
        Map<Enchantment, Integer> bootsEnchantments = Map.of(
                Enchantments.UNBREAKING, Enchantments.UNBREAKING.getMaxLevel(),
                Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.ALL_DAMAGE_PROTECTION.getMaxLevel(),
                Enchantments.FIRE_PROTECTION, Enchantments.FIRE_PROTECTION.getMaxLevel(),
                Enchantments.PROJECTILE_PROTECTION, Enchantments.PROJECTILE_PROTECTION.getMaxLevel(),
                Enchantments.BLAST_PROTECTION, Enchantments.BLAST_PROTECTION.getMaxLevel(),
                Enchantments.FALL_PROTECTION, Enchantments.FALL_PROTECTION.getMaxLevel(),
                Enchantments.DEPTH_STRIDER, Enchantments.DEPTH_STRIDER.getMaxLevel(),
                Enchantments.MENDING, Enchantments.MENDING.getMaxLevel(),
                Enchantments.VANISHING_CURSE, Enchantments.VANISHING_CURSE.getMaxLevel()
        );
        ItemStack netherBoots = generateUniqueItem(Items.NETHERITE_BOOTS, "Botas del general zombie", bootsEnchantments);
        uniqueItems.add(netherBoots);

        return uniqueItems.get(new Random().nextInt(uniqueItems.size()));
    }
    private void dropItem(ItemStack itemStack) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        this.level.addFreshEntity(new ItemEntity(this.level, x, y, z, itemStack));
    }
    private BossEvent.BossBarColor transitionColors() {

        if (barColor != this.nextColor) {
            float transitionSpeed = 0.05F;

            ServerBossEvent.BossBarColor currentColor = barColor;
            ServerBossEvent.BossBarColor nextColor = this.nextColor;

            int currentColorIndex = currentColor.ordinal();
            int nextColorIndex = nextColor.ordinal();

            int transitionColorIndex = (int) (currentColorIndex + (nextColorIndex - currentColorIndex) * transitionSpeed);

            ServerBossEvent.BossBarColor transitionColor = ServerBossEvent.BossBarColor.values()[transitionColorIndex];

            if (barColor == transitionColor) {
                barColor = this.nextColor
                ;
            }else{
                barColor = transitionColor;
            }
        }
        return barColor;
    }
    public void aiStep() {
        super.aiStep();
        transitionColors();

        float healthPercentage = this.getHealth() / (float)MAX_HEALTH;

        if (!this.level.isClientSide) {

            if (this.getHealth() <= ((float)MAX_HEALTH * 0.75)) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 1));
            }


            if (this.getHealth() <= ((float)MAX_HEALTH * 0.5)) {
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 1));
            }

            if (this.getHealth() <= ((float)MAX_HEALTH * 0.25)) {
                LivingEntity target = this.getTarget();

                if (target != null && this.random.nextFloat() <= 0.33F ) {
                    target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1));
                }
                if (target != null && this.random.nextFloat() <= 0.2F) {
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 1));
                }
                if (target != null && this.random.nextFloat() <= 0.4F) {
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 2));
                }
            }
            if (this.getHealth() <= MAX_HEALTH * 0.1) {
                LivingEntity target = this.getTarget();
                if (target != null) {
                    target.setSecondsOnFire(3);
                }
            }
        }
        this.bossEvent.setColor(transitionColors());
        this.bossEvent.setProgress(healthPercentage);
    }
    public void die(@NotNull DamageSource entity) {
        if (!this.level.isClientSide) {
            if (!this.dead) {
                float explosionSize = 12.0F;
                boolean causesFire = true;
                this.level.explode(this, this.getX(), this.getY(), this.getZ(), explosionSize, causesFire, Explosion.BlockInteraction.DESTROY);
                if (this.level instanceof ServerLevel serverLevel) {
                    serverLevel.setDayTime(serverLevel.getDayTime() + 60);
                }
                this.setHealth((float) MAX_HEALTH * 0.5F);
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 7));
                this.addEffect(new MobEffectInstance(MobEffects.JUMP, Integer.MAX_VALUE, 2));
                this.invulnerableTime = 3;
                this.revived = true;
            } else {
                if (this.revived) {
                    onLivingDeath(entity);
                    this.remove(Entity.RemovalReason.KILLED);
                    dropExperience();
                    dropChest(createChestContents());
                    dropItem(dropCustomDeathLoot());
                }
            }
        }
        super.die(entity);
    }
}