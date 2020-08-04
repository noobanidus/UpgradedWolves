package com.example.upgradedwolves.network.message;

import java.util.function.Supplier;

import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;
import com.example.upgradedwolves.common.TrainingTreatHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageRender implements IMessage<MessageRender> {
    int wolfValue;
    int playerId;
    public MessageRender(){
        wolfValue = 0;
        playerId = 0;
    }
    public MessageRender(int val,int id){
        wolfValue = val;
        playerId = id;
    }
    @Override
    public void encode(MessageRender message, PacketBuffer buffer) {                
        
        buffer.writeInt(message.wolfValue);        
        buffer.writeInt(message.playerId);
    }

    @Override
    public MessageRender decode(PacketBuffer buffer) {    
        return new MessageRender(buffer.readInt(),buffer.readInt());
    }

    @Override
    public void handle(MessageRender message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            ClientPlayerEntity player = (ClientPlayerEntity)mc.world.getEntityByID(message.playerId);
            ItemStack foodItem = TrainingTreatHandler.getFoodStack(player);
            ITraining handler = TrainingHandler.getHandler(foodItem);
            handler.setAttribute(message.wolfValue);
        });

    }

}