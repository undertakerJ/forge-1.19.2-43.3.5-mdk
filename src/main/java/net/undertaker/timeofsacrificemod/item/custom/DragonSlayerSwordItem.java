package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.undertaker.timeofsacrificemod.effect.ModEffects;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DragonSlayerSwordItem extends SwordItem {

    public DragonSlayerSwordItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Applies on hit with 50% chance next effects for 30 seconds: Slowness and Armor Shred IV." +
                    " If this is player, affects them with confusion for 30 seconds on hit." +
                    " Deal additional 25 damage to undead."));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            float randomNumber = player.getRandom().nextFloat();
            if (randomNumber < 0.5f) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30 * 20, 0));
                livingEntity.addEffect(new MobEffectInstance(ModEffects.ARMOR_SHRED.get(), 30 * 20, 3));
            }
            if (entity.getType() == EntityType.PLAYER) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 30 * 20, 3));
            }
            if (entity instanceof Zombie|| entity instanceof WitherSkeleton || entity instanceof Skeleton) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.HEAL, 20, 5));
            }
        }


        return super.onLeftClickEntity(stack, player, entity);


    }
}
