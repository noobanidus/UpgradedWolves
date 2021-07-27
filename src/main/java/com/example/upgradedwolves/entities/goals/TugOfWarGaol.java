package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;
import com.example.upgradedwolves.items.MobPlushy;
import com.example.upgradedwolves.network.message.RenderMessage;
import com.example.upgradedwolves.network.PacketHandler;

import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.PacketDistributor;

public class TugOfWarGaol extends Goal {
    protected int timeActive;
    protected WolfEntity wolf;
    protected PlayerEntity playerIn;

    public TugOfWarGaol(WolfEntity wolfEntity){
        this.wolf = wolfEntity;
        this.timeActive = 0;
    }

    @Override
    public boolean shouldExecute() {
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        if(handler.getRopeHolder() != null){
            playerIn = (PlayerEntity)handler.getRopeHolder();
            setWolfPath();
            return true;
        }
        return false;
    }

    public boolean shouldContinueExecuting(){
        double distance = wolf.getPosition().distanceSq(playerIn.getPosition());
        if(timeActive++ < 20*15 && distance < 64){
            return true;
        } else {
            wolf.entityDropItem(new ItemStack(WolfToysHandler.TUFOFWARROPE));
            playerIn = null;
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            handler.clearRopeHolder();
            PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new RenderMessage( wolf.getEntityId(),0,false) );
            return false;
        }
    }

    @Override
    public void tick() {        
        super.tick();
        if(wolf.getNavigator().noPath()){
            setWolfPath();
        }
        
    }

    private void setWolfPath(){
        Vector3d position;
        for(int i = 0; i < 10; i++){
            position = RandomPositionGenerator.findRandomTargetBlockAwayFrom(wolf,10,3,playerIn.getPositionVec());
            if(position != null){
                Path path = wolf.getNavigator().getPathToPos(position.x,position. y, position.z, 0);
                wolf.getNavigator().setPath(path, .5);
                break;
            }
        }        
    }

}
