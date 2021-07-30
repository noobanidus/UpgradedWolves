package com.example.upgradedwolves.entities;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.common.WolfPlayerInteraction;
import com.example.upgradedwolves.entities.goals.WolfFindAndPickUpItemGoal;
import com.example.upgradedwolves.init.ModEntities;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;

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
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class TennisBallEntity extends WolfChaseableEntity {

    public int timeOut = 0;

    public TennisBallEntity(EntityType<? extends TennisBallEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public TennisBallEntity(World worldIn, LivingEntity throwerIn) {
        super(ModEntities.tennisBallEntityType, throwerIn, worldIn);
    }

    public TennisBallEntity(World worldIn, double x, double y, double z) {
        super(ModEntities.tennisBallEntityType, x, y, z, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return WolfToysHandler.TENNISBALL;
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
            if(this.getMotion().length() > 0.2)
                this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), world.getBlockState(blockResult.getPos()).getBlock().getSoundType(null, null, null, null).getPlaceSound(), SoundCategory.BLOCKS, 0.5F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
            if(blockResult.getFace().getAxis() == Direction.Axis.Y && this.getMotion().length() < 0.1)
                super.OnHitBlock(blockResult);
            else
                this.setMotion(
                    blockResult.getFace().getAxis() == Direction.Axis.X ? -vector3d1.x * .7 : vector3d1.x * .9,
                    blockResult.getFace().getAxis() == Direction.Axis.Y ? -vector3d1.y * .7 : vector3d1.y * .9,
                    blockResult.getFace().getAxis() == Direction.Axis.Z ? -vector3d1.z * .7 : vector3d1.z * .9
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
    }

    public void wolfCollect(WolfEntity wolf){        
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        ItemStack tennisBallItem = new ItemStack(getDefaultItem());
        int wolfSlot = handler.getInventory().getAvailableSlot(tennisBallItem);

        if(wolfSlot >= 0){
            handler.getInventory().insertItem(wolfSlot, tennisBallItem, false);
            WolfFindAndPickUpItemGoal goal = (WolfFindAndPickUpItemGoal)WolfPlayerInteraction.getWolfGoal(wolf, WolfFindAndPickUpItemGoal.class);
            if(goal != null)
                goal.setEndPoint(wolf.getPositionVec());
            this.remove();
        }
    }
}
