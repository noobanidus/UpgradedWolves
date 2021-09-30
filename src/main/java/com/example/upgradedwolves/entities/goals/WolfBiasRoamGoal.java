package com.example.upgradedwolves.entities.goals;

import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

import com.mojang.math.Vector3d;

public class WolfBiasRoamGoal extends RandomStrollGoal {
    Vector3d bias;
    double maxDistance;
    double minDistance;

    public WolfBiasRoamGoal(PathfinderMob creature, double speedIn,double maxDistance, double minDistance) {
        super(creature, speedIn);    
        bias = creature.getPositionVec();
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
    }

    protected Vector3d getPosition(1){
        if (this.creature.isInWaterOrBubbleColumn()) {
            Vector3d vector3d = RandomPositionGenerator.getLandPos(this.creature, 15, 7);
            return vector3d == null ? super.getPosition(1) : vector3d;
        }
        return biasedPosition();
    }

    protected Vector3d biasedPosition(){
        Vector3d position = creature.getPositionVec();
        double distance = position.distanceTo(bias);

        if(distance < minDistance){
            return RandomPositionGenerator.getLandPos(this.creature, 10, 7);
        } else if(distance > maxDistance){
            return bias;
        } else {            
            Vector3d nextPosition = RandomPositionGenerator.func_234133_a_(this.creature,
             10, 7,
              bias
                );
            return nextPosition;            
        }
    }
}
