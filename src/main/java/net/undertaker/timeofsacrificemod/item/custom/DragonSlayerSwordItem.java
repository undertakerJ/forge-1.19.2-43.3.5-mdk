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

public class DragonSlayerSwordItem extends SwordItem {
    public DragonSlayerSwordItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.literal("Requires Strength effect to use. With 50% applies Slowness II and"));
            components.add(Component.literal("Armor Shred IV, players get additional Confusion effect, for 30 seconds."));
            components.add(Component.literal("Deal additional 25 damage to undead."));
            components.add(Component.literal(" "));
            components.add(Component.literal("Armor Shred - reduces armor for 10% each level of debuff.").withStyle(ChatFormatting.DARK_GRAY));
            components.add(Component.literal(" "));
            components.add(Component.literal("HIS WEAPON WAS TOO LARGE TO RIGHTFULLY BE CALLED A SWORD.").withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("IT WAS LARGER, THICKER, HEAVIER AND CRUELER THAN ANY NORMAL BLADE.").withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("BY ALL ACCOUNTS - IT WAS NO MORE THAN A HULKING MASS OF IRON. ").withStyle(ChatFormatting.GRAY));
        } else {
            components.add(Component.literal("Hold SHIFT for more info").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (player.hasEffect(MobEffects.DAMAGE_BOOST) != true) {
            player.sendSystemMessage(Component.literal("Looks like you dont have enough 'Strength' to lift a sword."));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 10, 254));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 254));
        } else {
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
            player.removeEffect(MobEffects.WEAKNESS);
            if (entity instanceof LivingEntity livingEntity) {
                float randomNumber = player.getRandom().nextFloat();
                if (randomNumber < 0.5f) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30 * 20, 1));
                    livingEntity.addEffect(new MobEffectInstance(ModEffects.ARMOR_SHRED.get(), 30 * 20, 3));
                }
                if (entity.getType() == EntityType.PLAYER) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 30 * 20, 3));
                }
                if (entity instanceof Zombie || entity instanceof WitherSkeleton || entity instanceof Skeleton) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.HEAL, 20, 5));
                }
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}


