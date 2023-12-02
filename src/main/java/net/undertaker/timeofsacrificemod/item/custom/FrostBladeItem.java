package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.undertaker.timeofsacrificemod.effect.ModEffects;

public class FrostBladeItem extends SwordItem {


    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        // Если энтити живой(моб, игрок)
        if (entity instanceof LivingEntity livingEntity) {
            // Получаем случайный флот из игрока
            float randomNumber = player.getRandom().nextFloat();
            //Если флот <0.33f, накладываем эффект замедления
            if (randomNumber < 0.33f) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5 * 20, 2));
            }
            //Если флот <0.2f, накладываем эффект фриза
            if (randomNumber < 0.2f) {
                livingEntity.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), 2 * 20, 0));
            }
            //Если это игрок - накладываем статус фриза
            if (entity.getType() == EntityType.PLAYER && randomNumber < 0.5f) {
                livingEntity.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), 20, 0));
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    public FrostBladeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
