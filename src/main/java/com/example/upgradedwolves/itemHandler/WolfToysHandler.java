package com.example.upgradedwolves.itemHandler;

import com.example.upgradedwolves.items.TennisBall;
import com.example.upgradedwolves.items.GoldenBone.EnchantedGoldenBone;
import com.example.upgradedwolves.items.GoldenBone.GoldenBone;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class WolfToysHandler {

    public static TennisBall TENNISBALL = null;
    public static GoldenBone GOLDENBONE = null;
    public static EnchantedGoldenBone ENCHANTEDGOLDENBONE = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        TENNISBALL = new TennisBall();
        event.getRegistry().register(TENNISBALL);
        GOLDENBONE = new GoldenBone();
        event.getRegistry().register(GOLDENBONE);
        ENCHANTEDGOLDENBONE = new EnchantedGoldenBone();
        event.getRegistry().register(ENCHANTEDGOLDENBONE);
    }

}
