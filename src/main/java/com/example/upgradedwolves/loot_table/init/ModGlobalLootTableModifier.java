package com.example.upgradedwolves.loot_table.init;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;
import com.example.upgradedwolves.loot_table.modifier.ChestLootModifier;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.loot.conditions.ILootCondition;

public class ModGlobalLootTableModifier extends GlobalLootModifierProvider{
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS,UpgradedWolves.ModId);
    public static final RegistryObject<ChestLootModifier.Serializer> CHEST_LOOT = LOOT_MODIFIERS.register("chest_loot", ChestLootModifier.Serializer::new);
    
    public ModGlobalLootTableModifier(DataGenerator gen) {
        super(gen, UpgradedWolves.ModId);
    }

    @Override
    protected void start() {
        addChestLootModifiers();
    }

    protected void addChestLootModifiers() {
        NonNullList<ChestLootModifier.ChestItem> enchantedChestDrops = NonNullList.create();
        NonNullList<ChestLootModifier.ChestItem> normalChestDrops = NonNullList.create();
        
        ResourceLocation[] enchantedLootTables = new ResourceLocation[]{
            LootTables.CHESTS_ABANDONED_MINESHAFT,
            LootTables.CHESTS_DESERT_PYRAMID,
            LootTables.CHESTS_JUNGLE_TEMPLE,
            LootTables.BASTION_TREASURE,
            LootTables.CHESTS_STRONGHOLD_CROSSING,
            LootTables.CHESTS_SIMPLE_DUNGEON,
            LootTables.CHESTS_STRONGHOLD_CORRIDOR
        };

        ResourceLocation[] normalLootTables = new ResourceLocation[]{
            LootTables.CHESTS_SHIPWRECK_TREASURE,
            LootTables.GAMEPLAY_FISHING_TREASURE,
            LootTables.CHESTS_SPAWN_BONUS_CHEST,
            LootTables.CHESTS_VILLAGE_VILLAGE_TAIGA_HOUSE
        };
        
        enchantedChestDrops.add(new ChestLootModifier.ChestItem(WolfToysHandler.GOLDENBONE, new RandomValueRange(1, 1), 0.3f));
        enchantedChestDrops.add(new ChestLootModifier.ChestItem(WolfToysHandler.ENCHANTEDGOLDENBONE, new RandomValueRange(1, 1), 0.06f));
        enchantedChestDrops.add(new ChestLootModifier.ChestItem(Items.DANDELION, new RandomValueRange(2, 10), 0.5f));

        normalChestDrops.add(new ChestLootModifier.ChestItem(WolfToysHandler.GOLDENBONE, new RandomValueRange(1, 1), 0.1f));
        normalChestDrops.add(new ChestLootModifier.ChestItem(Items.DANDELION, new RandomValueRange(2, 10), 0.3f));

        for (ResourceLocation resourceLocation : enchantedLootTables) {
            add(resourceLocation.getPath(), CHEST_LOOT.get(), new ChestLootModifier(
                new ILootCondition[] {
                        LootTableIdCondition.builder(resourceLocation).build(),
                },
                enchantedChestDrops
            ));
        }

        for (ResourceLocation resourceLocation : normalLootTables) {
            add(resourceLocation.getPath(), CHEST_LOOT.get(), new ChestLootModifier(
                new ILootCondition[] {
                        LootTableIdCondition.builder(resourceLocation).build(),
                },
                normalChestDrops
            ));
        }
    }
}
