package com.example.upgradedwolves.init;

import com.example.upgradedwolves.containers.WolfContainer;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public class ModContainers {
    public static MenuType<WolfContainer> WOLF_CONTAINER;

    @SubscribeEvent
    public static void registerContainers(final RegisterEvent event){
        if(ForgeRegistries.MENU_TYPES == event.getRegistryKey()){
            MenuType<WolfContainer> wolfContainer = IForgeMenuType.create(WolfContainer::createContainerClientSide);    
            event.register(ForgeRegistries.Keys.MENU_TYPES,helper -> {
                helper.register("wolf_container",wolfContainer);
            });
            WOLF_CONTAINER = wolfContainer;
        }
    }
}