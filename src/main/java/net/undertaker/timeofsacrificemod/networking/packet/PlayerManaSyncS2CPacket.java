package net.undertaker.timeofsacrificemod.networking.packet;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.undertaker.timeofsacrificemod.client.ClientPlayerManaData;

import java.util.function.Supplier;

public class PlayerManaSyncS2CPacket {
    private final int playerMana;

    public PlayerManaSyncS2CPacket(int playerMana, LocalPlayer player) {
        this.playerMana = playerMana;
    }

    public PlayerManaSyncS2CPacket(FriendlyByteBuf buf) {
        this.playerMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(playerMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //HERE CLIENT
            ClientPlayerManaData.set(playerMana);

        });
        return true;
    }
}
