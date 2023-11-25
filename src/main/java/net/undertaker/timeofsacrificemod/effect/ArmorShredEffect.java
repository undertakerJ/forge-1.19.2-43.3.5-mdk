package net.undertaker.timeofsacrificemod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorShredEffect extends MobEffect {
    public ArmorShredEffect() {
        super(MobEffectCategory.HARMFUL, 123);
        addAttributeModifier(Attributes.ARMOR,"8d7a29f3-c849-4ea6-a5cb-a839a464544f",-0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
