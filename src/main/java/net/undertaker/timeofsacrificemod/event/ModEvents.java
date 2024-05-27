package net.undertaker.timeofsacrificemod.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.mana.PlayerMana;
import net.undertaker.timeofsacrificemod.mana.PlayerManaProvider;
import net.undertaker.timeofsacrificemod.networking.ModMessages;
import net.undertaker.timeofsacrificemod.networking.packet.PlayerManaSyncS2CPacket;

import static net.undertaker.timeofsacrificemod.mana.PlayerManaProvider.PLAYER_MANA;


@Mod.EventBusSubscriber(modid = TimeOfSacrifice.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<net.minecraft.world.entity.Entity> event){
        if (event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PLAYER_MANA).isPresent()){
             event.addCapability(new ResourceLocation(TimeOfSacrifice.MOD_ID, "properties"), new PlayerManaProvider());
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PLAYER_MANA).ifPresent(oldStore -> {
                event.getEntity().getCapability(PLAYER_MANA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerMana.class);
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            event.player.getCapability(PLAYER_MANA).ifPresent(TOS_mana -> {
                if(TOS_mana.getTOS_mana() >= 0 && TOS_mana.getTOS_mana() <= TOS_mana.max_TOS_mana && event.player.getRandom().nextFloat() < 0.005f) {
                    TOS_mana.addTOS_mana(10);
                    event.player.sendSystemMessage(Component.literal("Current mana " + TOS_mana.getTOS_mana()));

                }
            });
        }
    }
//    int realmana = Minecraft.getInstance().player.getCapability(PLAYER_MANA).ifPresent(
//           TOS_mana -> TOS_mana.TOS_mana());

    public static class RenderGuiHandler{
        @SubscribeEvent
        public void onRenderGui(RenderGuiOverlayEvent.Post event){
                int mana = new PlayerMana().getTOS_mana();
                int maxmana = new PlayerMana().max_TOS_mana;
                String manaText = "Mana: " + mana + "/" + maxmana; // Customizable format
                Minecraft mc = Minecraft.getInstance();
                PoseStack poseStack = new PoseStack();
                poseStack.pushPose();
                poseStack.scale(2.0f, 2.0f, 1.0f); // Увеличить текст в два раза
                mc.font.draw(poseStack, manaText, 10,20, 0x00FFFF);

        }
    }
    @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event){
        if(!event.getLevel().isClientSide){
            if(event.getEntity() instanceof LocalPlayer player){
                event.getEntity().getCapability(PLAYER_MANA).ifPresent(TOS_mana -> {
                });
            }
        }
    }
}
