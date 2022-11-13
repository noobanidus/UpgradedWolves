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

    public static TennisBall TENNISBALL = new TennisBall();
    public static FlyingDisk FLYINGDISK = new FlyingDisk();
    public static GoldenBone GOLDENBONE = new GoldenBone();
    public static EnchantedGoldenBone ENCHANTEDGOLDENBONE = new EnchantedGoldenBone();
    public static TugOfWarRopeItem TUFOFWARROPE = new TugOfWarRopeItem();
    public static MobPlushy zombiePlushy = new MobPlushy(MobPlushyType.ZOMBIE);
    public static MobPlushy skeletonPlushy = new MobPlushy(MobPlushyType.SKELETON);
    public static MobPlushy creeperPlushy = new MobPlushy(MobPlushyType.CREEPER);

    @SubscribeEvent
    public static void registerItems(final RegisterEvent event){
        event.register(ForgeRegistries.Keys.ITEMS,helper ->{
            helper.register(UpgradedWolves.getId("tennis_ball"),TENNISBALL);
            helper.register(UpgradedWolves.getId("golden_bone"),GOLDENBONE);
            helper.register(UpgradedWolves.getId("enchanted_golden_bone"),ENCHANTEDGOLDENBONE);
            helper.register(UpgradedWolves.getId("flying_disk"),FLYINGDISK);
            helper.register(UpgradedWolves.getId("tug_of_war_rope"),TUFOFWARROPE);

            helper.register(UpgradedWolves.getId("zombie_plush"),zombiePlushy);
            helper.register(UpgradedWolves.getId("skeleton_plush"),skeletonPlushy);
            helper.register(UpgradedWolves.getId("creeper_plush"),creeperPlushy);
            }
        );
        
    }

}
