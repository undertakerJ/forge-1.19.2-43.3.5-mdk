package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;

public class UltimateStimulatorItem extends Item {

    public UltimateStimulatorItem(Properties properties) {
        super(properties);
    }

    private @Nullable EntityHitResult getPlayerAtCursor(Player player) {
        return ProjectileUtil.getEntityHitResult(
                player.level,
                player,
                player.getEyePosition(),
                player.getEyePosition().add(player.getLookAngle()),
                player.getBoundingBox().expandTowards(player.getLookAngle()).inflate(2),
                Player.class::isInstance);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide()) {
            // Если нажат Shift
            if (player.isShiftKeyDown()) {
                // Проверка есть ли игрок перед курсором
                EntityHitResult playerAtCursor = getPlayerAtCursor(player);
                // Если есть результат !=null и эффекты применяются
                if (playerAtCursor != null) {
                    applyEffects((LivingEntity) playerAtCursor.getEntity());
                }
               else{
                   applyEffects(player);
                }
                player.getCooldowns().addCooldown(this, 6000);
            }
        }
        return super.use(level, player, interactionHand);
    }

    // Метод для применения эффектов на сущность
    private void applyEffects(LivingEntity livingEntity) {
        // Добавляем эффект регенерации на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10 * 20, 3));
        // Добавляем эффект силы на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10 * 20, 3));
        // Добавляем эффект скорости на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10 * 20, 3));
        // Добавляем эффект сопротивления на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10 * 20, 3));
        // Добавляем эффект тошноты на 30 секунд
        livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 30 * 20, 0));

    }


}