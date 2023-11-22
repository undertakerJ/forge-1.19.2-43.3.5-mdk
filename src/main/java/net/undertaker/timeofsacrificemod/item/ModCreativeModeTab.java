package net.undertaker.timeofsacrificemod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TIME_OF_SACRIFICE_TAB = new CreativeModeTab("ToS_Tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PLATINUM_INGOT.get());
        }
    };
}
