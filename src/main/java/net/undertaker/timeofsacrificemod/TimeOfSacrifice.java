package net.undertaker.timeofsacrificemod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
import net.undertaker.timeofsacrificemod.event.ModEvents;
import net.undertaker.timeofsacrificemod.item.ModItems;
import net.undertaker.timeofsacrificemod.loot.ModLootModifiers;
import net.undertaker.timeofsacrificemod.networking.ModMessages;
import net.undertaker.timeofsacrificemod.particle.ModParticles;
import net.undertaker.timeofsacrificemod.sound.ModSounds;
import net.undertaker.timeofsacrificemod.world.feature.ModConfiguredFeatures;
import net.undertaker.timeofsacrificemod.world.feature.ModPlacedFeatures;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

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

        MinecraftForge.EVENT_BUS.register(new ModEvents.RenderGuiHandler());

    }
    public class Sphere {

        private final double x;
        private final double y;
        private final double z;
        private final double radius;

        public Sphere(BlockPos center, double radius) {
        this.x = center.getX() + 0.5;
        this.y = center.getY() + 0.5;
        this.z = center.getZ() + 0.5;
        this.radius = radius;
    }

        public boolean contains(BlockPos pos) {
        double distanceSq = (pos.getX() - x) * (pos.getX() - x) +
                (pos.getY() - y) * (pos.getY() - y) +
                (pos.getZ() - z) * (pos.getZ() - z);
        return distanceSq <= radius * radius;
    }

        public boolean intersects(AABB aabb) {
        // Check if any corner of the AABB is inside the sphere
        for (BlockPos corner : getCorners(aabb)) {
            if (contains(corner)) {
                return true;
            }
        }

        // Check if the sphere center is inside the AABB
        if (aabb.contains(new Vec3(x, y, z))) {
            return true;
        }

        // Check for intersection between sphere surface and AABB edges
        for (AABB edgeAABB : getEdges(aabb)) {
            if (intersectsEdge(edgeAABB)) {
                return true;
            }
        }

        return false;
    }

        private List<BlockPos> getCorners(AABB aabb) {
        List<BlockPos> corners = new ArrayList<>();
        for (int x = 0; x <= 1; x++) {
            for (int y = 0; y <= 1; y++) {
                for (int z = 0; z <= 1; z++) {
                    double minX = aabb.minX + x * (aabb.maxX - aabb.minX);
                    double minY = aabb.minY + y * (aabb.maxY - aabb.minY);
                    double minZ = aabb.minZ + z * (aabb.maxZ - aabb.minZ);
                    corners.add(new BlockPos(minX, minY, minZ));
                }
            }
        }
        return corners;
    }

        private List<AABB> getEdges(AABB aabb) {
        List<AABB> edges = new ArrayList<>();
        List<BlockPos> corners = getCorners(aabb);

        for (int i = 0; i < corners.size(); i++) {
            for (int j = i + 1; j < corners.size(); j++) {
                BlockPos pos1 = corners.get(i);
                BlockPos pos2 = corners.get(j);

                double minX = Math.min(pos1.getX(), pos2.getX());
                double minY = Math.min(pos1.getY(), pos2.getY());
                double minZ = Math.min(pos1.getZ(), pos2.getZ());

                double maxX = Math.max(pos1.getX(), pos2.getX());
                double maxY = Math.max(pos1.getY(), pos2.getY());
                double maxZ = Math.max(pos1.getZ(), pos2.getZ());

                edges.add(new AABB(minX, minY, minZ, maxX, maxY, maxZ));
            }
        }

        return edges;
    }

        private boolean intersectsEdge(AABB edgeAABB) {
        // Check if the closest point on the edge to the sphere center is within the sphere
        double closestX = Math.max(Math.min(edgeAABB.minX, x), edgeAABB.maxX);
        double closestY = Math.max(Math.min(edgeAABB.minY, y), edgeAABB.maxY);
        double closestZ = Math.max(Math.min(edgeAABB.minZ, z), edgeAABB.maxZ);

        double distanceSq = (closestX - x) * (closestX - x) +
                (closestY - y) * (closestY - y) +
                (closestZ - z) * (closestZ - z);
        return distanceSq <= radius * radius;
    }
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
