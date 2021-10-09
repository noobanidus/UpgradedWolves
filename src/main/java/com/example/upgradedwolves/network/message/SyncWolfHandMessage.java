package com.example.upgradedwolves.network.message;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

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
    public void encode(SyncWolfHandMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.wolfId);
        buffer.writeItemStack(message.item,true);
    }

    @Override
    public SyncWolfHandMessage decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();
        ItemStack item = buffer.readItem();
        return new SyncWolfHandMessage(id,item);
    }

    @Override
    public void handle(SyncWolfHandMessage message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Wolf wolf = (Wolf)mc.level.getEntity(message.wolfId);            
            wolf.setItemInHand(InteractionHand.MAIN_HAND, message.item);
        });
    }
}
