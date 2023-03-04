package com.example.upgradedwolves.itemHandler;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.items.FlyingDisk;
import com.example.upgradedwolves.items.MobPlushy;
import com.example.upgradedwolves.items.TennisBall;
import com.example.upgradedwolves.items.TugOfWarRopeItem;
import com.example.upgradedwolves.items.GoldenBone.EnchantedGoldenBone;
import com.example.upgradedwolves.items.GoldenBone.GoldenBone;
import com.example.upgradedwolves.utils.MobPlushyType;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;


public class WolfToysHandler {

    public static TennisBall TENNIS_BALL = new TennisBall();
    public static FlyingDisk FLYING_DISK = new FlyingDisk();
    public static GoldenBone GOLDEN_BONE = new GoldenBone();
    public static EnchantedGoldenBone ENCHANTED_GOLDEN_BONE = new EnchantedGoldenBone();
    public static TugOfWarRopeItem TUF_OF_WAR_ROPE = new TugOfWarRopeItem();
    public static MobPlushy ZOMBIE_PLUSHY = new MobPlushy(MobPlushyType.ZOMBIE);
    public static MobPlushy SKELETON_PLUSHY = new MobPlushy(MobPlushyType.SKELETON);
    public static MobPlushy CREEPER_PLUSHY = new MobPlushy(MobPlushyType.CREEPER);

    @SubscribeEvent
    public static void registerItems(final RegisterEvent event){
        event.register(ForgeRegistries.Keys.ITEMS,helper ->{
            helper.register(UpgradedWolves.getId("tennis_ball"),TENNIS_BALL);
            helper.register(UpgradedWolves.getId("golden_bone"),GOLDEN_BONE);
            helper.register(UpgradedWolves.getId("enchanted_golden_bone"),ENCHANTED_GOLDEN_BONE);
            helper.register(UpgradedWolves.getId("flying_disk"),FLYING_DISK);
            helper.register(UpgradedWolves.getId("tug_of_war_rope"),TUF_OF_WAR_ROPE);

            helper.register(UpgradedWolves.getId("zombie_plush"),ZOMBIE_PLUSHY);
            helper.register(UpgradedWolves.getId("skeleton_plush"),SKELETON_PLUSHY);
            helper.register(UpgradedWolves.getId("creeper_plush"),CREEPER_PLUSHY);
            }
        );
        
    }

    // Registered on the MOD event bus
    // Assume we have RegistryObject<Item> and RegistryObject<Block> called ITEM and BLOCK
    @SubscribeEvent
    public static void buildContents(CreativeModeTabEvent.BuildContents event) {
    // Add to ingredients tab
        if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            //event.accept(ITEM);
            event.accept(TENNIS_BALL);
            event.accept(FLYING_DISK);
            event.accept(GOLDEN_BONE);
            event.accept(ENCHANTED_GOLDEN_BONE);
            event.accept(TUF_OF_WAR_ROPE);
            event.accept(ZOMBIE_PLUSHY);
            event.accept(SKELETON_PLUSHY);
            event.accept(CREEPER_PLUSHY);
        }
    }

}
