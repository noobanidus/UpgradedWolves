package com.example.upgradedwolves.entities;

import java.util.List;

import com.example.upgradedwolves.init.ModEntities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class TugOfWarRopeEntity extends LeashKnotEntity {

    public TugOfWarRopeEntity(EntityType<? extends LeashKnotEntity> entityType, World world) {
        super(entityType, world);
		
    }
    
    public TugOfWarRopeEntity(World worldIn, BlockPos hangingPositionIn) {
		super(worldIn, hangingPositionIn);
		this.setPosition((double)hangingPositionIn.getX() + 0.5D, (double)hangingPositionIn.getY() + 0.5D, (double)hangingPositionIn.getZ() + 0.5D);
		this.setBoundingBox(new AxisAlignedBB(this.getPosX() - 0.1875D, this.getPosY() - 0.25D + 0.125D, this.getPosZ() - 0.1875D, this.getPosX() + 0.1875D, this.getPosY() + 0.25D + 0.125D, this.getPosZ() + 0.1875D));
		this.forceSpawn = true;
	}

    public TugOfWarRopeEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
		this(worldIn, new BlockPos(spawnEntity.getPosX(), spawnEntity.getPosY(), spawnEntity.getPosZ()));
	}

    @Override
	public EntityType<?> getType() {
		return ModEntities.tugOfWarKnot;
	}

    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
		if (this.world.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			boolean flag = false;
			List<MobEntity> list = this.world.getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB(this.getPosX() - 7.0D, this.getPosY() - 7.0D, this.getPosZ() - 7.0D, this.getPosX() + 7.0D, this.getPosY() + 7.0D, this.getPosZ() + 7.0D));

			for(MobEntity mobentity : list) {
				if (mobentity.getLeashHolder() == player) {
					mobentity.setLeashHolder(this, true);
					flag = true;
				}
			}

			if (!flag) {
				this.remove();
				if (player.abilities.isCreativeMode) {
					for(MobEntity mobentity1 : list) {
						if (mobentity1.getLeashed() && mobentity1.getLeashHolder() == this) {
							mobentity1.clearLeashed(true, false);
						}
					}
				}
			}

			return ActionResultType.CONSUME;
		}
	}

    public static TugOfWarRopeEntity createCustomLeash(World world, BlockPos pos) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		for(TugOfWarRopeEntity CustomLeashKnotEntity : world.getEntitiesWithinAABB(TugOfWarRopeEntity.class, new AxisAlignedBB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D))) {
			if (CustomLeashKnotEntity.getHangingPosition().equals(pos)) {
				return CustomLeashKnotEntity;
			}
		}

		TugOfWarRopeEntity CustomLeashKnotEntity1 = new TugOfWarRopeEntity(world, pos);
		world.addEntity(CustomLeashKnotEntity1);
		CustomLeashKnotEntity1.playPlaceSound();
		return CustomLeashKnotEntity1;
	}

    @Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
