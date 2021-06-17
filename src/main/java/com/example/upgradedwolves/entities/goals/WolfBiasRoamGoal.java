package com.example.upgradedwolves.entities.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.math.vector.Vector3d;

public class WolfBiasRoamGoal extends RandomWalkingGoal {
    Vector3d bias;
    double maxDistance;
    double minDistance;

    public WolfBiasRoamGoal(CreatureEntity creature, double speedIn,double maxDistance, double minDistance) {
        super(creature, speedIn);
        bias = creature.getPositionVec();
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
    }

    protected Vector3d getPosition(){
        if (this.creature.isInWaterOrBubbleColumn()) {
            Vector3d vector3d = RandomPositionGenerator.getLandPos(this.creature, 15, 7);
            return vector3d == null ? super.getPosition() : vector3d;
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
