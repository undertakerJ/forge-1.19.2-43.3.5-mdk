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

public class HealingOintmentItem extends Item {
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand){
            if(!level.isClientSide){
                if (player.isShiftKeyDown()) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5*20, 1, false,true));
                        player.getCooldowns().addCooldown(this, 15*20);
                        player.addItem(new ItemStack(Items.BOWL));
                        player.setItemInHand(interactionHand, ItemStack.EMPTY);
                }
            }
        return super.use(level, player, interactionHand);
    }
    public HealingOintmentItem(Properties pProperties) {
        super(pProperties);
    }
}
