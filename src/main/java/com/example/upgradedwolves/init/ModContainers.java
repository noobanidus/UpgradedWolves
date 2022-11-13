package com.example.upgradedwolves.init;

import com.example.upgradedwolves.containers.WolfContainer;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public class ModContainers {
    public static MenuType<WolfContainer> WOLF_CONTAINER = IForgeMenuType.create(WolfContainer::createContainerClientSide);

    @SubscribeEvent
    public static void registerContainers(final RegisterEvent event){
        event.register(ForgeRegistries.Keys.MENU_TYPES,helper -> {
            helper.register("wolf_container",WOLF_CONTAINER);
        });
    }
}