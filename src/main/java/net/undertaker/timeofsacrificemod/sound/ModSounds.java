package net.undertaker.timeofsacrificemod.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TimeOfSacrifice.MOD_ID);

    public static final RegistryObject<SoundEvent> STIMULATOR_USED =
            registerSoundEvent("stimulator_used");
    public static final RegistryObject<SoundEvent> KATANA_ATTACK_1 =
            registerSoundEvent("katana_slash1"),
            KATANA_ATTACK_2 =
                    registerSoundEvent("katana_slash2"),
            KATANA_ATTACK_3 =
                    registerSoundEvent("katana_slash3");

    public static final RegistryObject<SoundEvent> KATANA_SWEEP =
            registerSoundEvent("katana_sweep_1");
 public static final RegistryObject<SoundEvent> BANDAGE_USAGE =
            registerSoundEvent("bandage_usage");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(TimeOfSacrifice.MOD_ID, name);

                return SOUND_EVENTS.register(name,() -> new SoundEvent(new ResourceLocation(TimeOfSacrifice.MOD_ID, name)));
    }
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
