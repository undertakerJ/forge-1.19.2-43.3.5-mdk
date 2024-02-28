package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.ChatFormatting;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.undertaker.timeofsacrificemod.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

//Продлеваем класс предмета
public class StrengthStimulatorItem extends Item {
    public StrengthStimulatorItem(Properties properties) {
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
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.translatable("tooltip.strength_stimulator_item").withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && player.isShiftKeyDown()) {
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
                        player.sendSystemMessage(Component.translatable("message.stimulator_he_overdose"));
                        player.getCooldowns().addCooldown(this, 600 * 20);
                    }
                } else {
                    if (randomNumber <= 0.9) {
                        applyEffects(player);
                        player.getCooldowns().addCooldown(this, 120 * 20);
                    } else {
                        applySideEffects(player);
                        player.sendSystemMessage(Component.translatable("message.stimulator_overdose"));
                        player.getCooldowns().addCooldown(this, 600 * 20);
                    }
                }
                level.playSound(null, player, ModSounds.STIMULATOR_USED.get(), SoundSource.AMBIENT, 1f, 1f);

        }
        return super.use(level, player, interactionHand);
    }

    // Метод для применения эффектов на сущность
    private void applyEffects(LivingEntity livingEntity) {
        // Добавляем эффект силы на 30 секунд с уровнем 2
        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 30 * 20, 1));

    }

    private void applySideEffects(LivingEntity livingEntity) {
        // Добавляем эффект
        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 15 * 20, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5 * 20, 3));

    }
}