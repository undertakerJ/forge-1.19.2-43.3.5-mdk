package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
// Продлеваем класс предмета
public class AnBalkMemeItem extends SwordItem {
    //При использовании лкм на энтити
    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        //если энтити - живой
        if(entity instanceof LivingEntity) {
            //добавляем эффект слабости 255 лвла
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60 * 20, 254));
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
    public AnBalkMemeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
