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
import net.undertaker.timeofsacrificemod.effect.ArmorShredEffect;
import net.undertaker.timeofsacrificemod.effect.ModEffects;


public class AmethystDaggerItem extends SwordItem {

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            float randomNumber = player.getRandom().nextFloat();
            if (randomNumber < 0.25f) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON,   10 * 20, 0));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1 * 60 * 20, 0));
              //  livingEntity.addEffect(new MobEffectInstance(ModEffects.ARMOR_SHRED, 30*20, 1));
            }
            if(entity.getType()==EntityType.PLAYER){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING,1*60*20,0));
            }
        }


        return super.onLeftClickEntity(stack, player, entity);
    }

    public AmethystDaggerItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

}