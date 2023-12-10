package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.undertaker.timeofsacrificemod.effect.ModEffects;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//Продлеваем класс предмета
public class DragonSlayerSwordItem extends SwordItem {
    public DragonSlayerSwordItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    //Делаем описание предмета
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.dragon_slayer_item1"));
            components.add(Component.translatable("tooltip.dragon_slayer_item2"));
            components.add(Component.translatable("tooltip.dragon_slayer_item3"));
            components.add(Component.literal(" "));
            components.add(Component.translatable("tooltip.dragon_slayer_item4").withStyle(ChatFormatting.DARK_GRAY));
            components.add(Component.literal(" "));
            components.add(Component.literal("HIS WEAPON WAS TOO LARGE TO RIGHTFULLY BE CALLED A SWORD.").withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("IT WAS LARGER, THICKER, HEAVIER AND CRUELER THAN ANY NORMAL BLADE.").withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("BY ALL ACCOUNTS - IT WAS NO MORE THAN A HULKING MASS OF IRON. ").withStyle(ChatFormatting.GRAY));
        } else {
            components.add(Component.translatable("tooltip.dragon_slayer_item.shift").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    //Делаем the meme при нажатии на моба
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!player.getLevel().isClientSide()) {
            //Если у игрока нет эффекта силы
            if (!player.hasEffect(MobEffects.DAMAGE_BOOST)) {
                //Говорим ему фразу
                player.sendSystemMessage(Component.translatable("message.dragonslayer_item"));
                //Накладываем эффекты на полсекунды
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 10, 254));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 254));
            } else {
                //Если есть - убираем эффекты
                player.removeEffect(MobEffects.DIG_SLOWDOWN);
                player.removeEffect(MobEffects.WEAKNESS);
                //Если энтити живой
                if (entity instanceof LivingEntity livingEntity) {
                    //Получаем рандомный флоат из игрока
                    float randomNumber = player.getRandom().nextFloat();
                    //Если он ниже 0.5f
                    if (randomNumber < 0.5f) {
                        //Применяем эффекты на энтити
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30 * 20, 1));
                        livingEntity.addEffect(new MobEffectInstance(ModEffects.ARMOR_SHRED.get(), 30 * 20, 3));
                    }
                    //Если это игрок
                    if (entity.getType() == EntityType.PLAYER) {
                        // Дополнительно накладываем тошноту
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 30 * 20, 3));
                    }
                    //Если тип моба - зомби, визер-скелет или просто скелет
                    if (entity instanceof Zombie || entity instanceof WitherSkeleton || entity instanceof Skeleton) {
                        //Дополнительно накладываем эффект
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.HEAL, 20, 5));
                    }
                }
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}


