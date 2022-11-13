package com.example.upgradedwolves.itemHandler;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.items.FlyingDisk;
import com.example.upgradedwolves.items.MobPlushy;
import com.example.upgradedwolves.items.TennisBall;
import com.example.upgradedwolves.items.TugOfWarRopeItem;
import com.example.upgradedwolves.items.GoldenBone.EnchantedGoldenBone;
import com.example.upgradedwolves.items.GoldenBone.GoldenBone;
import com.example.upgradedwolves.utils.MobPlushyType;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;


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
    public static void registerItems(final RegisterEvent event){
        if(ForgeRegistries.ITEMS == event.getRegistryKey()){
            TENNISBALL = new TennisBall();
            GOLDENBONE = new GoldenBone();
            ENCHANTEDGOLDENBONE = new EnchantedGoldenBone();
            FLYINGDISK = new FlyingDisk();
            TUFOFWARROPE = new TugOfWarRopeItem();
            event.register(ForgeRegistries.Keys.ITEMS,helper ->{
                helper.register(UpgradedWolves.getId("tennis_ball"),TENNISBALL);
                helper.register(UpgradedWolves.getId("golden_bone"),GOLDENBONE);
                helper.register(UpgradedWolves.getId("enchanted_golden_bone"),ENCHANTEDGOLDENBONE);
                helper.register(UpgradedWolves.getId("flying_disk"),FLYINGDISK);
                helper.register(UpgradedWolves.getId("tug_of_war_rope"),TUFOFWARROPE);
                }
            );

            zombiePlushy = new MobPlushy(MobPlushyType.ZOMBIE);
            skeletonPlushy = new MobPlushy(MobPlushyType.SKELETON);
            creeperPlushy = new MobPlushy(MobPlushyType.CREEPER);
            event.register(ForgeRegistries.Keys.ITEMS, helper ->{
                helper.register(UpgradedWolves.getId("zombie_plush"),zombiePlushy);
                helper.register(UpgradedWolves.getId("skeleton_plush"),skeletonPlushy);
                helper.register(UpgradedWolves.getId("creeper_plush"),creeperPlushy);
                }
            );
        }
    }

}
