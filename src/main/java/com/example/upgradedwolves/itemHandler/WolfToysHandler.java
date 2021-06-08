package com.example.upgradedwolves.itemHandler;

import com.example.upgradedwolves.items.TennisBall;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class WolfToysHandler {

    public static TennisBall TENNISBALL = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        TENNISBALL = new TennisBall();
        event.getRegistry().register(TENNISBALL);
    }
}
