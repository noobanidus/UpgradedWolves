package com.example.upgradedwolves.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.itemHandler.ItemStackHandlerWolf;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.SpawnLevelUpParticle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.ItemStackHandler;

public class WolfStatsHandler {
    @CapabilityInject(IWolfStats.class)
    public static final Capability<IWolfStats> CAPABILITY_WOLF_STATS = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IWolfStats.class, new Storage(), WolfStats::new);
        MinecraftForge.EVENT_BUS.register(new WolfStatsHandler());
    }

    @Nullable
    public static IWolfStats getHandler(WolfEntity entity) {
        IWolfStats stats = entity.getCapability(CAPABILITY_WOLF_STATS, Direction.DOWN).orElse(null);
        stats.setActiveWolf(entity);
        return stats;
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof WolfEntity) {
            event.addCapability(UpgradedWolves.getId("wolf_stats"), new Provider());            
        }
    }
    
    public static class WolfStats implements IWolfStats {

        int speedLvl, strengthLvl, intelligenceLvl, loveLvl, wolfType;
        int speedXp, strengthXp, intelligenceXp;
        //The wolves will have a maximum of 27 slots. (3, +1 every 10 str)         
        ItemStackHandlerWolf inventory;
        WolfEntity currentWolf;
        
        private boolean LevelUpFunction(int level, int xp) {
            return xp > Math.pow(level,1.1) * 4;
        }
        public void InitLove(){
            if(loveLvl > 0)
                return;
            //Skewed Random function
            loveLvl = (int)(Math.pow(Math.random() * 1000,2) / 10000);
            if(loveLvl < 10)
            loveLvl = 10;
        }
        @Override
        public void addXp(WolfStatsEnum wolfStats, int amount) {

            switch (wolfStats) {
                case Strength:
                    if (LevelUpFunction(strengthLvl,strengthXp + amount)){                        
                        strengthXp -= Math.pow(strengthLvl++,1.1) * 4;
                        PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> currentWolf), new SpawnLevelUpParticle( currentWolf.getEntityId(),wolfStats.ordinal()));
                    }
                    strengthXp += amount;
                    break;
                case Speed:
                    if (LevelUpFunction(speedLvl,speedXp + amount)){                        
                        speedXp -= Math.pow(speedLvl++,1.1) * 4;
                        PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> currentWolf), new SpawnLevelUpParticle( currentWolf.getEntityId(),wolfStats.ordinal()));
                    }
                    speedXp += amount;
                    break;
                case Intelligence:
                    if (LevelUpFunction(intelligenceLvl,intelligenceXp + amount)){                        
                        intelligenceXp -= Math.pow(intelligenceLvl++,1.1) * 4;
                        PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> currentWolf), new SpawnLevelUpParticle( currentWolf.getEntityId(),wolfStats.ordinal()));
                    }
                    intelligenceXp += amount;
                    break;
                case Love:
                    break;
            }

        }

        @Override
        public int getLevel(WolfStatsEnum wolfStats) {
            switch (wolfStats) {
                case Speed:
                    return speedLvl;
                case Strength:
                    return strengthLvl;
                case Intelligence:
                    return intelligenceLvl;
                case Love:
                    return loveLvl;
                default:
                    return 0;
            }
        }

        @Override
        public void setLevel(WolfStatsEnum wolfStats, int amount) {
            switch (wolfStats) {
                case Speed:
                    speedLvl = amount;
                    break;
                case Strength:
                    strengthLvl = amount;
                    break;
                case Intelligence:
                    intelligenceLvl = amount;
                    break;
                case Love:
                    loveLvl = amount;
                    break;
            }

        }

        @Override
        public int getWolfType() {            
            return wolfType;
        }

        @Override
        public void setWolfType(int type) {
            wolfType = type;

        }

        @Override
        public int getXp(WolfStatsEnum wolfStats) {
            switch (wolfStats) {
                case Speed:
                    return speedXp;
                case Strength:
                    return strengthXp;
                case Intelligence:
                    return intelligenceXp;
                case Love:
                    return -4;
                default:
                    return 0;
            }            
        }

        @Override
        public double getWolfSpeed() {
            //Wolf Generic Movement Speed is 0.3D
            return 0.3D + (speedLvl * .01);
        }

        @Override
        public int getWolfStrength() {
            //Base wolf Damage is 4
            return 4 + (strengthLvl / 2);
        }

        @Override
        public ItemStackHandlerWolf getInventory() {            
            return inventory;
        }
        @Override
        public boolean addItemStack(ItemStack item){
            int i = 0;
            while(item.getCount() > 0){
                item = inventory.insertItem(i, item, false);
                if(i == 27)
                    return false;
                i++;
            }
            return true;
        }
        @Override
        public WolfEntity getActiveWolf() {
            return currentWolf;
        }
        @Override
        public void setActiveWolf(WolfEntity entity) {
            if(currentWolf == null)
                currentWolf = entity;
        }
        

    }

    public static class Storage implements Capability.IStorage<IWolfStats> {

        @Override
        public INBT writeNBT(Capability<IWolfStats> capability, IWolfStats instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            instance.InitLove();          
            nbt.putInt("SpeedLevel", instance.getLevel(WolfStatsEnum.Speed));
            nbt.putInt("StrengthLevel", instance.getLevel(WolfStatsEnum.Strength));
            nbt.putInt("IntelligenceLevel", instance.getLevel(WolfStatsEnum.Intelligence));
            nbt.putInt("LoveLevel", instance.getLevel(WolfStatsEnum.Love));
            nbt.putInt("SpeedXp", instance.getXp(WolfStatsEnum.Speed));
            nbt.putInt("StrengthXp", instance.getXp(WolfStatsEnum.Strength));
            nbt.putInt("IntelligenceXp", instance.getXp(WolfStatsEnum.Intelligence));
            nbt.putInt("WolfType",instance.getWolfType());
            //nbt.put("Inventory",instance.getInventory().serializeNBT());
            return nbt;
        }

        @Override
        public void readNBT(Capability<IWolfStats> capability, IWolfStats instance, Direction side, INBT nbt) {            
            CompoundNBT next = (CompoundNBT)nbt;            
            instance.setLevel(WolfStatsEnum.Speed, next.getInt("SpeedLevel"));
            instance.setLevel(WolfStatsEnum.Strength, next.getInt("StrengthLevel"));
            instance.setLevel(WolfStatsEnum.Intelligence, next.getInt("IntelligenceLevel"));
            instance.setLevel(WolfStatsEnum.Love, next.getInt("LoveLevel"));
            instance.addXp(WolfStatsEnum.Speed, next.getInt("SpeedXp"));
            instance.addXp(WolfStatsEnum.Strength, next.getInt("StrengthXp"));
            instance.addXp(WolfStatsEnum.Intelligence, next.getInt("IntelligenceXp"));
            instance.setWolfType(next.getInt("WolfType"));
            //instance.getInventory().deserializeNBT(next.getCompound("Inventory"));
            instance.InitLove();
        }

    }
    public static class Provider implements ICapabilitySerializable<CompoundNBT>
    {
        final IWolfStats INSTANCE = CAPABILITY_WOLF_STATS.getDefaultInstance();

        @Override
        public CompoundNBT serializeNBT()
        {
            return (CompoundNBT) CAPABILITY_WOLF_STATS.getStorage().writeNBT(CAPABILITY_WOLF_STATS, INSTANCE, null);
        }

        @Override
        public void deserializeNBT(CompoundNBT compound)
        {
            CAPABILITY_WOLF_STATS.getStorage().readNBT(CAPABILITY_WOLF_STATS, INSTANCE, null, compound);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            return CAPABILITY_WOLF_STATS.orEmpty(cap, LazyOptional.of(() -> INSTANCE));
        }
    }
}
