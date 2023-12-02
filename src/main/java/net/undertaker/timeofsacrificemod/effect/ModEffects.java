package net.undertaker.timeofsacrificemod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;

public class ModEffects extends MobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TimeOfSacrifice.MOD_ID);

    public static final RegistryObject<MobEffect> ARMOR_SHRED = MOB_EFFECTS.register("armor_shred",
            () -> new ArmorShredEffect());
    public static final RegistryObject<MobEffect> GUARANTEED_CRIT = MOB_EFFECTS.register("guaranteed_crit",
            () -> new GuaranteedCritEffect());
    public static final RegistryObject<MobEffect> FREEZE = MOB_EFFECTS.register("freeze",
            () -> new FreezeEffect(MobEffectCategory.HARMFUL, 3124687));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
