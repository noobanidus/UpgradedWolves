package com.example.upgradedwolves.network.message;

import java.util.function.Supplier;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class RenderMessage implements IMessage<RenderMessage> {
    int wolfId;
    int wolfValue;
    //true render Wolf color; false render wolf leash
    boolean renderType;

    public RenderMessage(){
        wolfId = 0;
        wolfValue = 0;
    }

    public RenderMessage(int id,int value){
        wolfId = id;
        wolfValue = value;
        renderType = true;
    }

    public RenderMessage(int id, int value,boolean type){
        wolfId = id;
        wolfValue = value;
        renderType = type;
    }

    @Override
    public void encode(RenderMessage message, PacketBuffer buffer) {
        buffer.writeInt(message.wolfId);
        buffer.writeInt(message.wolfValue);
        buffer.writeBoolean(message.renderType);
    }

    @Override
    public RenderMessage decode(PacketBuffer buffer) {
        
        return new RenderMessage(buffer.readInt(),buffer.readInt(),buffer.readBoolean());
    }

    @Override
    public void handle(RenderMessage message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            WolfEntity wolf = (WolfEntity)mc.world.getEntityByID(message.wolfId);
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            if(message.renderType)
                handler.setWolfType(message.wolfValue);
            else
                handler.clearRopeHolder();
        });
        supplier.get().setPacketHandled(true);

    }
    
}