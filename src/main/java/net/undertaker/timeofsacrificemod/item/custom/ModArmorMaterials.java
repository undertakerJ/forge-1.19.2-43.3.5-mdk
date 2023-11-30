package net.undertaker.timeofsacrificemod.item.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.item.ModItems;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    PLATINUM( "platinum",35,new int[]{3,7,5,3}, 25,SoundEvents.ARMOR_EQUIP_IRON,
            1f,0f, () ->
    {
        return Ingredient.of(ModItems.PLATINUM_INGOT.get());
    }),
    REINFORCED_SHADOW_CLOTH("reinforced_shadow_cloth", 25, new int[]{2,6,4,2}, 35, SoundEvents.ARMOR_EQUIP_IRON,
            2f, 0.1f, () -> {
        return Ingredient.of(ModItems.REINFORCED_SHADOW_CLOTH.get());
    });
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};

    ModArmorMaterials(String name, int durability, int[] protection, int enchantability, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {

        this.name = name;
        this.durabilityMultiplier = durability;
        this.slotProtections = protection;
        this.enchantmentValue = enchantability;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }


    public int getDurabilityForSlot(EquipmentSlot slot) {
        return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot slot) {
        return this.slotProtections[slot.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return TimeOfSacrifice.MOD_ID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
