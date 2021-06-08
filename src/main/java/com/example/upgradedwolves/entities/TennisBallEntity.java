package com.example.upgradedwolves.entities;

import com.example.upgradedwolves.init.ModEntities;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class TennisBallEntity extends ProjectileItemEntity {

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
            this.setMotion(
                blockResult.getFace().getAxis() == Direction.Axis.X ? -vector3d1.x * .5 : vector3d1.x,
                blockResult.getFace().getAxis() == Direction.Axis.Y ? -vector3d1.y * .5 : vector3d1.y,
                blockResult.getFace().getAxis() == Direction.Axis.Z ? -vector3d1.z * .5 : vector3d1.z
                );
        }
        if(result.getType() == RayTraceResult.Type.ENTITY){
            this.remove();
        }

    }
    
    public void tick() {
        super.tick();
        timeOut++;
        if(timeOut >= 1200){
            this.remove();
        }
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {        
        if (!this.world.isRemote) {
            boolean flag = this.func_234616_v_().getUniqueID() == entityIn.getUniqueID();
            if (!entityIn.inventory.addItemStackToInventory(new ItemStack(getDefaultItem()))) {
                flag = false;
            }
    
            if (flag) {
                entityIn.onItemPickup(this, 1);
                this.remove();
            }
    
        }
    }

}
