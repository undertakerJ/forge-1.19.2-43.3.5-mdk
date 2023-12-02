package net.undertaker.timeofsacrificemod.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.undertaker.timeofsacrificemod.TimeOfSacrifice;
import net.undertaker.timeofsacrificemod.block.ModBlocks;
import net.undertaker.timeofsacrificemod.item.custom.*;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TimeOfSacrifice.MOD_ID);


    //TOOLS
    public static final RegistryObject<Item> PLATINUM_SWORD = ITEMS.register("platinum_sword",
            () -> new SwordItem(Tiers.DIAMOND, 3, -2.4f,
                    new Item.Properties()
                            .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                            .stacksTo(1)
                            .defaultDurability(750)));
    public static final RegistryObject<Item> PLATINUM_PICKAXE = ITEMS.register("platinum_pickaxe",
            () -> new PickaxeItem(Tiers.IRON, 1, -3,
                    new Item.Properties()
                            .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                            .stacksTo(1)
                            .defaultDurability(750)));
    public static final RegistryObject<Item> PLATINUM_AXE = ITEMS.register("platinum_axe",
            () -> new AxeItem(Tiers.IRON, 6, -3.1f,
                    new Item.Properties()
                            .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                            .stacksTo(1)
                            .defaultDurability(750)));
    public static final RegistryObject<Item> PLATINUM_SHOVEL = ITEMS.register("platinum_shovel",
            () -> new ShovelItem(Tiers.IRON, 0, -3,
                    new Item.Properties()
                            .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                            .stacksTo(1)
                            .defaultDurability(750)));
    public static final RegistryObject<Item> PLATINUM_HOE = ITEMS.register("platinum_hoe",
            () -> new HoeItem(Tiers.IRON, -2, -3,
                    new Item.Properties()
                            .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                            .stacksTo(1)
                            .defaultDurability(750)));
    public static final RegistryObject<Item> DRAGON_SLAYER_SWORD = ITEMS.register("dragon_slayer_sword",
            () ->
                    new DragonSlayerSwordItem(Tiers.NETHERITE, 10, -3.5f,
                            new Item.Properties()
                                    .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                                    .stacksTo(1)
                                    .defaultDurability(2000)
                                    .rarity(Rarity.EPIC)
                    )
    );
    public static final RegistryObject<Item> FROST_BLADE = ITEMS.register("frost_blade",
            () ->
                    new FrostBladeItem(Tiers.DIAMOND, 3, -2.4f,
                            new Item.Properties()
                                    .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                                    .stacksTo(1)
                                    .defaultDurability(784)
                                    .rarity(Rarity.UNCOMMON)
                    )
    );
    public static final RegistryObject<Item> AMETHYST_DAGGER = ITEMS.register("amethyst_dagger",
            () ->
                    new AmethystDaggerItem(Tiers.DIAMOND, 2, -2F,
                            new Item.Properties()
                                    .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                                    .stacksTo(1)
                                    .defaultDurability(334)
                                    .rarity(Rarity.RARE)));




    ///ARMOR
    public static final RegistryObject<Item> PLATINUM_HELMET = ITEMS.register("platinum_helmet",
            () -> new ArmorItem(ModArmorMaterials.PLATINUM, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> PLATINUM_CHESTPLATE = ITEMS.register("platinum_chestplate",
            () -> new ArmorItem(ModArmorMaterials.PLATINUM, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> PLATINUM_LEGGINGS = ITEMS.register("platinum_leggings",
            () -> new ArmorItem(ModArmorMaterials.PLATINUM, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> PLATINUM_BOOTS = ITEMS.register("platinum_boots",
            () -> new ArmorItem(ModArmorMaterials.PLATINUM, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));

     public static final RegistryObject<Item> SHADOW_ASSASSIN_HELMET = ITEMS.register("shadow_assassin_helmet",
            () -> new ShadowAssassinArmorItem(ModArmorMaterials.REINFORCED_SHADOW_CLOTH, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> SHADOW_ASSASSIN_CHESTPLATE = ITEMS.register("shadow_assassin_chestplate",
            () -> new ShadowAssassinArmorItem(ModArmorMaterials.REINFORCED_SHADOW_CLOTH, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> SHADOW_ASSASSIN_LEGGINGS = ITEMS.register("shadow_assassin_leggings",
            () -> new ShadowAssassinArmorItem(ModArmorMaterials.REINFORCED_SHADOW_CLOTH, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> SHADOW_ASSASSIN_BOOTS = ITEMS.register("shadow_assassin_boots",
            () -> new ShadowAssassinArmorItem(ModArmorMaterials.REINFORCED_SHADOW_CLOTH, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));


    //ITEMS
    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register("platinum_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> SPEED_CATALYST = ITEMS.register("speed_catalyst",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> STRENGTH_CATALYST = ITEMS.register("strength_catalyst",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> REGENERATION_CATALYST = ITEMS.register("regeneration_catalyst",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> CLOTH = ITEMS.register("cloth",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
     public static final RegistryObject<Item> SHADOW_CLOTH = ITEMS.register("shadow_cloth",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
     public static final RegistryObject<Item> REINFORCED_CLOTH = ITEMS.register("reinforced_cloth",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
     public static final RegistryObject<Item> REINFORCED_SHADOW_CLOTH = ITEMS.register("reinforced_shadow_cloth",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
   public static final RegistryObject<Item> PLATINUM_PLATE = ITEMS.register("platinum_plate",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> PLATINUM_ORE = ITEMS.register("raw_platinum_ore",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> MORTAR = ITEMS.register("mortar",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> SPEED_STIMULATOR = ITEMS.register("speed_stimulator",
            () -> new SpeedStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> REGENERATION_STIMULATOR = ITEMS.register("regeneration_stimulator",
            () -> new RegenerationStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
     public static final RegistryObject<Item> THE_LIGHT_ROD = ITEMS.register("the_light_rod",
            () -> new TheLightRodItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> AN_AMAZING_METAL_BALK = ITEMS.register("an_amazing_metal_balk",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> AN_AMAZING_METAL_BALK_COVERED_WITH_PLATINUM = ITEMS.register("an_amazing_metal_balk_covered_with_platinum",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)));
    public static final RegistryObject<Item> AN_AMAZING_TEMPERED_METAL_BALK_COVERED_WITH_PLATINUM = ITEMS.register("an_amazing_tempered_metal_balk_covered_with_platinum",
            () -> new AnBalkMemeItem(Tiers.NETHERITE, 1005, 10000f,
                    new Item.Properties()
                            .tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB)
                            .stacksTo(1)
                            .rarity(Rarity.EPIC)));

    //CUSTOM ITEMS

    public static final RegistryObject<Item> BANDAGE = ITEMS.register("bandage",
            () -> new BandageItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(8)));
   public static final RegistryObject<Item> SMOKE_BOMB = ITEMS.register("smoke_bomb",
            () -> new SmokeBombItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(16)));
    public static final RegistryObject<Item> HEALING_OINTMENT = ITEMS.register("healing_ointment",
            () -> new HealingOintmentItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(8)));
    public static final RegistryObject<Item> BANDAGE_WITH_OINTMENT = ITEMS.register("bandage_with_ointment",
            () -> new BandageWithOintmentItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(8)));
    public static final RegistryObject<Item> STRENGTH_STIMULATOR = ITEMS.register("strength_stimulator",
            () -> new StrengthStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> ULTIMATE_STIMULATOR = ITEMS.register("ultimate_stimulator",
            () -> new UltimateStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> EXPERIMENTAL_STIMULATOR = ITEMS.register("experimental_stimulator",
            () -> new ExperimentalStimulatorItem(new Item.Properties().tab(ModCreativeModeTab.TIME_OF_SACRIFICE_TAB).stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
