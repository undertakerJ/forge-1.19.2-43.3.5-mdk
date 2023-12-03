package net.undertaker.timeofsacrificemod.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.effect.ModEffects;
import net.undertaker.timeofsacrificemod.item.ModItems;

import java.util.Iterator;

import static net.minecraft.world.entity.EquipmentSlot.*;
import static net.undertaker.timeofsacrificemod.item.ModItems.DWARFS_HAMMER;

@Mod.EventBusSubscriber(modid = TimeOfSacrifice.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBusEvents {
    @SubscribeEvent
    public static void storeProtectedItems(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Inventory inventory = player.getInventory();
        ItemStack dwarfsHammer = new ItemStack(DWARFS_HAMMER.get());

        for (int slot = 0; slot < 41; slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack.getItem() == dwarfsHammer.getItem()) {
                addProtectedItem(player, slot, stack);
            }
        }
    }
    private static void addProtectedItem(Player player, int slot, ItemStack stack) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains("ProtectedItems")) {
            data.put("ProtectedItems", new CompoundTag());
        }
        CompoundTag protectedItemsTag = data.getCompound("ProtectedItems");
        protectedItemsTag.put("Slot" + slot, stack.save(new CompoundTag()));
    }
    @SubscribeEvent
    public static void protectItemsFromDropping(LivingDropsEvent event) {
        CompoundTag data = event.getEntity().getPersistentData();
        if (!data.contains("ProtectedItems")) return;
        CompoundTag protectedItemsTag = data.getCompound("ProtectedItems");
        for (int slot = 0; slot < 41; slot++) {
            if (!protectedItemsTag.contains("Slot" + slot)) continue;
            ItemStack protectedStack = ItemStack.of(protectedItemsTag.getCompound("Slot" + slot));
            Iterator<ItemEntity> iterator = event.getDrops().iterator();
            while (iterator.hasNext()) {
                ItemEntity item = iterator.next();
                if (ItemStack.matches(protectedStack, item.getItem())) {
                    iterator.remove();
                    break;
                }
            }
        }
    }
    @SubscribeEvent
    public static void restoreProtectedItems(PlayerEvent.Clone event) {
        CompoundTag data = event.getOriginal().getPersistentData();
        if (!data.contains("ProtectedItems")) return;
        CompoundTag protectedItemsTag = data.getCompound("ProtectedItems");
        for (int slot = 0; slot < 41; slot++) {
            if (!protectedItemsTag.contains("Slot" + slot)) continue;
            ItemStack protectedStack = ItemStack.of(protectedItemsTag.getCompound("Slot" + slot));
            event.getEntity().getInventory().setItem(slot, protectedStack);
        }
    }
    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        ItemStack tossedItemStack = event.getEntity().getItem();
        ItemStack dwarfsHammer = new ItemStack(DWARFS_HAMMER.get());
        // Блокируем выкидывание предмета
        if (!ItemStack.isSame(dwarfsHammer, tossedItemStack)) {
            // Отменяем событие только если предмет не соответствует
            event.setCanceled(false);
        } else {
            // Не отменяем событие, если предмет соответствует
            event.setCanceled(true);
            event.getPlayer().addItem(dwarfsHammer);
        }
    }
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
}
