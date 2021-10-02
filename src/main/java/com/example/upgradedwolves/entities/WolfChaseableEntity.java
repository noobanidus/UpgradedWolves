package com.example.upgradedwolves.entities;

import javax.annotation.Nullable;

import com.example.upgradedwolves.common.TrainingEventHandler;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.mojang.math.Vector3d;
import net.minecraft.world.level.Level;

public abstract class WolfChaseableEntity extends Projectile {
    
    public int timeOut = 0;
    protected float onHitScalar = 0.7f;
    private boolean inGround;
    @Nullable
    private BlockState inBlockState;

    public WolfChaseableEntity(EntityType<? extends WolfChaseableEntity> p_i50159_1_, Level p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public WolfChaseableEntity(EntityType<? extends WolfChaseableEntity> p_i50159_1_, LivingEntity throwerIn, Level worldIn) {
        super(p_i50159_1_, throwerIn, worldIn);
    }

    public WolfChaseableEntity(EntityType<? extends WolfChaseableEntity> p_i50159_1_, double x, double y, double z, Level worldIn) {
        super(p_i50159_1_, x, y, z, worldIn);
    }

    public void tick() {
        super.tick();
        timeOut++;
        if(timeOut >= 1200){
            this.kill();
        }
        for(Wolf wolf : this.level.getEntitiesWithinAABB(Wolf.class, this.getBoundingBox())) {
            onCollideWithWolf(wolf);    
        }

        Vec3 blockpos = this.getPosition(1);
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (!blockstate.isAir(this.level, blockpos)) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
            if (!voxelshape.isEmpty()) {
                Vector3d vector3d1 = this.getPosition(1);

                for(AABB axisalignedbb : voxelshape.toBoundingBoxList()) {
                    if (axisalignedbb.offset(blockpos).contains(vector3d1)) {
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
            TrainingEventHandler.wolfCollectEntity(this, wolf, new ItemStack(getDefaultItem()));
    }

    @Override
    public void playerTouch(Player entityIn) {        
        if (!this.level.isRemote) {
            boolean flag = this.getOwner().getUUID() == entityIn.getUUID() && !speedFactor(1) && tickCount > 20;
            if (flag && !entityIn.addItem(new ItemStack(getDefaultItem()))) {
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

    @Override
    protected Item getPickupItem() {
        return null;
    }

    private boolean stillInGround(){
        return this.inGround && this.level.hasNoCollisions((new AABB(this.getPosition(1), this.getPosition(1))).expandTowards(0.06D));
    }

    private void notInBlock() {
        this.inGround = false;
        Vector3d vector3d = this.getDeltaMovement();
        this.setDeltaMovement(vector3d.mul((double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F)));
    }

    protected void OnHitBlock(BlockHitResult p_230299_1_) {
        this.inBlockState = this.level.getBlockState(p_230299_1_.getPos());
        super.func_230299_a_(p_230299_1_);
        Vector3d vector3d = p_230299_1_.getHitVec().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale((double)0.05F);
        this.setRawPosition(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.inGround = true;
    }

}
