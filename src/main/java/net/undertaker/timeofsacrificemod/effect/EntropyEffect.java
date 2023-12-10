package net.undertaker.timeofsacrificemod.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;

public class EntropyEffect extends MobEffect {
    public EntropyEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory.HARMFUL, 3124687);

    }
    public static final DamageSource ENTROPY = (new DamageSource(TimeOfSacrifice.MOD_ID + "_entropy_effect").bypassArmor().bypassMagic());
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {
            pLivingEntity.hurt(ENTROPY.bypassInvul().bypassArmor().bypassMagic(), 2);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
