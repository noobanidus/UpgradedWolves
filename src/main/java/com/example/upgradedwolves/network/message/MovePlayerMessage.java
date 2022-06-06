package com.example.upgradedwolves.network.message;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class MovePlayerMessage implements IMessage<MovePlayerMessage> {
    UUID playerId;
    double d0;
    double d1;
    double d2;

    public MovePlayerMessage(){
        this.playerId = UUID.randomUUID();
        this.d0 = 0;
        this.d1 = 0;
        this.d2 = 0;
    }

    public MovePlayerMessage(UUID playerId, double d0, double d1, double d2){
        this.playerId = playerId;
        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
    }

    @Override
    public MovePlayerMessage encode(MovePlayerMessage message, FriendlyByteBuf buffer) {
        buffer.writeUUID(message.playerId);
        buffer.writeDouble(message.d0);
        buffer.writeDouble(message.d1);
        buffer.writeDouble(message.d2);
        return message;   
    }

    @Override
    public MovePlayerMessage decode(FriendlyByteBuf buffer) {
        
        return new MovePlayerMessage(buffer.readUUID(), buffer.readDouble(),buffer.readDouble(),buffer.readDouble());
    }

    @Override
    public MovePlayerMessage handle(MovePlayerMessage message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Player playerIn = (Player)mc.level.getPlayerByUUID(message.playerId);
            playerIn.setDeltaMovement(playerIn.getDeltaMovement().add(Math.copySign(message.d0 * message.d0 * 0.5D, -message.d0), Math.copySign(message.d1 * message.d1 * 0.5D, -message.d1), Math.copySign(message.d2 * message.d2 * 0.5D, -message.d2)));
        });
        supplier.get().setPacketHandled(true);
        return message;
    }
    
}
