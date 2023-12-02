package net.undertaker.timeofsacrificemod.particle;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TimeOfSacrifice.MOD_ID);


    public static final RegistryObject<SimpleParticleType> SPARK_PARTICLES =
           PARTICLE_TYPES.register("spark_particles", () -> new SimpleParticleType(true));

public static final RegistryObject<SimpleParticleType> SPECIAL_SPARK_PARTICLES =
           PARTICLE_TYPES.register("special_spark_particles", () -> new SimpleParticleType(true));


    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
