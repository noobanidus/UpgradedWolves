package com.example.upgradedwolves.network.message;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

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
    public void encode(MovePlayerMessage message, PacketBuffer buffer) {
        buffer.writeUniqueId(message.playerId);
        buffer.writeDouble(message.d0);
        buffer.writeDouble(message.d1);
        buffer.writeDouble(message.d2);        
    }

    @Override
    public MovePlayerMessage decode(PacketBuffer buffer) {
        
        return new MovePlayerMessage(buffer.readUniqueId(), buffer.readDouble(),buffer.readDouble(),buffer.readDouble());
    }

    @Override
    public void handle(MovePlayerMessage message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            PlayerEntity playerIn = (PlayerEntity)mc.world.getPlayerByUuid(message.playerId);
            playerIn.setMotion(playerIn.getMotion().add(Math.copySign(message.d0 * message.d0 * 0.5D, -message.d0), Math.copySign(message.d1 * message.d1 * 0.5D, -message.d1), Math.copySign(message.d2 * message.d2 * 0.5D, -message.d2)));
        });
        supplier.get().setPacketHandled(true);
        
    }
    
}
