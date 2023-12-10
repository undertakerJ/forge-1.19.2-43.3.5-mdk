package net.undertaker.timeofsacrificemod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.networking.ModMessages;
import net.undertaker.timeofsacrificemod.networking.packet.*;
import net.undertaker.timeofsacrificemod.util.KeyBinding;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = TimeOfSacrifice.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {

            if (KeyBinding.MODE_1_KEY.consumeClick()) {
                ModMessages.sendToServer(new ChangeItemMode0C2SPacket());
            }
            if (KeyBinding.MODE_2_KEY.consumeClick()) {
                ModMessages.sendToServer(new ChangeItemMode1C2SPacket());
            }
            if (KeyBinding.MODE_3_KEY.consumeClick()) {
                ModMessages.sendToServer(new ChangeItemMode2C2SPacket());
            }
            if (KeyBinding.MODE_4_KEY.consumeClick()) {
                ModMessages.sendToServer(new ChangeItemMode3C2SPacket());
            }
            if (KeyBinding.MODE_5_KEY.consumeClick()) {
                ModMessages.sendToServer(new ChangeItemMode4C2SPacket());
            }
        }


    }


    @Mod.EventBusSubscriber(modid = TimeOfSacrifice.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.MODE_1_KEY);
            event.register(KeyBinding.MODE_2_KEY);
            event.register(KeyBinding.MODE_3_KEY);
            event.register(KeyBinding.MODE_4_KEY);
            event.register(KeyBinding.MODE_5_KEY);
        }
    }

}
