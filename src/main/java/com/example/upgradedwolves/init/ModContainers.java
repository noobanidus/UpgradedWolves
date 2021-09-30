package com.example.upgradedwolves.init;

import com.example.upgradedwolves.containers.WolfContainer;

import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.RegistryObject;

public class ModContainers {
    public static RegistryObject<MenuType<WolfContainer>> WOLF_CONTAINER;

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event){    
        WOLF_CONTAINER = IForgeContainerType.create(WolfContainer::createContainerClientSide);
        WOLF_CONTAINER.setRegistryName("wolf_container");
        event.getRegistry().register(WOLF_CONTAINER);
    }
}