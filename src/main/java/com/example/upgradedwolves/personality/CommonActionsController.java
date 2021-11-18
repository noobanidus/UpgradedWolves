package com.example.upgradedwolves.personality;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.phys.Vec3;

public class CommonActionsController {
    public final Wolf wolf;

    public CommonActionsController(Wolf wolf){
        this.wolf = wolf;
    }

    public void bark(){
        wolf.playAmbientSound();
    }

    public void growl(){
        wolf.playSound(SoundEvents.WOLF_GROWL, 1, 1);
    }

    public void jump(){
        if(wolf.isOnGround()){
            wolf.getJumpControl().jump();
        }
    }

    public void whine(){
        wolf.playSound(SoundEvents.WOLF_WHINE, 1, .85f + (wolf.getRandom().nextFloat() * .3f));
    }

    public void jumpTowards(LivingEntity entity){
        if(wolf.isOnGround()){
            Vec3 vec3 = wolf.getDeltaMovement();
            Vec3 vec31 = new Vec3(entity.getX() - this.wolf.getX(), 0.0D, entity.getZ() - this.wolf.getZ());
            if (vec31.lengthSqr() > 1.0E-7D) {
                vec31 = vec31.normalize().scale(0.4D).add(vec3.scale(0.2D));
            }

            this.wolf.setDeltaMovement(vec31.x, 0.4D, vec31.z);
        }
    }

    public void jumpTowards(Vec3 position){
        if(wolf.isOnGround()){
            Vec3 vec3 = wolf.getDeltaMovement();
            Vec3 vec31 = new Vec3(position.x - this.wolf.getX(), 0.0D, position.z - this.wolf.getZ());
            if (vec31.lengthSqr() > 1.0E-7D) {
                vec31 = vec31.normalize().scale(0.4D).add(vec3.scale(0.2D));
            }

            this.wolf.setDeltaMovement(vec31.x, 0.4D, vec31.z);
        }
    }

    public Vec3 getLeft(){
        Vec3 angle = wolf.getLookAngle();
        return new Vec3(-angle.z,0,angle.x).add(wolf.getPosition(0));
    }

    public Vec3 getRight(){
        Vec3 angle = wolf.getLookAngle();
        return new Vec3(angle.z,0,angle.x).add(wolf.getPosition(0));
    }

    public void jumpLateral(){
        Vec3 angle;
        if(wolf.getRandom().nextBoolean()){
            angle = getLeft();
        } else {
            angle = getRight();
        }
        jumpTowards(angle);
    }
}
