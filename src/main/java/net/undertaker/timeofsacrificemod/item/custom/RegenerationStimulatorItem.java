package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RegenerationStimulatorItem extends Item{
    public RegenerationStimulatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && interactionHand == InteractionHand.MAIN_HAND) {
            // Добавляем эффект регенерации 2 на 10 секунд
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10 * 20, 1));
            // Добавляем перезарядку в 120 секунд
            player.getCooldowns().addCooldown(this, 2400);
        }
        return super.use(level, player, interactionHand);
    }
}
