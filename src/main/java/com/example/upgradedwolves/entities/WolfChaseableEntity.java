package com.example.upgradedwolves.entities;

import javax.annotation.Nullable;

import com.example.upgradedwolves.common.TrainingEventHandler;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.Level;

public abstract class WolfChaseableEntity extends ThrowableItemProjectile {
    
    public int timeOut = 0;
    protected float onHitScalar = 0.7f;
    private boolean inGround;
    @Nullable
    private BlockState inBlockState;

    public WolfChaseableEntity(EntityType<? extends WolfChaseableEntity> p_i50159_1_,LivingEntity entity, Level p_i50159_2_){
        super(p_i50159_1_, entity, p_i50159_2_);
    }

    public WolfChaseableEntity(EntityType<? extends WolfChaseableEntity> p_i50159_1_, Level p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public void tick() {
        super.tick();
        timeOut++;
        if(timeOut >= 1200){
            this.kill();
        }
        for(Wolf wolf : this.level().getEntitiesOfClass(Wolf.class, this.getBoundingBox())) {
            onCollideWithWolf(wolf);    
        }

        Vec3 blockpos = this.getPosition(1);
        
        BlockState blockstate = this.level().getBlockState(BlockPos.containing(blockpos));
        if (!blockstate.isAir()) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level(), BlockPos.containing(blockpos));
            if (!voxelshape.isEmpty()) {
                Vec3 vector3d1 = this.getPosition(1);

                for(AABB axisalignedbb : voxelshape.toAabbs()) {
                    if (axisalignedbb.expandTowards(blockpos).contains(vector3d1)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.inGround) {
            if (this.inBlockState != blockstate && this.stillInGround()) {
               this.notInBlock();
            }        
        }
    }

    public void onCollideWithWolf(Wolf wolf){
        if(!speedFactor(0.5))            
            TrainingEventHandler.wolfCollectEntity(this, wolf, getItem());
    }

    @Override
    public void playerTouch(Player entityIn) {        
        if (!this.level().isClientSide) {
            boolean flag = this.getOwner().getUUID() == entityIn.getUUID() && !speedFactor(1) && tickCount > 20;
            if (flag && !entityIn.addItem(getItem())) {
                flag = false;
            }
    
            if (flag) {                
                entityIn.take(this, 1);
                this.kill();
            }
    
        }
    }
    public boolean speedFactor(double factor){
        double speed = this.getDeltaMovement().length();
        if(speed > factor)
            return true;
        return false;
    }

    private boolean stillInGround(){
        return this.inGround && this.level().noCollision((new AABB(this.getPosition(1), this.getPosition(1))).inflate(0.06D));
    }

    private void notInBlock() {
        this.inGround = false;
        Vec3 vector3d = this.getDeltaMovement();
        this.setDeltaMovement(vector3d.multiply((double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F)));
    }

    protected void OnHitBlock(BlockHitResult p_230299_1_) {
        this.inBlockState = this.level().getBlockState(p_230299_1_.getBlockPos());
        super.onHitBlock(p_230299_1_);
        Vec3 vector3d = p_230299_1_.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vector3d);
        Vec3 vector3d1 = vector3d.normalize().scale((double)0.05F);
        this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.inGround = true;
    }

}
