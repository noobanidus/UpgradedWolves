package com.example.upgradedwolves.common;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DamageHandler {
    //When the entity is attacked by a wolf
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onEntityHurt(LivingHurtEvent event) {
        if(event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof WolfEntity){
            WolfEntity wolf = (WolfEntity)event.getSource().getTrueSource();
            event.setAmount(1000);
            //Wolf Speed
            wolf.getAttribute(Attributes.field_233821_d_).setBaseValue(0.7D);
            wolf.setCanPickUpLoot(true);
            LogManager.getLogger().info(wolf.getHeldItemMainhand());
            
            if(wolf.getHeldItemMainhand() != null){
                wolf.getOwner().entityDropItem(wolf.getHeldItemMainhand());
                wolf.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);                
            }
        }
    }
}