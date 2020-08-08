package com.example.upgradedwolves.init;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.screens.WolfContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(
        ForgeRegistries.CONTAINERS, UpgradedWolves.ModId
    );

    public static final RegistryObject<ContainerType<WolfContainer>> WOLF_CONTAINER = CONTAINER_TYPES.register("wolf_container", () -> IForgeContainerType.create(WolfContainer::new)));
}