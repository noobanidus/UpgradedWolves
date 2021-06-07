package com.example.upgradedwolves.itemHandler;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.items.TennisBall;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;


@ObjectHolder(UpgradedWolves.ModId)
@Mod.EventBusSubscriber(modid = UpgradedWolves.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WolfToysHandler {

    public static TennisBall TENNISBALL = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        event.getRegistry().register(new TennisBall());
    }
}
