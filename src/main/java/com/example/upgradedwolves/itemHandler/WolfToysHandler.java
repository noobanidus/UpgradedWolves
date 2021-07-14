package com.example.upgradedwolves.itemHandler;

import com.example.upgradedwolves.items.FlyingDisk;
import com.example.upgradedwolves.items.TennisBall;
import com.example.upgradedwolves.items.TugOfWarRopeItem;
import com.example.upgradedwolves.items.GoldenBone.EnchantedGoldenBone;
import com.example.upgradedwolves.items.GoldenBone.GoldenBone;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class WolfToysHandler {

    public static TennisBall TENNISBALL = null;
    public static FlyingDisk FLYINGDISK = null;
    public static GoldenBone GOLDENBONE = null;
    public static EnchantedGoldenBone ENCHANTEDGOLDENBONE = null;
    public static TugOfWarRopeItem TUFOFWARROPE = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        TENNISBALL = new TennisBall();
        event.getRegistry().register(TENNISBALL);
        GOLDENBONE = new GoldenBone();
        event.getRegistry().register(GOLDENBONE);
        ENCHANTEDGOLDENBONE = new EnchantedGoldenBone();
        event.getRegistry().register(ENCHANTEDGOLDENBONE);
        FLYINGDISK = new FlyingDisk();
        event.getRegistry().register(FLYINGDISK);
        TUFOFWARROPE = new TugOfWarRopeItem();
        event.getRegistry().register(TUFOFWARROPE);
    }

}
