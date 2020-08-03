package com.example.upgradedwolves.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.example.upgradedwolves.UpgradedWolves;

import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TrainingHandler {
    @CapabilityInject(ITraining.class)
    public static final Capability<ITraining> CAPABILITY_TRAINING = null;

    public interface ITraining{
        public void setAttribute(int value);        
        public int getAndResetAttribute();
    }
    public static void register() {
        CapabilityManager.INSTANCE.register(ITraining.class, new Storage(), Training::new);
        MinecraftForge.EVENT_BUS.register(new TrainingHandler());
    }
    @Nullable
    public static ITraining getHandler(ItemStack item)
    {
        return item.getCapability(CAPABILITY_TRAINING, Direction.DOWN).orElse(null);
    }
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = (ItemStack)event.getObject();
        if(stack.isFood()){
            Food next = stack.getItem().getFood();
            if(next.isMeat()){
                event.addCapability(UpgradedWolves.getId("capability_training"), new Provider());
            }
        }
        
    }

    public static class Storage implements Capability.IStorage<ITraining> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<ITraining> capability, ITraining instance, Direction side) {
            return IntNBT.valueOf(instance.getAndResetAttribute());
        }

        @Override
        public void readNBT(Capability<ITraining> capability, ITraining instance, Direction side, INBT nbt) {
            IntNBT next = (IntNBT)nbt;
            instance.setAttribute(next.getInt());
        }

    }
    public static class Provider implements ICapabilitySerializable<IntNBT>
    {
        final ITraining INSTANCE = CAPABILITY_TRAINING.getDefaultInstance();

        @Override
        public IntNBT serializeNBT()
        {
            return (IntNBT) CAPABILITY_TRAINING.getStorage().writeNBT(CAPABILITY_TRAINING, INSTANCE, null);
        }

        @Override
        public void deserializeNBT(IntNBT compound)
        {
            CAPABILITY_TRAINING.getStorage().readNBT(CAPABILITY_TRAINING, INSTANCE, null, compound);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            return CAPABILITY_TRAINING.orEmpty(cap, LazyOptional.of(() -> INSTANCE));
        }
    }
    public static class Training implements ITraining{
        int currentAttribute;

        @Override
        public void setAttribute(int value) {
            currentAttribute = 0;

        }

        @Override
        public int getAndResetAttribute() {
            int retint = currentAttribute;
            currentAttribute = 0;
            return retint;
        }

    }
}