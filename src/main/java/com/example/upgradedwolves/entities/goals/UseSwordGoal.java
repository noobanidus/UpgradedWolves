package com.example.upgradedwolves.entities.goals;

import java.io.Serializable;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.itemHandler.WolfItemStackHandler;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.SyncWolfHandMessage;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

@ClientGoal
public class UseSwordGoal extends Goal implements Serializable {
    protected final Wolf wolf;
    protected ItemStack sword;

    
    public UseSwordGoal(Wolf wolf){
        this.wolf = wolf;
    }

    @Override
    public boolean canUse() {
        if(wolf.getAttackTarget() != null){
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            WolfItemStackHandler wolfInventory = handler.getInventory();
            int swordSlot = wolfInventory.getSword();
            if(swordSlot >= 0){
                sword = wolfInventory.extractItem(wolfInventory.getSword(), 1, false);
                wolf.setItemInHand(InteractionHand.MAIN_HAND, sword);
                PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new SyncWolfHandMessage(wolfgetId(),sword));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {        
        if(wolf.getAttackTarget() != null && super.shouldContinueExecuting()){
            PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new SyncWolfHandMessage(wolfgetId(),ItemStack.EMPTY));
            return false;
        }
        return true;
    }
    
}
