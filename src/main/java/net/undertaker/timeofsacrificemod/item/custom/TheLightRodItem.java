package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.undertaker.timeofsacrificemod.block.ModBlocks;
import net.undertaker.timeofsacrificemod.entity.custom.AmethystSparkEntity;

public class TheLightRodItem extends Item {

    public TheLightRodItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide()) {
            AmethystSparkEntity amethystSparkEntity = new AmethystSparkEntity(level, player, ModBlocks.AMETHYST_SPARKLE_BLOCK.get().defaultBlockState());
            amethystSparkEntity.setItem(new ItemStack(this));
            amethystSparkEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3F, 0.0F);
            level.addFreshEntity(amethystSparkEntity);
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }
        return super.use(level, player, interactionHand);
    }
}
