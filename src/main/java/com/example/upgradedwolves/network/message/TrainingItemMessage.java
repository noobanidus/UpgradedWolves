package com.example.upgradedwolves.network.message;

import java.util.function.Supplier;

import com.example.upgradedwolves.capabilities.TrainingHandler;
import com.example.upgradedwolves.capabilities.TrainingHandler.ITraining;
import com.example.upgradedwolves.common.TrainingEventHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;

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
    public TrainingItemMessage encode(TrainingItemMessage message, FriendlyByteBuf buffer) {                
        
        buffer.writeInt(message.wolfValue);        
        buffer.writeInt(message.playerId);
        return message;
    }

    @Override
    public TrainingItemMessage decode(FriendlyByteBuf buffer) {    
        return new TrainingItemMessage(buffer.readInt(),buffer.readInt());
    }

    @Override
    public TrainingItemMessage handle(TrainingItemMessage message, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            setItemAttribute(message);
        });
        context.setPacketHandled(true);
        return message;
    }

    @OnlyIn(Dist.CLIENT)
    private void setItemAttribute(TrainingItemMessage message){
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = (LocalPlayer)mc.level.getEntity(message.playerId);
        ItemStack foodItem = TrainingEventHandler.getFoodStack(player);
        ITraining handler = TrainingHandler.getHandler(foodItem);
        handler.setAttribute(message.wolfValue);
    }

}