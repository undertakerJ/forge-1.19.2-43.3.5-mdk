package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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

public class ExperimentalStimulatorItem extends Item {
    public ExperimentalStimulatorItem(Properties properties) {
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
                int randomNumber = getRandomNumber();
                if (randomNumber >= 19){
                player.getCooldowns().addCooldown(this, 30*60*20);
                player.sendSystemMessage(Component.literal("You got an overdose. You need to rest a 30 minutes."));
                } else {
                    player.getCooldowns().addCooldown(this, 10*60*20);
                    player.sendSystemMessage(Component.literal("You got extremely lucky, and became almost a demi-god for a 30 seconds."));
                }
                level.playSound(null,player, ModSounds.STIMULATOR_USED.get(), SoundSource.AMBIENT,1f,1f);

            }
        }
        return super.use(level, player, interactionHand);
    }

    // Метод для применения эффектов на сущность
    private void applyEffects(LivingEntity livingEntity) {

        int randomNumber = getRandomNumber();
        // 20% шанс на нормальные эффекты
        if (randomNumber >= 19) {

            livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5*60*20, 3));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5*60*20, 3));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 5*60*20, 3));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5*60*20, 3));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 5*60*20, 3));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 5*60*20, 3));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 5*60*20, 1));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 5*60*20, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5*60*20, 3));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 5*60*20, 3));

    } else {

            // Добавляет эффект силы 2 уровня на 10 секунд
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.HEAL, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 30 * 20, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 30 * 20, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 30 * 20, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 30 * 20, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.LUCK, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 30 * 20, 5));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 30 * 20, 5));
        }
    }

    private int getRandomNumber() {
        return RandomSource.createNewThreadLocalInstance().nextInt(100);
    }
}