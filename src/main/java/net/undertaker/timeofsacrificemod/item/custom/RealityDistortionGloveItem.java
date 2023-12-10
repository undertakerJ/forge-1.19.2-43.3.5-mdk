package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.undertaker.timeofsacrificemod.effect.ModEffects;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import static net.undertaker.timeofsacrificemod.effect.EntropyEffect.ENTROPY;


public class RealityDistortionGloveItem extends SwordItem {

    public RealityDistortionGloveItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.SWORD_SWEEP.equals(false);

    }

    private static final int numberOfModes = 5;

    private @Nullable EntityHitResult getEntityAtCursor(Player player, float maxDistance) {
        Vec3 lookVector = player.getLookAngle().scale(maxDistance);
        Vec3 startPos = player.getEyePosition();
        Vec3 endPos = startPos.add(lookVector);
        AABB searchArea = player.getBoundingBox().expandTowards(lookVector).inflate(1);
        Predicate<Entity> filter = LivingEntity.class::isInstance;
        return ProjectileUtil.getEntityHitResult(
                player.level, player, startPos, endPos, searchArea, filter);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() instanceof RealityDistortionGloveItem) {
            switch (getCurrentMode(itemStack)) {
                case 0:
                    return onRightClickMode1(level, player, interactionHand, itemStack);
                case 1:
                    return onRightClickMode2(level, player, interactionHand, itemStack);
                case 2:
                    return onRightClickMode3(level, player, interactionHand, itemStack);
                case 3:
                    return onRightClickMode4(level, player, interactionHand, itemStack);
                case 4:
                    return onRightClickMode5(level, player, interactionHand, itemStack);
            }
            return InteractionResultHolder.fail(itemStack);
        }
        return super.use(level, player, interactionHand);
    }

    //Эффект Хаоса
    private InteractionResultHolder<ItemStack> onRightClickMode1(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        if (!level.isClientSide()) {
            ItemStack helditem = player.getMainHandItem();
            if (!(player.getItemInHand(interactionHand).getItem().getDamage(helditem) >= 460)) {

                double interactionRange = 10.0;

                BlockPos centerPos = player.blockPosition();

                List<LivingEntity> entitiesInArea = level.getEntitiesOfClass(LivingEntity.class,
                        new AABB(centerPos.offset(-interactionRange, -interactionRange, -interactionRange),
                                centerPos.offset(interactionRange, interactionRange, interactionRange)));
                for (LivingEntity entity : entitiesInArea) {
                    if (!entity.getUUID().equals(player.getUUID())) {
                        float randomFloat = player.getRandom().nextFloat();
                        BlockPos entityPos = entity.blockPosition();
                        double entityX = entity.getX();
                        double entityY = entity.getY();
                        double entityZ = entity.getZ();

                        if (randomFloat < 0.09f) {
                            level.explode(entity, entityX, entityY, entityZ, 3, Explosion.BlockInteraction.NONE);
                            entity.hurt(ENTROPY, 100);

                        } else if (randomFloat < 0.19f) {
                            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
                            lightningbolt.moveTo(Vec3.atBottomCenterOf(entityPos));
                            level.addFreshEntity(lightningbolt);
                            level.playSound(player, entityPos, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 1f, 1f);

                        } else if (randomFloat < 0.29f) {
                            Entity cloneEntity = entity.getType().create(level);
                            cloneEntity.moveTo(Vec3.atCenterOf(entityPos));
                            level.addFreshEntity(cloneEntity);

                        } else if (randomFloat < 0.39f) {
                            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10 * 20, 1));
                            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10 * 20, 1));

                        } else if (randomFloat < 0.49f) {
                            entity.setHealth(100f);

                        } else if (randomFloat < 0.59f) {
                            if (!(entity instanceof Player)) {
                                Entity pigEntity = EntityType.PIG.create(level);
                                pigEntity.setCustomName(Component.translatable("entity.pigEntity"));
                                pigEntity.moveTo(Vec3.atCenterOf(entityPos));
                                level.addFreshEntity(pigEntity);
                                entity.remove(Entity.RemovalReason.DISCARDED);
                            }
                        } else if (randomFloat < 0.69f) {
                            entity.setNoGravity(true);

                        } else if (randomFloat < 0.79f) {
                            if (entity instanceof Enemy) {
                                entity.setInvulnerable(true);
                            }

                        } else if (randomFloat < 0.89f) {
                            if (entity instanceof Player) {
                                entity.randomTeleport(player.getRandomX(2), entityY, player.getRandomZ(2), false);
                            }

                        } else if (randomFloat < 0.99f) {
                            if (entity instanceof Mob) {
                                ((Mob) entity).isNoAi();
                            }
                        }

                        player.getItemInHand(interactionHand).hurt(10, level.getRandom(), null);
                    }
                }
            }
        }
        return InteractionResultHolder.success(itemStack);
    }

    //Кинетическое Искажение
    private InteractionResultHolder<ItemStack> onRightClickMode2(Level level, LivingEntity livingEntity, InteractionHand interactionHand, ItemStack itemStack) {
        Player player = (Player) livingEntity;
        ItemStack helditem = player.getMainHandItem();

        if (!(livingEntity instanceof Player)) {
            return InteractionResultHolder.pass(itemStack);
        }
        if (!(player.getItemInHand(interactionHand).getItem().getDamage(helditem) >= 460)) {
            double interactionRange = 5.0;
            BlockPos centerPos = player.blockPosition();
            List<LivingEntity> entitiesInArea = level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(centerPos.offset(-interactionRange, -interactionRange, -interactionRange),
                            centerPos.offset(interactionRange, interactionRange, interactionRange)));

            double speedMultiplier = 5;
            double randomX = level.random.nextDouble() * 2.0 - 1.0;
            double randomY = level.random.nextDouble() * 2.0 - 1.0;
            double randomZ = level.random.nextDouble() * 2.0 - 1.0;

            double deltaMovementX = randomX * speedMultiplier;
            double deltaMovementY = randomY * speedMultiplier;
            double deltaMovementZ = randomZ * speedMultiplier;
            for (LivingEntity entity : entitiesInArea) {
                if (!entity.getUUID().equals(player.getUUID()) && !player.isShiftKeyDown()) {
                    entity.setDeltaMovement(deltaMovementX, deltaMovementY, deltaMovementZ);

                }
            }
            player.getItemInHand(interactionHand).hurt(10, level.getRandom(), null);
        }
        return InteractionResultHolder.success(itemStack);
    }

    //Энергетический Конденсатор
    private InteractionResultHolder<ItemStack> onRightClickMode3(Level level, Player player, InteractionHand interactionHand, ItemStack itemStack) {

        player.getItemInHand(interactionHand).hurt(-2, level.getRandom(), null);
        return InteractionResultHolder.success(itemStack);
    }

    //Энтропийная Деградация
    private InteractionResultHolder<ItemStack> onRightClickMode4(Level level, LivingEntity livingEntity, InteractionHand interactionHand, ItemStack itemStack) {
        EntityHitResult entityAtCursor = getEntityAtCursor((Player) livingEntity, 3);
        Player player = ((Player) livingEntity);
        ItemStack helditem = player.getMainHandItem();
        if (!(player.getItemInHand(interactionHand).getItem().getDamage(helditem) >= 460)) {
            HitResult rayTraceBlocks = player.pick(3.0, 1.0F, false);
            if (entityAtCursor != null) {
                applyEntropyEffect((LivingEntity) entityAtCursor.getEntity());
            } else {
                if (rayTraceBlocks.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) rayTraceBlocks;
                    BlockPos blockPos = blockHitResult.getBlockPos();

                    BlockState targetState = level.getBlockState(blockPos);
                    Block targetBlock = targetState.getBlock();

                    if (!(targetBlock == Blocks.BEDROCK ||
                            targetBlock == Blocks.OBSIDIAN ||
                            targetBlock == Blocks.REINFORCED_DEEPSLATE ||
                            targetBlock == Blocks.END_GATEWAY ||
                            targetBlock == Blocks.END_PORTAL ||
                            targetBlock == Blocks.END_PORTAL_FRAME ||
                            targetBlock == Blocks.BEACON ||
                            targetBlock == Blocks.NETHER_PORTAL ||
                            targetBlock == Blocks.ENCHANTING_TABLE)) {
                        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.AMBIENT, 1, 1);

                        level.removeBlock(blockPos, false);
                        spawnEntropyParticles(blockPos, level);
                        player.getItemInHand(interactionHand).hurt(10, level.getRandom(), null);
                    }
                }
            }
        }
        return InteractionResultHolder.success(itemStack);
    }

    //Дар Целителя
    private InteractionResultHolder<ItemStack> onRightClickMode5(Level level, LivingEntity livingEntity, InteractionHand interactionHand, ItemStack itemStack) {
        EntityHitResult entityAtCursor = getEntityAtCursor((Player) livingEntity, 3);
        if (!level.isClientSide) {
            Player player = ((Player) livingEntity);
            ItemStack helditem = player.getMainHandItem();
            if (!(player.getItemInHand(interactionHand).getItem().getDamage(helditem) >= 460)) {
            if (entityAtCursor != null && livingEntity.isShiftKeyDown()) {
                livingEntity.getItemInHand(interactionHand).hurtAndBreak(10, livingEntity, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                applyRegenerationEffect((LivingEntity) entityAtCursor.getEntity());
                if (entityAtCursor.getEntity() instanceof Enemy) {
                    applyHealEffect((LivingEntity) entityAtCursor.getEntity());
                }
                if (entityAtCursor.getEntity() instanceof Player) {
                    applyRegenerationEffect((LivingEntity) entityAtCursor.getEntity());
                }
            } else {
                applyRegenerationEffect(livingEntity);
            }
                player.getItemInHand(interactionHand).hurt(10, level.getRandom(), null);
            }
        }

        return InteractionResultHolder.success(itemStack);
    }

    public static void spawnEntropyParticles(BlockPos blockPos, Level level) {
        if (level.isClientSide) {
            for (int i = 0; i < 15; ++i) {
                double offsetX = blockPos.getX() + 0.5D + (ThreadLocalRandom.current().nextDouble() - 0.5D) * 2;
                double offsetY = blockPos.getY() + ThreadLocalRandom.current().nextDouble();
                double offsetZ = blockPos.getZ() + 0.5D + (ThreadLocalRandom.current().nextDouble() - 0.5D) * 2;

                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, offsetX, offsetY, offsetZ, 0.0D, 0.005D, 0.0D);
            }
        }
    }

    private void applyRegenerationEffect(LivingEntity livingEntity) {
        // Добавляем эффект
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10 * 20, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 30 * 20, 1));
    }

    private void applyHealEffect(LivingEntity livingEntity) {
        // Добавляем эффект
        livingEntity.addEffect(new MobEffectInstance(MobEffects.HEAL, 10, 3));

    }

    private void applyEntropyEffect(LivingEntity livingEntity) {
        // Добавляем эффект
        livingEntity.addEffect(new MobEffectInstance(ModEffects.ENTROPY.get(), 10 * 20, 0));

    }

    private int getCurrentMode(ItemStack itemStack) {
        // Получает текущий режим из тега предмета
        return itemStack.getOrCreateTag().getInt("Mode");
    }
}
