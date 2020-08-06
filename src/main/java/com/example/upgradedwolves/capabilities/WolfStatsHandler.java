package com.example.upgradedwolves.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.example.upgradedwolves.UpgradedWolves;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
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

public class WolfStatsHandler {
    @CapabilityInject(IWolfStats.class)
    public static final Capability<IWolfStats> CAPABILITY_WOLF_STATS = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IWolfStats.class, new Storage(), WolfStats::new);
        MinecraftForge.EVENT_BUS.register(new WolfStatsHandler());
    }

    @Nullable
    public static IWolfStats getHandler(WolfEntity entity) {
        return entity.getCapability(CAPABILITY_WOLF_STATS, Direction.DOWN).orElse(null);
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
        
        private int LevelUpFunction(int level, int xp) {
            if (xp >= Math.pow(level, 1.1) * 4) {
                xp -= Math.pow(level, 1.1) * 4;
            }
            return xp;
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
            int xp;
            switch (wolfStats) {
                case Strength:
                    if (strengthXp > (xp = LevelUpFunction(strengthLvl, strengthXp + amount)))
                        strengthLvl++;
                    strengthXp = xp;
                    break;
                case Speed:
                    if (speedXp > (xp = LevelUpFunction(speedLvl, speedXp + amount)))
                        speedLvl++;
                    speedXp = xp;
                    break;
                case Intelligence:
                    if (intelligenceXp > (xp = LevelUpFunction(intelligenceLvl, intelligenceXp + amount)))
                        intelligenceLvl++;
                    intelligenceXp = xp;
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
