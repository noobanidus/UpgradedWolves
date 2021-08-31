package com.example.upgradedwolves.network.message;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SyncWolfHandMessage implements IMessage<SyncWolfHandMessage> {    
    int wolfId;
    ItemStack item;

    public SyncWolfHandMessage(){
        this.wolfId = 0;
        this.item = null;
    }

    public SyncWolfHandMessage(int wolfId,ItemStack item){
        this.wolfId = wolfId;
        this.item = item;
    }

    @Override
    public void encode(SyncWolfHandMessage message, PacketBuffer buffer) {
        buffer.writeInt(message.wolfId);
        buffer.writeItemStack(message.item);
    }

    @Override
    public SyncWolfHandMessage decode(PacketBuffer buffer) {
        int id = buffer.readInt();
        ItemStack item = buffer.readItemStack();
        return new SyncWolfHandMessage(id,item);
    }

    @Override
    public void handle(SyncWolfHandMessage message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            WolfEntity wolf = (WolfEntity)mc.world.getEntityByID(message.wolfId);            
            wolf.setHeldItem(Hand.MAIN_HAND, message.item);
        });
    }
}
