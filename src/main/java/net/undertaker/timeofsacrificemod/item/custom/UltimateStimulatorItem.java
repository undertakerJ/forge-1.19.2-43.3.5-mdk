package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.undertaker.timeofsacrificemod.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

//Продлеваем класс предмета
public class UltimateStimulatorItem extends Item {

    public UltimateStimulatorItem(Properties properties) {
        super(properties);
    }
    //Получаем информацию о игроке перед лицом
    private @Nullable EntityHitResult getPlayerAtCursor(Player player) {
        return ProjectileUtil.getEntityHitResult(
                player.level,
                player,
                player.getEyePosition(),
                player.getEyePosition().add(player.getLookAngle()),
                player.getBoundingBox().expandTowards(player.getLookAngle()).inflate(2),
                Player.class::isInstance);
    }
    //При использовании
    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.translatable("tooltip.ultimate_stimulator_item").withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
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
                } else {
                    applyEffects(player);
                }
                player.getCooldowns().addCooldown(this, 5 * 60 * 20);
                level.playSound(null, player, ModSounds.STIMULATOR_USED.get(), SoundSource.AMBIENT, 1f, 1f);
            }
        }
        return super.use(level, player, interactionHand);
    }

    // Метод для применения эффектов на сущность
    private void applyEffects(LivingEntity livingEntity) {
        // Добавляем эффект регенерации на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 30 * 20, 3));
        // Добавляем эффект силы на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 30 * 20, 3));
        // Добавляем эффект скорости на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30 * 20, 3));
        // Добавляем эффект сопротивления на 10 секунд с уровнем 4
        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30 * 20, 3));

    }

}
