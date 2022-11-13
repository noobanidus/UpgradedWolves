package com.example.upgradedwolves.loot_table.init;

import java.util.ArrayList;
import java.util.List;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;
import com.example.upgradedwolves.loot_table.modifier.ChestLootModifier;
import com.mojang.serialization.Codec;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ModGlobalLootTableModifier extends GlobalLootModifierProvider{
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS,UpgradedWolves.ModId);
    public static final net.minecraftforge.registries.RegistryObject<Codec<ChestLootModifier>> CHEST_LOOT = LOOT_MODIFIERS.register("chest_loot", ChestLootModifier.CODEC);
    
    public ModGlobalLootTableModifier(DataGenerator gen) {
        super(gen, UpgradedWolves.ModId);
    }

    @Override
    protected void start() {
        addChestLootModifiers();
    }

    protected void addChestLootModifiers() {
        List<ChestLootModifier.ChestItem> enchantedChestDrops = new ArrayList<ChestLootModifier.ChestItem>();
        List<ChestLootModifier.ChestItem> normalChestDrops = new ArrayList<ChestLootModifier.ChestItem>();
        
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
        
        enchantedChestDrops.add(new ChestLootModifier.ChestItem(WolfToysHandler.GOLDENBONE,0.3f,1));
        enchantedChestDrops.add(new ChestLootModifier.ChestItem(WolfToysHandler.ENCHANTEDGOLDENBONE, 0.06f,1));
        enchantedChestDrops.add(new ChestLootModifier.ChestItem(Items.DANDELION, 0.5f,10));

        normalChestDrops.add(new ChestLootModifier.ChestItem(WolfToysHandler.GOLDENBONE,0.1f,1));
        normalChestDrops.add(new ChestLootModifier.ChestItem(Items.DANDELION, 0.3f,10));

        for (ResourceLocation resourceLocation : enchantedLootTables) {
            add(resourceLocation.getPath(), new ChestLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(resourceLocation).build(),
                },
                enchantedChestDrops
            ));
        }

        for (ResourceLocation resourceLocation : normalLootTables) {
            add(resourceLocation.getPath(), new ChestLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(resourceLocation).build(),
                },
                normalChestDrops
            ));
        }
    }
}
