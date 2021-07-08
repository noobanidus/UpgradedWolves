package com.example.upgradedwolves.entities;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.common.WolfPlayerInteraction;
import com.example.upgradedwolves.entities.goals.WolfFindAndPickUpItemGoal;
import com.example.upgradedwolves.init.ModEntities;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FlyingDiskEntity extends WolfChaseableEntity{

    public int timeOut = 0;
    protected int flightTime = 100;
    protected float variant;
    protected boolean fly = true;

    public FlyingDiskEntity(EntityType<? extends FlyingDiskEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public FlyingDiskEntity(World worldIn, LivingEntity throwerIn) {
        super(ModEntities.flyingDiskEntityType, throwerIn, worldIn);
    }

    public FlyingDiskEntity(World worldIn, double x, double y, double z) {
        super(ModEntities.flyingDiskEntityType, x, y, z, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return WolfToysHandler.FLYINGDISK;
    }

    @Override
    public IPacket<?> createSpawnPacket() {        
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if(result.getType() == RayTraceResult.Type.BLOCK){
            BlockRayTraceResult blockResult = (BlockRayTraceResult)result;
            Vector3d vector3d1 = this.getMotion();
            this.setMotion(
                blockResult.getFace().getAxis() == Direction.Axis.X ? -vector3d1.x * .2 : vector3d1.x * .3,
                blockResult.getFace().getAxis() == Direction.Axis.Y ? -vector3d1.y * .2 : vector3d1.y * .3,
                blockResult.getFace().getAxis() == Direction.Axis.Z ? -vector3d1.z * .2 : vector3d1.z * .3
                );
            RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
            if(raytraceresult.getType() != RayTraceResult.Type.MISS){
                onImpact(raytraceresult);
            }
        }
        if(result.getType() == RayTraceResult.Type.ENTITY){
            EntityRayTraceResult entityResult = (EntityRayTraceResult)result;
            if(speedFactor(1)){
                if(entityResult.getEntity() instanceof LivingEntity){
                    LivingEntity entity = (LivingEntity)entityResult.getEntity();
                    entity.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity)this.func_234616_v_()), 1);
                    double speed = this.getMotion().length() * .6;
                    Vector3d bounceDirection = new Vector3d(entity.getPositionVec().x - this.getPositionVec().x,
                                                                entity.getPositionVec().y - this.getPositionVec().y,
                                                                entity.getPositionVec().z - this.getPositionVec().z)
                                                                .normalize();
                    this.setMotion(bounceDirection.scale(speed));
                }
            }            
            else if(entityResult.getEntity() instanceof WolfEntity){
                wolfCollect((WolfEntity)entityResult.getEntity());
            }
        }

    }
    
    public void tick() {
        super.tick();
        PlayerEntity player = (PlayerEntity)func_234616_v_();
        if(fly && player != null){
            Vector3d retrieveDirection = new Vector3d(player.getPositionVec().x - this.getPositionVec().x,
                                                                    player.getPositionVec().y + 1.5 - this.getPositionVec().y,
                                                                    player.getPositionVec().z - this.getPositionVec().z)
                                                                    .normalize().scale(.01);
            this.addVelocity(retrieveDirection.x, retrieveDirection.y,retrieveDirection.z);
            if(timeOut >= flightTime * variant){
                fly = false;
                this.setNoGravity(false);
            }
        }
    }

    public void wolfCollect(WolfEntity wolf){        
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        ItemStack flyingDiskItem = new ItemStack(getDefaultItem());
        int wolfSlot = handler.getInventory().getAvailableSlot(flyingDiskItem);

        if(wolfSlot >= 0){
            handler.getInventory().insertItem(wolfSlot, flyingDiskItem, false);
            WolfFindAndPickUpItemGoal goal = (WolfFindAndPickUpItemGoal)WolfPlayerInteraction.getWolfGoal(wolf, WolfFindAndPickUpItemGoal.class);
            if(goal != null){
                goal.setEndPoint(wolf.getPositionVec());
            }
            this.remove();
        }
    }

    @Override
    protected float getGravityVelocity() {      
        return 0.03f;
    }
    @Override
    public void func_234612_a_(Entity p_234612_1_, float p_234612_2_, float p_234612_3_, float p_234612_4_,
            float p_234612_5_, float p_234612_6_) {  
        super.func_234612_a_(p_234612_1_, p_234612_2_, p_234612_3_, p_234612_4_, p_234612_5_, p_234612_6_);
        variant = (p_234612_5_ - .2f) * 2.5f;
    }
}