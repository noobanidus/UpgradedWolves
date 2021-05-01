package com.example.upgradedwolves.init;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.containers.WolfContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static ContainerType<WolfContainer> WOLF_CONTAINER;

    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event){
        WOLF_CONTAINER = IForgeContainerType.create(WolfContainer::createContainerClientSide);
        WOLF_CONTAINER.setRegistryName("wolf_container");
        event.getRegistry().register(WOLF_CONTAINER);
    }
}