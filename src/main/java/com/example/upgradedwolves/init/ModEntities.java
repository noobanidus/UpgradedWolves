package com.example.upgradedwolves.init;

import org.apache.logging.log4j.LogManager;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.entities.FlyingDiskEntity;
import com.example.upgradedwolves.entities.TennisBallEntity;
import com.example.upgradedwolves.entities.plushy.MobPlushyEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public class ModEntities {
    public static EntityType<TennisBallEntity> tennisBallEntityType = EntityType.Builder.<TennisBallEntity>of(TennisBallEntity::new, MobCategory.MISC)
    .sized(.25f, .25f).clientTrackingRange(4).updateInterval(10)
    .build(UpgradedWolves.getId("tennis_ball_entity_type").toString());

    public static EntityType<FlyingDiskEntity> flyingDiskEntityType = EntityType.Builder.<FlyingDiskEntity>of(FlyingDiskEntity::new, MobCategory.MISC)
    .sized(.25f, .25f)
    .build(UpgradedWolves.getId("flying_disk_entity_type").toString());

    public static EntityType<MobPlushyEntity> mobPlushyEntityType = EntityType.Builder
    .<MobPlushyEntity>of(MobPlushyEntity::new,MobCategory.MISC)
    .sized(0.5F, 0.5F)
    .build(UpgradedWolves.getId("mob_plushy_entity_type").toString());



    @SubscribeEvent
    public static void onEntityTypeRegistration(final RegisterEvent event){
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> {
            helper.register(UpgradedWolves.getId("tennis_ball_entity_type"),tennisBallEntityType);
            helper.register(UpgradedWolves.getId("flying_disk_entity_type"),flyingDiskEntityType);
            helper.register(UpgradedWolves.getId("mob_plushy_entity_type"),mobPlushyEntityType);
            }
        );
    }
}
