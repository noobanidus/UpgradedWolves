package com.example.upgradedwolves.loot_table.init;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;
import com.example.upgradedwolves.loot_table.modifier.ChestLootModifier;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.RandomValueRange;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.storage.loot.conditions.ILootCondition;

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
            BuiltInLootTables.ABANDONED_MINESHAFT,
            BuiltInLootTables.DESERT_PYRAMID,
            BuiltInLootTables.JUNGLE_TEMPLE,
            BuiltInLootTables.BASTION_TREASURE,
            BuiltInLootTables.STRONGHOLD_CROSSING,
            BuiltInLootTables.SIMPLE_DUNGEON,
            BuiltInLootTables.STRONGHOLD_CORRIDOR
        };

        ResourceLocation[] normalLootTables = new ResourceLocation[]{
            BuiltInLootTables.SHIPWRECK_TREASURE,
            BuiltInLootTables.FISHING_TREASURE,
            BuiltInLootTables.SPAWN_BONUS_CHEST,
            BuiltInLootTables.VILLAGE_TAIGA_HOUSE
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
