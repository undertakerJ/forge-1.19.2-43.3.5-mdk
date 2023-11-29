package net.undertaker.timeofsacrificemod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.undertaker.timeofsacrificemod.entity.ModEntities;

public class SmokeZoneEntity extends Entity {
    public SmokeZoneEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }
    public SmokeZoneEntity(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntities.SMOKE_ZONE_ENTITY.get(), pLevel);
        this.setPos(pX, pY, pZ);
    }
    public  SmokeZoneEntity(Level pLevel) {
        super(ModEntities.SMOKE_ZONE_ENTITY.get(), pLevel);
    }



    protected void defineSynchedData() {}
    protected void readAdditionalSaveData(CompoundTag pCompound) {}
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
