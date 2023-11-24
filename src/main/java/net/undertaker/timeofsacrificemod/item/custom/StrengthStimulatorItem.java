package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.undertaker.timeofsacrificemod.sound.ModSounds;

import javax.annotation.Nullable;

public class StrengthStimulatorItem extends Item {
    public StrengthStimulatorItem(Properties properties) {
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

                float randomNumber = player.getRandom().nextFloat();
                // Если есть результат !=null и эффекты применяются
                if (playerAtCursor != null) {
                    if (randomNumber <= 0.9) {
                        applyEffects((LivingEntity) playerAtCursor.getEntity());
                        player.getCooldowns().addCooldown(this, 120 * 20);
                    } else {
                        applySideEffects((LivingEntity) playerAtCursor.getEntity());
                        player.sendSystemMessage(Component.literal("He got an overdose. He need to take a rest for 10 minutes."));
                        player.getCooldowns().addCooldown(this, 600 * 20);
                    }
                } else {
                    if (randomNumber <= 0.9) {
                        applyEffects(player);
                        player.getCooldowns().addCooldown(this, 120 * 20);
                    } else {
                        applySideEffects(player);
                        player.sendSystemMessage(Component.literal("You got an overdose. You need to take a rest for 10 minutes."));
                        player.getCooldowns().addCooldown(this, 600 * 20);
                    }
                }
                level.playSound(null, player, ModSounds.STIMULATOR_USED.get(), SoundSource.AMBIENT, 1f, 1f);
            }
        }
        return super.use(level, player, interactionHand);
    }

    // Метод для применения эффектов на сущность
    private void applyEffects(LivingEntity livingEntity) {
        // Добавляем эффект регенерации на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10 * 20, 1));

    }

    private void applySideEffects(LivingEntity livingEntity) {
        // Добавляем эффект регенерации на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30 * 20, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30 * 20, 3));

    }
}