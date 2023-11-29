package net.undertaker.timeofsacrificemod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.entity.custom.SmokeBombEntity;
import net.undertaker.timeofsacrificemod.entity.custom.SmokeZoneEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TimeOfSacrifice.MOD_ID);

    public static final RegistryObject<EntityType<SmokeBombEntity>> SMOKE_BOMB_PROJECTILE =
            ENTITY_TYPES.register("smoke_bomb_projectile", () -> EntityType.Builder.<SmokeBombEntity>of(SmokeBombEntity::new, MobCategory.MISC)
                    .sized(0.5f,0.5f).build("smoke_bomb_projectile"));
public static final RegistryObject<EntityType<SmokeZoneEntity>> SMOKE_ZONE_ENTITY =
            ENTITY_TYPES.register("smoke_zone", () -> EntityType.Builder.<SmokeZoneEntity>of(SmokeZoneEntity::new, MobCategory.MISC)
                    .sized(5f,5f).build("smoke_zone"));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
