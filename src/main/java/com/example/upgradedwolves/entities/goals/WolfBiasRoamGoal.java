package com.example.upgradedwolves.entities.goals;

import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.phys.Vec3;

import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class WolfBiasRoamGoal extends WaterAvoidingRandomStrollGoal {
    Vec3 bias;
    double maxDistance;
    double minDistance;

    public WolfBiasRoamGoal(PathfinderMob creature, double speedIn,double maxDistance, double minDistance) {
        super(creature, speedIn);    
        bias = creature.getPosition(1);
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
    }

    protected Vec3 getPosition(){
        if(mob instanceof Wolf){
            bias = WolfStatsHandler.getHandler((Wolf)mob).getRoamPoint();
        }
        if (this.mob.isInWaterOrBubble()) {
            Vec3 vector3d = LandRandomPos.getPos(this.mob, 15, 7);
            return vector3d == null ? super.getPosition() : vector3d;
        }
        return biasedPosition();
    }

    protected Vec3 biasedPosition(){
        Vec3 position = mob.getPosition(1);
        double distance = position.distanceTo(bias);

        if(distance < minDistance){
            return LandRandomPos.getPos(this.mob, 10, 7);
        } else if(distance > maxDistance){
            return bias;
        } else {            
            Vec3 nextPosition = LandRandomPos.getPosTowards(this.mob,
             10, 7,
              bias
                );
            return nextPosition;            
        }
    }
}
