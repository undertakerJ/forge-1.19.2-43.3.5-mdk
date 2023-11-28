package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.undertaker.timeofsacrificemod.item.ModItems;
import net.undertaker.timeofsacrificemod.sound.ModSounds;
//Продеваем класс предмета
public class HealingOintmentItem extends Item {
    //Делаем при использовании
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        //Если это на сервере и у игрока нажат шифт
        if (!level.isClientSide && player.isShiftKeyDown()) {
            //Накладываем эффекты
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5 * 20, 1, false, true));
            //Накладываем перезарядку
                player.getCooldowns().addCooldown(this, 15 * 20);
                //Даём предмет игроку
                player.addItem(new ItemStack(Items.BOWL));
                //Уменьшаем стак в руке на 1
                player.getItemInHand(interactionHand).shrink(1);
            //Если стак равен 0
                if (player.getItemInHand(interactionHand).getCount() == 0) {
                    //Удаляем из руки стак
                    player.setItemInHand(interactionHand, ItemStack.EMPTY);
                }

        }
        return super.use(level, player, interactionHand);
    }

    public HealingOintmentItem(Properties pProperties) {
        super(pProperties);
    }
}
