package com.example.upgradedwolves.client;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.entities.UpgradedWolfRenderer;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UpgradedWolves.ModId)
public class ClientHandler {
    @OnlyIn(Dist.CLIENT)
    public static void setup(){
        RenderingRegistry.registerEntityRenderingHandler(EntityType.WOLF, UpgradedWolfRenderer::new);
    }
}