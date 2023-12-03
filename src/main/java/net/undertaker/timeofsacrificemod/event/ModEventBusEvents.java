package net.undertaker.timeofsacrificemod.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.particle.ModParticles;
import net.undertaker.timeofsacrificemod.particle.custom.SparkleParticles;
import net.undertaker.timeofsacrificemod.particle.custom.SpecialSparkleParticles;

@Mod.EventBusSubscriber(modid = TimeOfSacrifice.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.SPARK_PARTICLES.get(),
        SparkleParticles.Provider::new);

        Minecraft.getInstance().particleEngine.register(ModParticles.SPECIAL_SPARK_PARTICLES.get(),
        SpecialSparkleParticles.Provider::new);
    }

}
