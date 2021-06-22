package com.example.upgradedwolves.itemHandler;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.items.TennisBall;
import com.example.upgradedwolves.items.GoldenBone.EnchantedGoldenBone;
import com.example.upgradedwolves.items.GoldenBone.GoldenBone;

import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class WolfToysHandler {

    public static TennisBall TENNISBALL = null;
    public static GoldenBone GOLDENBONE = null;
    public static EnchantedGoldenBone ENCHANTEDGOLDENBONE = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        TENNISBALL = new TennisBall();
        event.getRegistry().register(TENNISBALL);
        GOLDENBONE = new GoldenBone();
        event.getRegistry().register(GOLDENBONE);
        ENCHANTEDGOLDENBONE = new EnchantedGoldenBone();
        event.getRegistry().register(ENCHANTEDGOLDENBONE);
    }

    @SubscribeEvent
    public void lootTable(LootTableLoadEvent lootEvent){
        if(lootEvent.getName().equals(LootTables.CHESTS_ABANDONED_MINESHAFT) ||
            lootEvent.getName().equals(LootTables.CHESTS_DESERT_PYRAMID) ||
            lootEvent.getName().equals(LootTables.CHESTS_JUNGLE_TEMPLE) ||
            lootEvent.getName().equals(LootTables.BASTION_TREASURE) ||
            lootEvent.getName().equals(LootTables.CHESTS_STRONGHOLD_CROSSING) ||
            lootEvent.getName().equals(LootTables.CHESTS_SIMPLE_DUNGEON) ||
            lootEvent.getName().equals(LootTables.CHESTS_STRONGHOLD_CORRIDOR)
            ){
                LootTable table = lootEvent.getLootTableManager().getLootTableFromLocation(UpgradedWolves.getId("inject/dungeon_inject"));
                LootPool pool = table.getPool("high_gold_bone");
                lootEvent.getTable().addPool(pool);
        }
        else if(lootEvent.getName().equals(LootTables.CHESTS_SHIPWRECK_TREASURE) ||
                lootEvent.getName().equals(LootTables.GAMEPLAY_FISHING_TREASURE) ||
                lootEvent.getName().equals(LootTables.CHESTS_SPAWN_BONUS_CHEST) ||
                lootEvent.getName().equals(LootTables.CHESTS_VILLAGE_VILLAGE_TAIGA_HOUSE)                
        ){
                LootTable table = lootEvent.getLootTableManager().getLootTableFromLocation(UpgradedWolves.getId("inject/dungeon_inject"));
                LootPool pool = table.getPool("low_gold_bone");
                lootEvent.getTable().addPool(pool);
        }
    }
}
