package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.undertaker.timeofsacrificemod.sound.ModSounds;

//Продлеваем класс предмета
public class BandageWithOintmentItem extends Item {
    //При использовании предмета
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        //Если это на сервере и игрок нажал шифт
        if (!level.isClientSide && player.isShiftKeyDown()) {
            //Добавляем эффекты
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5 * 20, 1, false, true));
            //Делаем перезарядку после использования
            player.getCooldowns().addCooldown(this, 15 * 20);
            //Уменьшаем предмет на 1 за использование
            player.getItemInHand(interactionHand).shrink(1);
            //Проверка является ли количество предмета в руке 0
            if (player.getItemInHand(interactionHand).getCount() == 0) {
                //Если 0 = удаляем предмет
                player.setItemInHand(interactionHand, ItemStack.EMPTY);
            }

        }
        //Проигрываем звук бинта
        level.playSound(null, player, ModSounds.BANDAGE_USAGE.get(), SoundSource.AMBIENT, 1f, 1f);
        return super.use(level, player, interactionHand);
    }

    public BandageWithOintmentItem(Item.Properties pProperties) {
        super(pProperties);
    }
}
