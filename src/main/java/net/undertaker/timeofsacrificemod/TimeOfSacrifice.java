package net.undertaker.timeofsacrificemod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.undertaker.timeofsacrificemod.block.ModBlocks;
import net.undertaker.timeofsacrificemod.effect.ModEffects;
import net.undertaker.timeofsacrificemod.entity.ModEntities;
import net.undertaker.timeofsacrificemod.entity.client.AmethystSparkEntityRenderer;
import net.undertaker.timeofsacrificemod.entity.client.SmokeZoneRenderer;
import net.undertaker.timeofsacrificemod.entity.custom.SmokeZoneEntity;
import net.undertaker.timeofsacrificemod.item.ModItems;
import net.undertaker.timeofsacrificemod.loot.ModLootModifiers;
import net.undertaker.timeofsacrificemod.networking.ModMessages;
import net.undertaker.timeofsacrificemod.particle.ModParticles;
import net.undertaker.timeofsacrificemod.sound.ModSounds;
import net.undertaker.timeofsacrificemod.world.feature.ModConfiguredFeatures;
import net.undertaker.timeofsacrificemod.world.feature.ModPlacedFeatures;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TimeOfSacrifice.MOD_ID)
public class TimeOfSacrifice
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "timeofsacrifice";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public TimeOfSacrifice()
            //bruh
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEffects.register(modEventBus);

        ModEntities.register(modEventBus);

        ModParticles.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModLootModifiers.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {


            EntityRenderers.register(ModEntities.SMOKE_BOMB_PROJECTILE.get(), ThrownItemRenderer::new);

            EntityRenderers.register(ModEntities.AMETHYST_SPARK_ENTITY.get(), AmethystSparkEntityRenderer::new);
          //HERE AN ISSUE
           EntityRenderers.register(ModEntities.SMOKE_ZONE_ENTITY.get(), SmokeZoneRenderer::new);

        }
    }
}
