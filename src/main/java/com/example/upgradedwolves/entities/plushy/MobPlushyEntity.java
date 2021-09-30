package com.example.upgradedwolves.entities.plushy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.example.upgradedwolves.common.TrainingEventHandler;
import com.example.upgradedwolves.init.ModEntities;
import com.example.upgradedwolves.itemHandler.WolfToysHandler;
import com.example.upgradedwolves.items.MobPlushy;

import net.minecraft.world.level.block.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import com.mojang.math.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraft.util.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;

public class MobPlushyEntity extends ThrowableProjectile {
    private static final DataParameter<ItemStack> ITEMSTACK_DATA = EntityDataManager.createKey(ThrowableItemProjectile.class, DataSerializers.ITEMSTACK);
    private boolean inGround;
    @Nullable
    private BlockState inBlockState;

    public MobPlushyEntity(EntityType<? extends MobPlushyEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);      
    }

    public MobPlushyEntity(World worldIn, LivingEntity throwerIn) {
        super(ModEntities.mobPlushyEntityType, throwerIn, worldIn);
    }

    public MobPlushyEntity(World worldIn, double x, double y, double z) {
        super(ModEntities.mobPlushyEntityType, x, y, z, worldIn);
    }    

    protected void onImpact(RayTraceResult result) {
        RayTraceResult.Type raytraceresult$type = result.getType();
        if(raytraceresult$type == RayTraceResult.Type.ENTITY){
            EntityRayTraceResult entityResult = (EntityRayTraceResult)result;
            if(entityResult.getEntity() instanceof Wolf){
                TrainingEventHandler.wolfCollectEntity(this, (Wolf)entityResult.getEntity(), getItem());
            }
        }
        if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockResult = (BlockRayTraceResult)result;
            if(blockResult.getFace().getAxis() == Direction.Axis.Y)
                this.OnHitBlock(blockResult);
            else{
                Vector3d vector3d1 = this.getDeltaMovement();
                this.setDeltaMovement(
                    blockResult.getFace().getAxis() == Direction.Axis.X ? -vector3d1.x * .3 : vector3d1.x * .5,
                    vector3d1.y * .5,
                    blockResult.getFace().getAxis() == Direction.Axis.Z ? -vector3d1.z * .3 : vector3d1.z * .5
                );
            }

        }
    }
    @Override
    public void onCollideWithPlayer(Player entityIn) {        
        if (!this.world.isRemote) {
            boolean flag = this.func_234616_v_().getUniqueID() == entityIn.getUniqueID() && ticksExisted > 20;
            if (flag) {                
                if(!entityIn.isCreative() && !entityIn.addItemStackToInventory(getItem()))
                    flag = false;
            }
    
            if (flag) {                
                entityIn.onItemPickup(this, 1);
                this.remove();
            }
    
        }
    }

    public void onCollideWithWolf(Wolf wolf){
        wolf.setItemInHand(InteractionHand.MAIN_HAND, getItem());
        this.remove(); 
    }

    protected List<MobPlushy> plushTypes(){
        ArrayList<MobPlushy> plushes = new ArrayList<MobPlushy>();        
        plushes.add(WolfToysHandler.creeperPlushy);
        plushes.add(WolfToysHandler.skeletonPlushy);
        plushes.add(WolfToysHandler.zombiePlushy);
        return plushes;
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.func_213882_k();
        return itemstack.isEmpty() ? null : itemstack;
    }

    protected ItemStack func_213882_k() {
        return this.getDataManager().get(ITEMSTACK_DATA);
    }

    @Override
    public IPacket<?> createSpawnPacket() {        
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        if(getItem() == null)
            this.remove();
        super.tick();
        BlockPos blockpos = this.getPosition(1);
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

        for(Wolf wolf : this.world.getEntitiesWithinAABB(Wolf.class, this.getBoundingBox())) {
            onCollideWithWolf(wolf);    
        }
        
        if (this.inGround) {
            if (this.inBlockState != blockstate && this.stillInGround()) {
               this.notInBlock();
            }        
        }
    }

    private boolean stillInGround(){
        return this.inGround && this.world.hasNoCollisions((new AxisAlignedBB(this.getPositionVec(), this.getPositionVec())).grow(0.06D));
    }

    private void notInBlock() {
        this.inGround = false;
        Vector3d vector3d = this.getDeltaMovement();
        this.setDeltaMovement(vector3d.mul((double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F)));
    }

    protected void OnHitBlock(BlockRayTraceResult p_230299_1_) {
        this.inBlockState = this.world.getBlockState(p_230299_1_.getPos());
        super.func_230299_a_(p_230299_1_);
        Vector3d vector3d = p_230299_1_.getHitVec().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale((double)0.05F);
        this.setRawPosition(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.inGround = true;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(ITEMSTACK_DATA, ItemStack.EMPTY); 
    }

    public void setItem(ItemStack stack) {
        if (plushTypes().contains(stack.getItem()) || stack.hasTag()) {
           this.getDataManager().set(ITEMSTACK_DATA, Util.make(stack.copy(), (p_213883_0_) -> {
              p_213883_0_.setCount(1);
           }));
        }
  
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return super.isInRangeToRender3d(x, y, z);
        //return true;
    }

    public void writeAdditional(CompoundTag compound) {
        super.writeAdditional(compound);
        ItemStack itemstack = this.func_213882_k();
        if (!itemstack.isEmpty()) {
           compound.put("Item", itemstack.write(new CompoundTag()));
        }
  
    }
  
    public void readAdditional(CompoundTag compound) {
        super.readAdditional(compound);
        ItemStack itemstack = ItemStack.read(compound.getCompound("Item"));
        this.setItem(itemstack);
    }
}
