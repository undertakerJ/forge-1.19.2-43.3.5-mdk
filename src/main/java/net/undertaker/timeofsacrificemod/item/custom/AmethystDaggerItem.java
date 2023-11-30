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
import net.minecraft.world.entity.EquipmentSlot;
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
import net.undertaker.timeofsacrificemod.item.ModItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static net.minecraft.world.entity.EquipmentSlot.*;

@Mod.EventBusSubscriber(modid = TimeOfSacrifice.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
// Продлеваем класс предмета
public class AmethystDaggerItem extends SwordItem {
    // Отключаем разящий удар
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.SWORD_SWEEP.equals(false);
    }

    // Делаем функцию на проверку моба на курсоре
    private @Nullable EntityHitResult getEntityAtCursor(Player player, float maxDistance) {
        Vec3 lookVector = player.getLookAngle().scale(maxDistance);
        Vec3 startPos = player.getEyePosition();
        Vec3 endPos = startPos.add(lookVector);
        AABB searchArea = player.getBoundingBox().expandTowards(lookVector).inflate(1);
        Predicate<Entity> filter = LivingEntity.class::isInstance;
        return ProjectileUtil.getEntityHitResult(
                player.level, player, startPos, endPos, searchArea, filter);
    }

    // При нажатии лкм на энтити
    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        // Если энтити живой(моб, игрок)
        if (entity instanceof LivingEntity livingEntity) {
            // Получаем случайный флот из игрока
            float randomNumber = player.getRandom().nextFloat();
            //Если флот <0.25f, накладываем эффекты
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

    // Это описание предмета
    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("tooltip.amethyst_dagger_item"));
            components.add(Component.translatable("tooltip.amethyst_dagger_item1"));
            components.add(Component.translatable("tooltip.amethyst_dagger_item2"));
            components.add(Component.translatable("tooltip.amethyst_dagger_item3"));
            components.add(Component.translatable("tooltip.amethyst_dagger_item4"));
            components.add(Component.translatable("tooltip.amethyst_dagger_item5"));
            components.add(Component.translatable("tooltip.amethyst_dagger_item6"));
            components.add(Component.translatable("tooltip.amethyst_dagger_item7").withStyle(ChatFormatting.DARK_GRAY));

        } else {
            components.add(Component.translatable("tooltip.amethyst_dagger_item.shift").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    // Гарантированный крит при эффекте
    @SubscribeEvent
    public static void onCriticalHitEvent(CriticalHitEvent event) {
        Player player = event.getEntity();
        MobEffectInstance critEffect = player.getEffect(ModEffects.GUARANTEED_CRIT.get());
        // Проверка  есть ли на игроке эффект Гарантированого Крита
        if (critEffect != null && critEffect.getAmplifier() == 3) {
            // Разрешаем крит
            event.setResult((CriticalHitEvent.Result.ALLOW));
            //Проверяем надет ли полный сет шадоу брони
            boolean shadowFullSet = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SHADOW_ASSASSIN_HELMET.get() &&
                    player.getItemBySlot(CHEST).getItem() == ModItems.SHADOW_ASSASSIN_CHESTPLATE.get() &&
                    player.getItemBySlot(LEGS).getItem() == ModItems.SHADOW_ASSASSIN_LEGGINGS.get() &&
                    player.getItemBySlot(FEET).getItem() == ModItems.SHADOW_ASSASSIN_BOOTS.get();
            if(shadowFullSet){
                // Модификатор крит урона для сета
                event.setDamageModifier(4f);}
            else {
            // Модификатор крит урона
            event.setDamageModifier(2f);}
            // Очищаем эффекты у игрока
            player.removeEffect(ModEffects.GUARANTEED_CRIT.get());
        }
    }

    // Использование предмета
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        EntityHitResult entityAtCursor = getEntityAtCursor(player, 16);
        // Проверка чтобы это было на сервере и то что нажат шифт у игрока
        if (!level.isClientSide() && player.isShiftKeyDown() && entityAtCursor != null) {
                // Получаем координаты энитити на курсоре
                double x = entityAtCursor.getEntity().getX();
                double y = entityAtCursor.getEntity().getY();
                double z = entityAtCursor.getEntity().getZ();
                //Получаем её вектор взгляда в -1
                Vec3 lookVector = entityAtCursor.getEntity().getLookAngle().scale(-1);
                //Создаем координаты учитывая линию взгляда, и получаем координаты за её спиной
                double newX = x + lookVector.x();
                double newY = y + lookVector.y();
                double newZ = z + lookVector.z();
                //Добавляем эффекты
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 3 * 20, 2));
                player.addEffect(new MobEffectInstance(ModEffects.GUARANTEED_CRIT.get(), 10 * 20, 3));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3 * 20, 9));
            //Ставим перезарядку на предмет
            player.getCooldowns().addCooldown(this, 10 * 20);
                //Телепортируем игрока по координатам
                player.teleportTo(newX, newY , newZ);
                //Ставим линию вгляда игрока
                player.lookAt(EntityAnchorArgument.Anchor.EYES, getEntityAtCursor(player, 16).getEntity().getEyePosition());
                //Тратим прочность за использование
                player.getItemInHand(interactionHand).hurtAndBreak(8, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }

        return super.use(level, player, interactionHand);
    }
    public AmethystDaggerItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }
}