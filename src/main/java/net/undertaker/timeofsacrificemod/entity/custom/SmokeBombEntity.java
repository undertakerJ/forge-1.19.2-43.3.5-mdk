package net.undertaker.timeofsacrificemod.entity.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.undertaker.timeofsacrificemod.entity.ModEntities;
import net.undertaker.timeofsacrificemod.item.ModItems;

public class SmokeBombEntity extends ThrowableItemProjectile {
    public  SmokeBombEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {

        super(pEntityType, pLevel);
    }
public  SmokeBombEntity(Level pLevel) {
        super(ModEntities.SMOKE_BOMB_PROJECTILE.get(), pLevel);
    }
public  SmokeBombEntity(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.SMOKE_BOMB_PROJECTILE.get(), livingEntity, pLevel);
    }
    @Override
    protected Item getDefaultItem() {
        return ModItems.SMOKE_BOMB.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!this.level.isClientSide) {
            SmokeZoneEntity smokeZone = new SmokeZoneEntity(ModEntities.SMOKE_ZONE_ENTITY.get(), level);
            smokeZone.setPos(getBlockX(),getBlockY(),getBlockZ());
            this.level.addFreshEntity(smokeZone);

        }
        level.playSound((Player) null, getBlockX(), getBlockY(), getBlockZ(), SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F);
        this.discard();
        super.onHitBlock(pResult);
    }
}
