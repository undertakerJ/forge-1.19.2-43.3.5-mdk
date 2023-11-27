package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.effect.ModEffects;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = TimeOfSacrifice.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class AmethystDaggerItem extends SwordItem {

    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.SWORD_SWEEP.equals(false);
    }

    private @Nullable EntityHitResult getEntityAtCursor(Player player, float maxDistance) {
        Vec3 lookVector = player.getLookAngle().scale(maxDistance);
        Vec3 startPos = player.getEyePosition();
        Vec3 endPos = startPos.add(lookVector);
        AABB searchArea = player.getBoundingBox().expandTowards(lookVector).inflate(1);
        Predicate<Entity> filter = LivingEntity.class::isInstance;
        return ProjectileUtil.getEntityHitResult(
                player.level, player, startPos, endPos, searchArea, filter);
    }
    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            float randomNumber = player.getRandom().nextFloat();
            if (randomNumber < 0.25f) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 30 * 20, 0));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30 * 20, 0));
                livingEntity.addEffect(new MobEffectInstance(ModEffects.ARMOR_SHRED.get(), 30 * 20, 1));
            }
            if (entity.getType() == EntityType.PLAYER) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1 * 60 * 20, 0));
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()){
            components.add(Component.literal("Applies on hit with 25% chance next effects for 30 seconds:"));
            components.add(Component.literal("Poison, Slowness, Armor Shred II"));
            components.add(Component.literal("Attacking player additional apply a glowing effect for 60 seconds"));
            components.add(Component.literal("Press SHIFT+RMB to entity in 12 block to teleport behind"));
            components.add(Component.literal("them, and gain Invicible III for 3 seconds and"));
            components.add(Component.literal("Guaranteed Crit effect for 10 seconds with x2.5 damage."));
            components.add(Component.literal("Teleport consumes 8 durability each time. Reload - 10 seconds. "));
            components.add(Component.literal("Guaranteed Crit - make your first attack critical").withStyle(ChatFormatting.DARK_GRAY));

        } else {
            components.add(Component.literal("Hold SHIFT for more info").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
    @SubscribeEvent
    public static void onCriticalHitEvent(CriticalHitEvent event) {
        Player player = event.getEntity();
        // Проверка  есть ли на игроке эффект Гарантированого Крита
        if (player.hasEffect(ModEffects.GUARANTEED_CRIT.get())) {
            // Разрешаем крит
            event.setResult((CriticalHitEvent.Result.ALLOW));
            // Модификатор крит урона
            event.setDamageModifier(2.5f);
            // Очищаем эффекты у игрока
            player.removeEffect(ModEffects.GUARANTEED_CRIT.get());
        }
    }


    @Override
    public  InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide()) {
            if (player.isShiftKeyDown()) {
                EntityHitResult entityAtCursor = getEntityAtCursor(player, 12);
                if (entityAtCursor != null) {
                    if (!player.level.isClientSide) {
                        double x = entityAtCursor.getEntity().getX();
                        double y = entityAtCursor.getEntity().getY();
                        double z = entityAtCursor.getEntity().getZ();

                        Vec3 lookVector = entityAtCursor.getEntity().getLookAngle().scale(-1);

                        double newX = x + lookVector.x();
                        double newY = y + lookVector.y();
                        double newZ = z + lookVector.z();

                        player.lookAt(EntityAnchorArgument.Anchor.EYES, getEntityAtCursor(player, 16).getEntity().getEyePosition());
                        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 3 * 20, 2));
                        player.addEffect(new MobEffectInstance(ModEffects.GUARANTEED_CRIT.get(), 10 * 20, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3 * 20, 9));
                        player.teleportTo(newX, newY, newZ);
                        player.getItemInHand(interactionHand).hurtAndBreak(8, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        player.getCooldowns().addCooldown(this,10*20);
                        player.setInvulnerable(true);
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }

    public AmethystDaggerItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }
}