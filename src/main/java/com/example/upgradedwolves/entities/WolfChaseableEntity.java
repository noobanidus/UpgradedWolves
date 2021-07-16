package com.example.upgradedwolves.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class WolfChaseableEntity extends ProjectileItemEntity {
    
    public int timeOut = 0;
    protected float onHitScalar = 0.7f;

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
    }

    public void onCollideWithWolf(WolfEntity wolf){
        if(!speedFactor(0.5))
            wolfCollect(wolf);
    }

    public void wolfCollect(WolfEntity wolf){  

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
}
