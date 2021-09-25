package com.example.upgradedwolves.network.message;

import java.util.function.Supplier;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class RenderMessage implements IMessage<RenderMessage> {
    int wolfId;
    int wolfValue;
    int wolfFur;
    //true render Wolf color; false render wolf leash
    boolean renderType;

    public RenderMessage(){
        wolfId = 0;
        wolfValue = 0;
        wolfFur = 0;
    }

    public RenderMessage(int id,int value,int fur){
        wolfId = id;
        wolfValue = value;
        wolfFur = fur;
        renderType = true;
    }

    public RenderMessage(int id, int value,int fur, boolean type){
        wolfId = id;
        wolfValue = value;
        wolfFur = fur;
        renderType = type;
    }

    @Override
    public void encode(RenderMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.wolfId);
        buffer.writeInt(message.wolfValue);
        buffer.writeInt(message.wolfFur);
        buffer.writeBoolean(message.renderType);
    }

    @Override
    public RenderMessage decode(FriendlyByteBuf buffer) {
        
        return new RenderMessage(buffer.readInt(),buffer.readInt(),buffer.readInt(),buffer.readBoolean());
    }

    @Override
    public void handle(RenderMessage message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Wolf wolf = (Wolf)mc.level.getEntityByID(message.wolfId);
            IWolfStats handler = WolfStatsHandler.getHandler(wolf);
            if(message.renderType){
                handler.setWolfType(message.wolfValue);
                handler.setWolffur(message.wolfFur);
            }
            else
                handler.clearRopeHolder();
        });
        supplier.get().setPacketHandled(true);

    }
    
}