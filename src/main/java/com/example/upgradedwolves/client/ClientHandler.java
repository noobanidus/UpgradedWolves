package com.example.upgradedwolves.client;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.containers.WolfScreen;
import com.example.upgradedwolves.entities.UpgradedWolfModel;
import com.example.upgradedwolves.entities.UpgradedWolfRenderer;
import com.example.upgradedwolves.entities.plushy.MobPlushyRenderer;
import com.example.upgradedwolves.init.ModContainers;
import com.example.upgradedwolves.init.ModEntities;
import com.example.upgradedwolves.init.ModModelLayers;
import com.example.upgradedwolves.loot_table.init.ModGlobalLootTableModifier;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = UpgradedWolves.ModId,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler {
    @OnlyIn(Dist.CLIENT)
    public static void setup(){
        
        EntityRenderers.register(EntityType.WOLF, UpgradedWolfRenderer::new);
        EntityRenderers.register(ModEntities.mobPlushyEntityType,MobPlushyRenderer::new);
        EntityRenderers.register(ModEntities.tennisBallEntityType, ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.flyingDiskEntityType, ThrownItemRenderer::new);
        // EntityRenderers.register(ModEntities.tennisBallEntityType,
        //     manager -> new ItemEntityRenderer(manager,Minecraft.getInstance().getItemRenderer()));
        // EntityRenderers.register(ModEntities.flyingDiskEntityType,
        //     manager -> new ItemEntityRenderer(manager,Minecraft.getInstance().getItemRenderer()));

        // EntityRendererManager entityRendererManager = Minecraft.getInstance().getRenderManager();
        // entityRendererManager.register(ModEntities.mobPlushyEntityType, new MobPlushyRenderer(entityRendererManager));

        MenuScreens.register(ModContainers.WOLF_CONTAINER, WolfScreen::new);        
    }

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        if(event.includeServer()) {
            gen.addProvider(new ModGlobalLootTableModifier(gen));
        }
    }

    @SubscribeEvent
    public static void LayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event){        
        event.registerLayerDefinition(ModModelLayers.UPGRADED_WOLF, UpgradedWolfModel::createBodyLayer);
    }
}