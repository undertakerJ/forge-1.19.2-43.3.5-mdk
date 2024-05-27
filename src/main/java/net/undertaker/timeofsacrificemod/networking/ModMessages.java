package net.undertaker.timeofsacrificemod.networking;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.networking.packet.*;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(TimeOfSacrifice.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.8")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ExampleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExampleC2SPacket::new)
                .encoder(ExampleC2SPacket::toBytes)
                .consumerMainThread(ExampleC2SPacket::handle)
                .add();

        net.messageBuilder(ChangeItemMode0C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeItemMode0C2SPacket::new)
                .encoder(ChangeItemMode0C2SPacket::toBytes)
                .consumerMainThread(ChangeItemMode0C2SPacket::handle)
                .add();

        net.messageBuilder(ChangeItemMode1C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeItemMode1C2SPacket::new)
                .encoder(ChangeItemMode1C2SPacket::toBytes)
                .consumerMainThread(ChangeItemMode1C2SPacket::handle)
                .add();

        net.messageBuilder(ChangeItemMode2C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeItemMode2C2SPacket::new)
                .encoder(ChangeItemMode2C2SPacket::toBytes)
                .consumerMainThread(ChangeItemMode2C2SPacket::handle)
                .add();

        net.messageBuilder(ChangeItemMode3C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeItemMode3C2SPacket::new)
                .encoder(ChangeItemMode3C2SPacket::toBytes)
                .consumerMainThread(ChangeItemMode3C2SPacket::handle)
                .add();

        net.messageBuilder(ChangeItemMode4C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeItemMode4C2SPacket::new)
                .encoder(ChangeItemMode4C2SPacket::toBytes)
                .consumerMainThread(ChangeItemMode4C2SPacket::handle)
                .add();
net.messageBuilder(PlayerManaSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerManaSyncS2CPacket::new)
                .encoder(PlayerManaSyncS2CPacket::toBytes)
                .consumerMainThread(PlayerManaSyncS2CPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(PlayerManaSyncS2CPacket message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }


}
