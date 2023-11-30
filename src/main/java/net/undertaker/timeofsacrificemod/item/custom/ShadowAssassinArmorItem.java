package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.undertaker.timeofsacrificemod.item.ModItems;

import static net.minecraft.world.entity.EquipmentSlot.*;

public class ShadowAssassinArmorItem extends ArmorItem {
    public ShadowAssassinArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        long time = level.getDayTime();
        //Создание проверки на полный сет
        boolean shadowFullSet = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SHADOW_ASSASSIN_HELMET.get() &&
                player.getItemBySlot(CHEST).getItem() == ModItems.SHADOW_ASSASSIN_CHESTPLATE.get() &&
                player.getItemBySlot(LEGS).getItem() == ModItems.SHADOW_ASSASSIN_LEGGINGS.get() &&
                player.getItemBySlot(FEET).getItem() == ModItems.SHADOW_ASSASSIN_BOOTS.get();
        //Если игрок на сервере, у него полный сет
        if (!level.isClientSide() && shadowFullSet) {
            BlockPos playerPos = new BlockPos(player.getX(), player.getY(), player.getZ());
            int lightLevel = level.getBrightness(LightLayer.BLOCK, playerPos);
            int lightLevelSky = level.getBrightness(LightLayer.SKY, playerPos);

            if ((time >= 12541 && time <= 23458) && lightLevel <= 3) {
                // Ночь и освещение ниже 3
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 1));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 2));
            } else if (lightLevel <= 3 && lightLevelSky <= 3) {
                // День и освещение ниже 3 и игрок не видит неба
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 1));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 2));
            }
        }

    }


}

