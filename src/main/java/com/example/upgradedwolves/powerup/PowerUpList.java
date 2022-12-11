package com.example.upgradedwolves.powerup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class PowerUpList {

    public static final List<String> StrengthWolfDefault = Arrays.asList(
        "4:enhance_detect", 
        "5:auto_attack",
        "5:enhance_detect",
        "6:flee_health",
        "7:enhance_speed",
        "8:flee_creeper",
        "9:use_sword",
        "10:pick_up_item",
        "10:enhance_attack",
        "13:bark_stun",
        "14:enhance_detect",
        "14:disarm_enemy",
        "15:enhance_attack",
        "15:self_preservation",
        "18:arrow_intercept",
        "18:enhance_speed",
        "18:fish_catcher",
        "20:enhance_attack",
        "21:toss_arrow");
    public static final List<String> ScavengerWolfDefault = Arrays.asList(
        "2:flee_health",
        "2:pick_up_item",
        "4:enhance_detect",
        "5:enhance_speed",
        "6:enhance_attack",
        "7:flee_creeper",
        "8:detect_enemy",
        "10:enhance_speed",
        "11:self_preservation",
        "12:death_retrieval",
        "14:enhance_detect",
        "15:enhance_speed",
        "17:dig_for_item",
        "18:fish_catcher",
        "18:enhance_attack",
        "20:enhance_speed",
        "22:loot_bonus",
        "25:enhance_speed"
    );
    public static final List<String> ShowWolfDefault = Arrays.asList(
        "4:share_item",
        "5:enhance_speed",
        "6:enhance_detect",
        "6:enhance_attack",
        "7:fish_catcher",
        "10:pick_up_item",
        "11:use_potion",
        "12:enhance_detect",
        "15:self_preservation",
        "16:enhance_speed",
        "16:enhance_detect",
        "18:enhance_attack",
        "19:impress",
        "21:enhance_detect",
        "25:enhance_detect"
    );
    public static final List<String> notSetDefault = Arrays.asList(
        "5:flee_health",
        "10:flee_creeper"
    );

    public static BiMap<String,Class> PowerUpIdMap = HashBiMap.create();

    public static void RegisterPowerUps(){
        register("arrow_intercept",ArrowInterceptPowerUp.class);
        register("auto_attack",AutoAttackPowerUp.class);
        register("bark_stun",BarkStunPowerUp.class);
        register("detect_enemy",DetectEnemyPowerUp.class);
        register("dig_for_item", DigForItemPowerUp.class);
        register("disarm_enemy",DisarmEnemyPowerUp.class);
        register("enhance_attack",EnhanceAttackPowerUp.class);
        register("enhance_detect",EnhanceDetectionPowerUp.class);
        register("enhance_speed",EnhanceSpeedPowerUp.class);
        register("fish_catcher",FishForItemPowerUp.class);
        register("flee_creeper", FleeCreeperPowerUp.class);
        register("flee_health", FleeHealthPowerUp.class);
        register("impress",ImpressVillagerPowerUp.class);
        register("loot_bonus",LootBonusPowerUp.class);
        register("pick_up_item",PickUpItemPowerUp.class);
        register("death_retrieval",RetrieveOnDeathPowerUp.class);
        register("self_preservation",SelfPreservationPowerUp.class);
        register("share_item",ShareItemPowerUp.class);
        register("use_potion",ThrowPotionPowerUp.class);
        register("use_sword",UseSwordPowerUp.class);
        register("toss_arrow",WolfTossArrowPowerUp.class);
    }

    private static void register(String resourceName, Class clazz){
        PowerUpIdMap.put(resourceName,clazz);
    }
}
