package com.example.upgradedwolves.client;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.containers.WolfScreen;
import com.example.upgradedwolves.entities.UpgradedWolfRenderer;
import com.example.upgradedwolves.init.ModContainers;
import com.example.upgradedwolves.init.ModEntities;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UpgradedWolves.ModId)
public class ClientHandler {
    @OnlyIn(Dist.CLIENT)
    public static void setup(){
        RenderingRegistry.registerEntityRenderingHandler(EntityType.WOLF, UpgradedWolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.tennisBallEntityType,
            manager -> new SpriteRenderer<>(manager,Minecraft.getInstance().getItemRenderer()));

        ScreenManager.registerFactory(ModContainers.WOLF_CONTAINER, WolfScreen::new);
    }
}