package net.undertaker.timeofsacrificemod.item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.item.custom.*;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TimeOfSacrifice.MOD_ID);

    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> PLATINUM_ORE = ITEMS.register("raw_platinum_ore",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> SPEED_STIMULATOR = ITEMS.register("speed_stimulator",
            () -> new SpeedStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> REGENERATION_STIMULATOR = ITEMS.register("regeneration_stimulator",
            () -> new RegenerationStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> STRENGTH_STIMULATOR = ITEMS.register("strength_stimulator",
            () -> new StrengthStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> ULTIMATE_STIMULATOR = ITEMS.register("ultimate_stimulator",
            () -> new UltimateStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> EXPERIMENTAL_STIMULATOR = ITEMS.register("experimental_stimulator",
            () -> new ExperimentalStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> DRAGON_SLAYER_SWORD = ITEMS.register("dragon_slayer_sword",
            () ->
                 new SwordItem(Tiers.NETHERITE, 20, -3.5f,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                                .stacksTo(1)
                                .defaultDurability(2000)
                                .rarity(Rarity.EPIC))
            );
    public static final RegistryObject<Item> AMETHYST_DAGGER =  ITEMS.register("amethyst_dagger",
            () ->
                new AmethystDaggerItem(Tiers.DIAMOND, 3, -2F,
                        new Item.Properties()
                                .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                                .stacksTo(1)
                                .defaultDurability(334)
                                .rarity(Rarity.RARE)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
