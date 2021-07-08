package com.example.upgradedwolves.client;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.containers.WolfScreen;
import com.example.upgradedwolves.entities.UpgradedWolfRenderer;
import com.example.upgradedwolves.init.ModContainers;
import com.example.upgradedwolves.init.ModEntities;
import com.example.upgradedwolves.loot_table.init.ModGlobalLootTableModifier;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = UpgradedWolves.ModId,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler {
    @OnlyIn(Dist.CLIENT)
    public static void setup(){
        RenderingRegistry.registerEntityRenderingHandler(EntityType.WOLF, UpgradedWolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.tennisBallEntityType,
            manager -> new SpriteRenderer<>(manager,Minecraft.getInstance().getItemRenderer()));
            RenderingRegistry.registerEntityRenderingHandler(ModEntities.flyingDiskEntityType,
            manager -> new SpriteRenderer<>(manager,Minecraft.getInstance().getItemRenderer()));

        ScreenManager.registerFactory(ModContainers.WOLF_CONTAINER, WolfScreen::new);
    }

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        if(event.includeServer()) {
            gen.addProvider(new ModGlobalLootTableModifier(gen));
        }
    }
}