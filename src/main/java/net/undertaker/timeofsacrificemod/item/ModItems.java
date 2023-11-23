package net.undertaker.timeofsacrificemod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.item.custom.StimulatorItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TimeOfSacrifice.MOD_ID);

    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> PLATINUM_ORE = ITEMS.register("raw_platinum_ore",
            () -> new StimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
 public static final RegistryObject<Item> STIMULATOR = ITEMS.register("stimulator",
            () -> new StimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
