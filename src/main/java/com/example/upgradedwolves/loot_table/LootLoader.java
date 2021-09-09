package com.example.upgradedwolves.loot_table;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.upgradedwolves.UpgradedWolves;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.SimpleResource;
import net.minecraft.util.ResourceLocation;

public class LootLoader {
    List<BasicLootModel> lootModelList;

    public LootLoader(ResourceLocation resource){
        try{
            SimpleResource iresource = (SimpleResource) Minecraft.getInstance().getResourceManager().getResource(resource);
            Gson lootData =  new Gson();
            Type listOfMyClassObject = new TypeToken<ArrayList<BasicLootModel>>() {}.getType();
            lootModelList = lootData.fromJson( new InputStreamReader(iresource.getInputStream()),listOfMyClassObject);
        } catch (IOException e) {
            LogManager.getLogger().error("Failed to load loot table " + e.getMessage() );
        }
    }

    public LootLoader(String tableName){
        this(UpgradedWolves.getId("loot_tables/" + tableName + ".json"));
    }
}
