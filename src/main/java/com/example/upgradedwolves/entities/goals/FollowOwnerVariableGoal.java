package com.example.upgradedwolves.entities.goals;

import com.example.upgradedwolves.common.WolfPlayerInteraction;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FollowOwnerVariableGoal extends FollowOwnerGoal{
    TameableEntity tameable;
    float dist;
    PathNavigator navigator;

    public FollowOwnerVariableGoal(TameableEntity tameable, double speed, float minDist, float maxDist,
            boolean teleportToLeaves) {
        super(tameable, speed, minDist, maxDist, teleportToLeaves);
        this.tameable = tameable;
        this.dist = minDist;
        this.navigator = tameable.getNavigator();
    }

    public void setMinDistance(float dist){
        this.dist = dist;
    }

    public boolean shouldExecute() {
        if(tameable.getOwner() == null)
            return false;
        else if(this.tameable.getDistanceSq(tameable.getOwner()) < (double)(this.dist * this.dist))
            return false;
        else if(WolfPlayerInteraction.getWolfGoal((WolfEntity)tameable,WolfFindAndPickUpItemGoal.class) != null)
            return false;
        else
            return super.shouldExecute();
    }

    private int getRandomNumber(int min, int max) {
        return this.tameable.getRNG().nextInt(max - min + 1) + min;
    }

    void teleportToEntity(){
        BlockPos blockpos = this.tameable.getOwner().getPosition();

        for(int i = 0; i < 10; ++i) {
            int j = this.getRandomNumber(-3, 3);
            int k = this.getRandomNumber(-1, 1);
            int l = this.getRandomNumber(-3, 3);
            boolean flag = this.tryToTeleportToLocation(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }
    }

    private boolean tryToTeleportToLocation(int x, int y, int z) {
        LivingEntity owner = tameable.getOwner();
        if (Math.abs((double)x - owner.getPosX()) < 2.0D && Math.abs((double)z - owner.getPosZ()) < 2.0D) {
           return false;
        } else if (!this.isTeleportFriendlyBlock(new BlockPos(x, y, z))) {
           return false;
        } else {
           this.tameable.setLocationAndAngles((double)x + 0.5D, (double)y, (double)z + 0.5D, this.tameable.rotationYaw, this.tameable.rotationPitch);
           this.navigator.clearPath();
           return true;
        }
    }
    private boolean isTeleportFriendlyBlock(BlockPos pos) {
        World world = tameable.world;
        PathNodeType pathnodetype = WalkNodeProcessor.func_237231_a_(world, pos.toMutable());
        if (pathnodetype != PathNodeType.WALKABLE) {
           return false;
        } else {
           BlockState blockstate = world.getBlockState(pos.down());
           if (blockstate.getBlock() instanceof LeavesBlock) {
              return false;
           } else {
              BlockPos blockpos = pos.subtract(this.tameable.getPosition());
              return world.hasNoCollisions(this.tameable, this.tameable.getBoundingBox().offset(blockpos));
           }
        }
     }

    @Override
    public void tick() {
        LivingEntity owner = tameable.getOwner();
        this.tameable.getLookController().setLookPositionWithEntity(owner, 10.0F, (float)this.tameable.getVerticalFaceSpeed());
            
         if (!this.tameable.getLeashed() && !tameable.isPassenger()) {
            if (this.tameable.getDistanceSq(owner) >= 900.0D * 25 || this.navigator.getPathToEntity(owner, 1) == null) {
               this.teleportToEntity();
            } else {
               this.navigator.tryMoveToEntityLiving(owner, 1);
            }
        }
    }
}


