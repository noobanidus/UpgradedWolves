package com.example.upgradedwolves.init;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.entities.FlyingDiskEntity;
import com.example.upgradedwolves.entities.TennisBallEntity;
import com.example.upgradedwolves.entities.TugOfWarRopeEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEntities {
    public static EntityType<TennisBallEntity> tennisBallEntityType;
    public static EntityType<FlyingDiskEntity> flyingDiskEntityType;
    public static EntityType<TugOfWarRopeEntity> tugOfWarKnot;


    @SubscribeEvent
    public static void onEntityTypeRegistration(RegistryEvent.Register<EntityType<?>> event){
        tennisBallEntityType = EntityType.Builder.<TennisBallEntity>create(TennisBallEntity::new, EntityClassification.MISC)
        .size(.25f, .25f)
        .build(UpgradedWolves.getId("tennis_ball_entity_type").toString());
        tennisBallEntityType.setRegistryName(UpgradedWolves.getId("tennis_ball_entity_type"));
        event.getRegistry().register(tennisBallEntityType);

        flyingDiskEntityType = EntityType.Builder.<FlyingDiskEntity>create(FlyingDiskEntity::new, EntityClassification.MISC)
        .size(.25f, .25f)
        .build(UpgradedWolves.getId("flying_disk_entity_type").toString());
        flyingDiskEntityType.setRegistryName(UpgradedWolves.getId("flying_disk_entity_type"));
        event.getRegistry().register(flyingDiskEntityType);

        tugOfWarKnot = EntityType.Builder.<TugOfWarRopeEntity>create(TugOfWarRopeEntity::new, EntityClassification.MISC)
        .size(0.5F, 0.5f)
        .trackingRange(10).func_233608_b_(Integer.MAX_VALUE)
        .setCustomClientFactory(TugOfWarRopeEntity::new).build(UpgradedWolves.getId("tug_of_war_knot").toString());
        event.getRegistry().register(tugOfWarKnot);
    }
}
