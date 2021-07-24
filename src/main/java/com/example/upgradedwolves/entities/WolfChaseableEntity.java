package com.example.upgradedwolves.entities;

import javax.annotation.Nullable;

import com.example.upgradedwolves.common.TrainingEventHandler;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class WolfChaseableEntity extends ProjectileItemEntity {
    
    public int timeOut = 0;
    protected float onHitScalar = 0.7f;
    private boolean inGround;
    @Nullable
    private BlockState inBlockState;

    public WolfChaseableEntity(EntityType<? extends WolfChaseableEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public WolfChaseableEntity(EntityType<? extends WolfChaseableEntity> p_i50159_1_, LivingEntity throwerIn, World worldIn) {
        super(p_i50159_1_, throwerIn, worldIn);
    }

    public WolfChaseableEntity(EntityType<? extends WolfChaseableEntity> p_i50159_1_, double x, double y, double z, World worldIn) {
        super(p_i50159_1_, x, y, z, worldIn);
    }

    public void tick() {
        super.tick();
        timeOut++;
        if(timeOut >= 1200){
            this.remove();
        }
        for(WolfEntity wolf : this.world.getEntitiesWithinAABB(WolfEntity.class, this.getBoundingBox())) {
            onCollideWithWolf(wolf);    
        }

        BlockPos blockpos = this.getPosition();
        BlockState blockstate = this.world.getBlockState(blockpos);
        if (!blockstate.isAir(this.world, blockpos)) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
            if (!voxelshape.isEmpty()) {
                Vector3d vector3d1 = this.getPositionVec();

                for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
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

    public void onCollideWithWolf(WolfEntity wolf){
        if(!speedFactor(0.5))            
            TrainingEventHandler.wolfCollectEntity(this, wolf, new ItemStack(getDefaultItem()));
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {        
        if (!this.world.isRemote) {
            boolean flag = this.func_234616_v_().getUniqueID() == entityIn.getUniqueID() && !speedFactor(1) && ticksExisted > 20;
            if (flag && !entityIn.addItemStackToInventory(new ItemStack(getDefaultItem()))) {
                flag = false;
            }
    
            if (flag) {                
                entityIn.onItemPickup(this, 1);
                this.remove();
            }
    
        }
    }
    public boolean speedFactor(double factor){
        double speed = this.getMotion().length();
        if(speed > factor)
            return true;
        return false;
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    private boolean stillInGround(){
        return this.inGround && this.world.hasNoCollisions((new AxisAlignedBB(this.getPositionVec(), this.getPositionVec())).grow(0.06D));
    }

    private void notInBlock() {
        this.inGround = false;
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.mul((double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F)));
    }

    protected void OnHitBlock(BlockRayTraceResult p_230299_1_) {
        this.inBlockState = this.world.getBlockState(p_230299_1_.getPos());
        super.func_230299_a_(p_230299_1_);
        Vector3d vector3d = p_230299_1_.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
        this.setMotion(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale((double)0.05F);
        this.setRawPosition(this.getPosX() - vector3d1.x, this.getPosY() - vector3d1.y, this.getPosZ() - vector3d1.z);
        this.inGround = true;
    }

}
