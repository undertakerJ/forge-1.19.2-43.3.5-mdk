package net.undertaker.timeofsacrificemod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.level.PistonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.undertaker.timeofsacrificemod.block.ModBlocks;
import net.undertaker.timeofsacrificemod.particle.ModParticles;

import java.util.Random;


public class AmethystSparkleBlock extends Block {
    public AmethystSparkleBlock(Properties properties) {
        super(properties);

    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if(pLevel.isClientSide) {
            double d0 = (double) pPos.getX() + 0.5D;
            double d1 = (double) pPos.getY() + 0.5D;
            double d2 = (double) pPos.getZ() + 0.5D;
            double y = (double) pPos.getY() + 0.5D;
            pLevel.addParticle(ModParticles.SPARK_PARTICLES.get(), d0, d1, d2, 0.0D, 0.005D, 0.0D);
            pLevel.addParticle(ModParticles.SPECIAL_SPARK_PARTICLES.get(), true, d0, y, d2, 0.0D, 0.00D, 0.0D);
        }
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pPos, CollisionContext context) {
        return Shapes.box(0.3, 0.3, 0.3, 0.7, 0.7, 0.7);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pPos) {
        return 1.0F; // Установка яркости блока на максимальное значение
    }
}

