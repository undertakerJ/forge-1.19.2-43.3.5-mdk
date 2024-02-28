package net.undertaker.timeofsacrificemod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.undertaker.timeofsacrificemod.item.custom.KatanaItem;
import net.undertaker.timeofsacrificemod.item.custom.RealityDistortionGloveItem;

import java.util.function.Supplier;

public class ChangeItemMode3C2SPacket {
    public ChangeItemMode3C2SPacket() {

    }

    public ChangeItemMode3C2SPacket(FriendlyByteBuf buf) {

    }
    public void toBytes(FriendlyByteBuf buf){

    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //HERE SERVER
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            ItemStack heldItem = player.getMainHandItem();

            if(heldItem.getItem() instanceof RealityDistortionGloveItem || heldItem.getItem() instanceof KatanaItem){
                heldItem.getOrCreateTag().putInt("Mode", 3);
                if (heldItem.getItem() instanceof RealityDistortionGloveItem){
                    heldItem.setHoverName(Component.translatable("rdg.item_mode3"));}
                else {
                    heldItem.setHoverName(Component.translatable("katana.item_mode3"));}
            }
        });
        return true;
    }
}
