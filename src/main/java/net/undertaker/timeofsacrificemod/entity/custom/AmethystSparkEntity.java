package net.undertaker.timeofsacrificemod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fluids.FluidType;
import net.undertaker.timeofsacrificemod.block.ModBlocks;
import net.undertaker.timeofsacrificemod.entity.ModEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;


public class AmethystSparkEntity extends ThrowableItemProjectile {
    private final BlockState blockState;

    public AmethystSparkEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level, LivingEntity livingEntity, BlockState blockState) {
        super(entityType, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), level);
        this.blockState = blockState;
    }

    public AmethystSparkEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level, BlockState blockState) {
        super(entityType, level);
        this.blockState = blockState;
    }

    public AmethystSparkEntity(Level level, Player player, BlockState blockState) {
        super(ModEntities.AMETHYST_SPARK_ENTITY.get(), player, level);
        this.blockState = blockState;
    }

    // ... остальные методы

    @Override
    protected void onHit(HitResult hitResult) {
        if (!this.level.isClientSide) {
            BlockPos blockPos;

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                blockPos = ((BlockHitResult) hitResult).getBlockPos();
            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                blockPos = ((EntityHitResult) hitResult).getEntity().blockPosition();
            } else {
                blockPos = this.blockPosition();
            }

            if (blockPos != null) {
                BlockPos spawnPos = blockPos.relative(((BlockHitResult) hitResult).getDirection());
                this.level.setBlockAndUpdate(spawnPos, ModBlocks.AMETHYST_SPARKLE_BLOCK.get().defaultBlockState());
            }

            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    public boolean alwaysAccepts() {
        return super.alwaysAccepts();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public CompoundTag serializeNBT() {
        return super.serializeNBT();
    }

    @Override
    public boolean shouldRiderSit() {
        return super.shouldRiderSit();
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return super.getPickedResult(target);
    }

    @Override
    public boolean canRiderInteract() {
        return super.canRiderInteract();
    }

    @Override
    public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) {
        return super.canBeRiddenUnderFluidType(type, rider);
    }

    @Override
    public MobCategory getClassification(boolean forSpawnCount) {
        return super.getClassification(forSpawnCount);
    }

    @Override
    public boolean isMultipartEntity() {
        return super.isMultipartEntity();
    }

    @Override
    public @Nullable PartEntity<?>[] getParts() {
        return super.getParts();
    }

    @Override
    public float getStepHeight() {
        return super.getStepHeight();
    }

    @Override
    public boolean isInFluidType(FluidState state) {
        return super.isInFluidType(state);
    }

    @Override
    public boolean isInFluidType(FluidType type) {
        return super.isInFluidType(type);
    }

    @Override
    public boolean isInFluidType(BiPredicate<FluidType, Double> predicate) {
        return super.isInFluidType(predicate);
    }

    @Override
    public boolean isEyeInFluidType(FluidType type) {
        return super.isEyeInFluidType(type);
    }

    @Override
    public boolean canStartSwimming() {
        return super.canStartSwimming();
    }

    @Override
    public double getFluidMotionScale(FluidType type) {
        return super.getFluidMotionScale(type);
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return super.isPushedByFluid(type);
    }

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        return super.canSwimInFluidType(type);
    }

    @Override
    public boolean canFluidExtinguish(FluidType type) {
        return super.canFluidExtinguish(type);
    }

    @Override
    public float getFluidFallDistanceModifier(FluidType type) {
        return super.getFluidFallDistanceModifier(type);
    }

    @Override
    public boolean canHydrateInFluidType(FluidType type) {
        return super.canHydrateInFluidType(type);
    }

    @Override
    public @Nullable SoundEvent getSoundFromFluidType(FluidType type, SoundAction action) {
        return super.getSoundFromFluidType(type, action);
    }

    @Override
    public boolean hasCustomOutlineRendering(Player player) {
        return super.hasCustomOutlineRendering(player);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }
}
