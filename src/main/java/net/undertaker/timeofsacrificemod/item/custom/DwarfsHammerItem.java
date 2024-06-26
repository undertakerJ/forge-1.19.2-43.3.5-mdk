package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;


public class DwarfsHammerItem extends PickaxeItem {
    public DwarfsHammerItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
        MinecraftForge.EVENT_BUS.register(this);

    }
    public static final DamageSource THIEF_HAMMER = (new DamageSource(TimeOfSacrifice.MOD_ID + "_wrong_player_hammer").bypassArmor().bypassMagic());
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();
//        if (!player.getName().contains(Component.literal("SaltFairy")) && heldItem.getItem() instanceof DwarfsHammerItem) {
//
//            player.hurt(THIEF_HAMMER.bypassInvul(), 9999);
//        }
        if (heldItem.getItem() instanceof DwarfsHammerItem &&
                !player.isShiftKeyDown() &&
                event.getState().getBlock() != Blocks.AIR) {
            breakBlocksInArea(event);
            CompoundTag tag = heldItem.getOrCreateTag();
            tag.putBoolean("Unbreakable", true);
        }
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && player.isShiftKeyDown()) {
            ItemStack heldItem = player.getMainHandItem();
            if (!player.getName().contains(Component.literal("SaltFairy")) && heldItem.getItem() instanceof DwarfsHammerItem) {
                player.hurt(THIEF_HAMMER.bypassInvul(), 9999);
            }
            player.getCooldowns().addCooldown(this, 10 * 60 * 20);
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 5 * 60 * 20, 3));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5 * 60 * 20, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5 * 60 * 20, 3));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 5 * 60 * 20, 0));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3 * 60 * 20, 3));
            player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 3 * 60 * 20, 3));
        }
        return super.use(level, player, interactionHand);
    }
    private void breakBlocksInArea(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Vec3 eyePosition = player.getEyePosition();
        Vec3 lookPosition = eyePosition.add(player.getLookAngle().scale(8));
        ClipContext clipContext =
                new ClipContext(
                        eyePosition, lookPosition, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        BlockHitResult hitResult = player.level.clip(clipContext);
        Direction hitSide = hitResult.getDirection();
        BlockPos pos = event.getPos();
        int radiusX = 1;
        int radiusY = 1;
        int radiusZ = 1;
        for (int i = -radiusX; i <= radiusX; i++) {
            for (int j = -radiusY; j <= radiusY; j++) {
                for (int k = -radiusZ; k <= radiusZ; k++) {
                    BlockPos targetPos = pos.offset(i, j, k);
                    BlockState targetState = player.level.getBlockState(targetPos);
                    Block targetBlock = targetState.getBlock();
                    if (targetBlock == Blocks.BEDROCK ||
                            targetBlock == Blocks.OBSIDIAN ||
                            targetBlock == Blocks.REINFORCED_DEEPSLATE ||
                            targetBlock == Blocks.END_GATEWAY ||
                            targetBlock == Blocks.END_PORTAL ||
                            targetBlock == Blocks.END_PORTAL_FRAME ||
                            targetBlock == Blocks.BEACON ||
                            targetBlock == Blocks.NETHER_PORTAL ||
                            targetBlock == Blocks.ENCHANTING_TABLE) {
                        return;
                    }
                }
            }
        }
        switch (hitSide.getAxis()) {
            case X:
                breakBlocksVertically(event.getPlayer(), pos, 0, radiusY, radiusZ);
                break;
            case Y:
                breakBlocksHorizontally(event.getPlayer(), pos, radiusX, radiusZ);
                break;
            case Z:
                breakBlocksVertically(event.getPlayer(), pos, radiusX, radiusY, 0);
                break;
        }
    }
    private void breakBlocksHorizontally(Player player, BlockPos pos, int radiusX, int radiusZ) {
        for (int i = -radiusX; i <= radiusX; i++) {
            for (int j = -radiusZ; j <= radiusZ; j++) {
                BlockPos targetPos = pos.offset(i, 0, j);
                player.level.destroyBlock(targetPos, true);
            }
        }
    }
    private void breakBlocksVertically(Player player, BlockPos pos, int radiusX, int radiusY, int radiusZ) {
        for (int i = -radiusX; i <= radiusX; i++) {
            for (int j = -radiusY; j <= radiusY; j++) {
                for (int k = -radiusZ; k <= radiusZ; k++) {
                    BlockPos targetPos = pos.offset(i, j, k);
                    player.level.destroyBlock(targetPos, true);
                }
            }
        }
    }
    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        // Проверяем, подходит ли инструмент для дропа блока
        return true;
    }
}
