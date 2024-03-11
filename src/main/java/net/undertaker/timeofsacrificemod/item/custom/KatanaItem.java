package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.undertaker.timeofsacrificemod.sound.ModSounds;

import java.util.Random;

public class KatanaItem extends SwordItem {
    private final SoundEvent[] attackSounds = {
            new SoundEvent(ModSounds.KATANA_ATTACK_1.getId()),
            new SoundEvent(ModSounds.KATANA_ATTACK_2.getId()),
            new SoundEvent(ModSounds.KATANA_ATTACK_3.getId())
    };
    private final SoundEvent blockSound;
    public KatanaItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.blockSound = new SoundEvent(ModSounds.KATANA_SWEEP.getId());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        ItemStack itemStack = player.getMainHandItem();
            switch (getCurrentMode(itemStack)) {
                case 0:

                    return onRightClickMode1(level, player, interactionHand);
                case 1:
                    return onRightClickMode2(level, player, interactionHand, itemStack);
                case 2:
                    return onRightClickMode3(level, player, interactionHand, itemStack);
                case 3:
                    return onRightClickMode4(level, player, interactionHand, itemStack);
            }
        return super.use(level, player, interactionHand);
    }
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPEAR;
    }
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
    private InteractionResultHolder<ItemStack> onRightClickMode1(Level level, Player player, InteractionHand interactionHand) {
        Random random = new Random();
        SoundEvent attackSound = attackSounds[random.nextInt(attackSounds.length)];
        player.playSound(attackSound, 1.0f, 1.0f);
        player.isBlocking();
        ItemStack itemstack = player.getItemInHand(interactionHand);
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.consume(itemstack);
    }


    private InteractionResultHolder<ItemStack> onRightClickMode2(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        if (!level.isClientSide) {
            player.sendSystemMessage(Component.literal("Test2"));
        }
        return InteractionResultHolder.success(itemStack);
    }

    private InteractionResultHolder<ItemStack> onRightClickMode3(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {

        if (!level.isClientSide) {
            player.sendSystemMessage(Component.literal("Test3"));
        }
        return InteractionResultHolder.success(itemStack);
    }  private InteractionResultHolder<ItemStack> onRightClickMode4(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {

        if (!level.isClientSide) {
            player.sendSystemMessage(Component.literal("Test4"));
        }
        return InteractionResultHolder.success(itemStack);
    }
    private int getCurrentMode(ItemStack itemStack) {
        // Получает текущий режим из тега предмета
        return itemStack.getOrCreateTag().getInt("Mode");
    }
    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, worldIn, entityLiving, timeLeft);
    }


}
