package com.macawmod;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MacawEntity extends TameableEntity {
    private static final TrackedData<Boolean> ON_SHOULDER =
        DataTracker.registerData(MacawEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public MacawEntity(EntityType<? extends MacawEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMacawAttributes() {
        return TameableEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.0, 5.0f, 1.0f, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ON_SHOULDER, false);
    }

    public boolean isOnShoulder() {
        return this.dataTracker.get(ON_SHOULDER);
    }

    public void setOnShoulder(boolean onShoulder) {
        this.dataTracker.set(ON_SHOULDER, onShoulder);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("OnShoulder", this.isOnShoulder());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setOnShoulder(nbt.getBoolean("OnShoulder"));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!this.isTamed()) {
            if (isSeed(stack)) {
                if (!this.getWorld().isClient) {
                    if (!player.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                    if (this.random.nextInt(3) == 0) {
                        this.setOwner(player);
                        this.setTamed(true);
                        this.navigation.stop();
                        this.getWorld().sendEntityStatus(this, (byte) 7);
                    } else {
                        this.getWorld().sendEntityStatus(this, (byte) 6);
                    }
                }
                return ActionResult.success(this.getWorld().isClient);
            }
        } else {
            if (this.isOwner(player)) {
                if (!this.getWorld().isClient) {
                    boolean mounted = MacawShoulderTracker.tryMountOnShoulder(this, player);
                    if (mounted) {
                        this.setOnShoulder(true);
                        this.discard();
                    }
                }
                return ActionResult.success(this.getWorld().isClient);
            }
        }

        return super.interactMob(player, hand);
    }

    private static boolean isSeed(ItemStack stack) {
        return stack.isOf(Items.WHEAT_SEEDS)
            || stack.isOf(Items.MELON_SEEDS)
            || stack.isOf(Items.PUMPKIN_SEEDS)
            || stack.isOf(Items.BEETROOT_SEEDS);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return isSeed(stack);
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        MacawEntity child = MacawModEntities.MACAW.create(world);
        if (child != null && this.isTamed()) {
            child.setOwnerUuid(this.getOwnerUuid());
            child.setTamed(true);
        }
        return child;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PARROT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(net.minecraft.entity.damage.DamageSource source) {
        return SoundEvents.ENTITY_PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PARROT_DEATH;
    }

    @Override
    public boolean canBreatheInWater() {
        return false;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isTamed();
    }
}