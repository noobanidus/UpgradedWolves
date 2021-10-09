package com.example.upgradedwolves.network.message;

import java.util.function.Supplier;

import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;
import com.example.upgradedwolves.common.TrainingEventHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class TrainingItemMessage implements IMessage<TrainingItemMessage> {
    int wolfValue;
    int playerId;
    public TrainingItemMessage(){
        wolfValue = 0;
        playerId = 0;
    }
    public TrainingItemMessage(int val,int id){
        wolfValue = val;
        playerId = id;
    }
    @Override
    public void encode(TrainingItemMessage message, FriendlyByteBuf buffer) {                
        
        buffer.writeInt(message.wolfValue);        
        buffer.writeInt(message.playerId);
    }

    @Override
    public TrainingItemMessage decode(FriendlyByteBuf buffer) {    
        return new TrainingItemMessage(buffer.readInt(),buffer.readInt());
    }

    @Override
    public void handle(TrainingItemMessage message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = (LocalPlayer)mc.level.getEntity(message.playerId);
            ItemStack foodItem = TrainingEventHandler.getFoodStack(player);
            ITraining handler = TrainingHandler.getHandler(foodItem);
            handler.setAttribute(message.wolfValue);
        });
        supplier.get().setPacketHandled(true);

    }

}