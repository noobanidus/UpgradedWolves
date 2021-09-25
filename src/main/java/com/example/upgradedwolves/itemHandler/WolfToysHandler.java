package com.example.upgradedwolves.itemHandler;

import com.example.upgradedwolves.items.FlyingDisk;
import com.example.upgradedwolves.items.MobPlushy;
import com.example.upgradedwolves.items.TennisBall;
import com.example.upgradedwolves.items.TugOfWarRopeItem;
import com.example.upgradedwolves.items.GoldenBone.EnchantedGoldenBone;
import com.example.upgradedwolves.items.GoldenBone.GoldenBone;
import com.example.upgradedwolves.utils.MobPlushyType;

import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class WolfToysHandler {

    public static TennisBall TENNISBALL = null;
    public static FlyingDisk FLYINGDISK = null;
    public static GoldenBone GOLDENBONE = null;
    public static EnchantedGoldenBone ENCHANTEDGOLDENBONE = null;
    public static TugOfWarRopeItem TUFOFWARROPE = null;
    public static MobPlushy zombiePlushy = null;
    public static MobPlushy skeletonPlushy = null;
    public static MobPlushy creeperPlushy = null;

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

        zombiePlushy = new MobPlushy("zombie_plush",MobPlushyType.ZOMBIE);
        event.getRegistry().register(zombiePlushy);
        skeletonPlushy = new MobPlushy("skeleton_plush",MobPlushyType.SKELETON);
        event.getRegistry().register(skeletonPlushy);
        creeperPlushy = new MobPlushy("creeper_plush",MobPlushyType.CREEPER);
        event.getRegistry().register(creeperPlushy);
    }

}
