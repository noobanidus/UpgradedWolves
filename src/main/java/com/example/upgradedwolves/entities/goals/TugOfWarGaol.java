package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;
import com.example.upgradedwolves.network.message.MovePlayerMessage;
import com.example.upgradedwolves.network.message.RenderMessage;

import com.example.upgradedwolves.network.PacketHandler;

import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.pathfinding.Path;
import com.mojang.math.Vector3d;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;

public class TugOfWarGaol extends Goal {
    protected int timeActive;
    protected Wolf wolf;
    protected Player playerIn;

    public TugOfWarGaol(Wolf Wolf){
        this.wolf = Wolf;
        this.timeActive = 0;
    }

    @Override
    public boolean canUse() {
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        if(handler.getRopeHolder() != null){
            playerIn = (Player)handler.getRopeHolder();
            setWolfPath();
            timeActive = 0;
            return true;
        }
        return false;
    }

    public boolean shouldContinueExecuting(){
        double distance = wolf.getPosition(1).distanceSq(playerIn.getPosition(1));
        if(timeActive++ < 20*15 && distance < 64){
            return true;
        } else {
            wolf.spawnAtLocation(new ItemStack(WolfToysHandler.TUFOFWARROPE));
            playerIn = null;
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            handler.clearRopeHolder();
            handler.addXp(WolfStatsEnum.Strength, 2);
            PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> wolf), new RenderMessage( wolfgetId(),0,0,false) );
            return false;
        }
    }

    @Override
    public void tick() {        
        super.tick();
        if(wolf.getNavigation().noPath()){
            setWolfPath();
        }
        float distance = wolf.distanceTo(playerIn);
        if(distance > 4){
            double d0 = (playerIn.getX() - wolf.getX()) / (double)distance;
            double d1 = (playerIn.getY() - wolf.getY()) / (double)distance;
            double d2 = (playerIn.getZ() - wolf.getZ()) / (double)distance;
            wolf.setDeltaMovement(wolf.getDeltaMovement().add(Math.copySign(d0 * d0 * 0.5D, d0), Math.copySign(d1 * d1 * 0.5D, d1), Math.copySign(d2 * d2 * 0.5D, d2)));
            PacketHandler.instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)playerIn), new MovePlayerMessage(playerIn.getUniqueID(),d0,d1,d2));
        }
        
    }

    private void setWolfPath(){
        Vector3d position;
        for(int i = 0; i < 10; i++){
            position = RandomPositionGenerator.findRandomTargetBlockAwayFrom(wolf,10,3,playerIn.getPositionVec());
            if(position != null){
                Path path = wolf.getNavigation().getPathToPos(position.x,position. y, position.z, 0);
                wolf.getNavigation().setPath(path, .5);
                break;
            }
        }        
    }

}
