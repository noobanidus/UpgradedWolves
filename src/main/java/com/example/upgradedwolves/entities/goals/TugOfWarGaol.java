package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;
import com.example.upgradedwolves.network.message.MovePlayerMessage;
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
import net.minecraft.entity.player.ServerPlayerEntity;

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
            timeActive = 0;
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
        float distance = wolf.getDistance(playerIn);
        if(distance > 4){
            double d0 = (playerIn.getPosX() - wolf.getPosX()) / (double)distance;
            double d1 = (playerIn.getPosY() - wolf.getPosY()) / (double)distance;
            double d2 = (playerIn.getPosZ() - wolf.getPosZ()) / (double)distance;
            wolf.setMotion(wolf.getMotion().add(Math.copySign(d0 * d0 * 0.5D, d0), Math.copySign(d1 * d1 * 0.5D, d1), Math.copySign(d2 * d2 * 0.5D, d2)));
            PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)playerIn), new MovePlayerMessage(playerIn.getUniqueID(),d0,d1,d2));
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
