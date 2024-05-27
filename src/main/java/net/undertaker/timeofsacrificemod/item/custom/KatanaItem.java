package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.undertaker.timeofsacrificemod.mana.PlayerMana;
import net.undertaker.timeofsacrificemod.mana.PlayerManaProvider;
import net.undertaker.timeofsacrificemod.sound.ModSounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KatanaItem extends SwordItem {
    public KatanaItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
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
        player.isBlocking();
        ItemStack itemstack = player.getItemInHand(interactionHand);
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.consume(itemstack);
    }

    private InteractionResultHolder<ItemStack> onRightClickMode2(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        List<LivingEntity> targetEntities;
        int attackedEntities = 0;
        if (!level.isClientSide) {
            BlockPos playerPos = player.blockPosition();
            AABB playerBoundingBox = new AABB(playerPos, playerPos.offset(1, 1, 1));
            MinecraftServer server = level.getServer();
            targetEntities = level.getEntitiesOfClass(LivingEntity.class, playerBoundingBox.inflate(5));
            if (!targetEntities.isEmpty() && attackedEntities <= 5) {
                LivingEntity targetEntity = targetEntities.remove(0);
                if (targetEntity != player && server != null) {
                    player.teleportTo(targetEntity.position().x, targetEntity.position().y, targetEntity.position().z);
                    player.attack(targetEntity);
                    attackedEntities++;

                }
            }
        }
        if (attackedEntities >= 5) {
            return InteractionResultHolder.success(itemStack);
        }

        return InteractionResultHolder.fail(itemStack);
    }
    private InteractionResultHolder<ItemStack> onRightClickMode3(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {

        return InteractionResultHolder.success(itemStack);
    }

    private InteractionResultHolder<ItemStack> onRightClickMode4(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {

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
