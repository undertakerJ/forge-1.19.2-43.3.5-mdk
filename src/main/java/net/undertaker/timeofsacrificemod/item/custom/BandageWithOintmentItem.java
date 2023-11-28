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

public class BandageWithOintmentItem extends Item {
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand){
        if(!level.isClientSide){
            if (player.isShiftKeyDown()) {
                player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0, false,false));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5*20, 1, false,true));
                player.getCooldowns().addCooldown(this, 15*20);
                player.getItemInHand(interactionHand).shrink(1);
                if (player.getItemInHand(interactionHand).getCount() == 0) {
                    player.setItemInHand(interactionHand, ItemStack.EMPTY);
                }
            }
        }
        level.playSound(null, player, ModSounds.BANDAGE_USAGE.get(), SoundSource.AMBIENT,1f,1f);
        return super.use(level, player, interactionHand);
    }
    public BandageWithOintmentItem(Item.Properties pProperties) {
        super(pProperties);
    }
}
