package com.example.upgradedwolves.init;

import com.example.upgradedwolves.containers.WolfContainer;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModContainers {
    public static MenuType<WolfContainer> WOLF_CONTAINER;

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<MenuType<?>> event){
        MenuType<WolfContainer> wolfContainer = IForgeMenuType.create(WolfContainer::createContainerClientSide);    
        wolfContainer.setRegistryName("wolf_container");
        event.getRegistry().register(wolfContainer);
        WOLF_CONTAINER = wolfContainer;
    }
}