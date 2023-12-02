package net.undertaker.timeofsacrificemod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.undertaker.timeofsacrificemod.entity.ModEntities;
import net.undertaker.timeofsacrificemod.item.ModItems;

import java.util.List;

import static net.minecraft.world.entity.EquipmentSlot.*;


public class SmokeZoneEntity extends Entity {
    private int duration = 600;
    private int waitTime = 20;

    public SmokeZoneEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }

    public SmokeZoneEntity(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntities.SMOKE_ZONE_ENTITY.get(), pLevel);
        this.setPos(pX, pY, pZ);
    }

    public SmokeZoneEntity(Level pLevel) {
        super(ModEntities.SMOKE_ZONE_ENTITY.get(), pLevel);
    }

    public void makeParticlesAroundEntity(Entity entity, int radius) {
        BlockPos entityPos = entity.blockPosition();
        if (level.nextSubTickCount() % 4 == 0) {
            for (int i = 0; i < 15; ++i) {
                double offsetX = entityPos.getX() + 0.5D + (level.random.nextDouble() - 0.5D) * radius * 2;
                double offsetY = entityPos.getY() + level.random.nextDouble() * radius;
                double offsetZ = entityPos.getZ() + 0.5D + (level.random.nextDouble() - 0.5D) * radius * 2;

                level.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, offsetX, offsetY, offsetZ, 0.0D, 0.005D, 0.0D);
            }
        }
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            makeParticlesAroundEntity(this, 2);
        } else {
            if (this.tickCount >= this.waitTime + this.duration) {
                this.discard();
            }
        }
        List<Player> players = this.level.getEntitiesOfClass(Player.class, this.getBoundingBox());
        for (Player player : players) {// Применение эффекта к игроку на секунду
            boolean shadowFullSet = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SHADOW_ASSASSIN_HELMET.get() &&
                    player.getItemBySlot(CHEST).getItem() == ModItems.SHADOW_ASSASSIN_CHESTPLATE.get() &&
                    player.getItemBySlot(LEGS).getItem() == ModItems.SHADOW_ASSASSIN_LEGGINGS.get() &&
                    player.getItemBySlot(FEET).getItem() == ModItems.SHADOW_ASSASSIN_BOOTS.get();
            if (!level.isClientSide && shadowFullSet) {
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 30, 0));
            } else {

                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 30, 0));
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 0));
            }
        }
        List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16));
        for (LivingEntity entity : entities) {
            if (entity instanceof Enemy) {
                // Применение эффекта к враждебным мобам на секунду
                applyEffectToEntity(entity, MobEffects.GLOWING, 20, 0);
            }
        }

    }

    private void applyEffectToEntity(LivingEntity entity, MobEffect effect, int duration, int amplifier) {
        entity.addEffect(new MobEffectInstance(effect, duration, amplifier, true, true));
    }

    protected void defineSynchedData() {
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }

    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
