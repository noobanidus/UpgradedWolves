package com.example.upgradedwolves.network;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.network.message.CreateParticleForMobMessage;
import com.example.upgradedwolves.network.message.IMessage;
import com.example.upgradedwolves.network.message.MovePlayerMessage;
import com.example.upgradedwolves.network.message.RenderMessage;
import com.example.upgradedwolves.network.message.SpawnLevelUpParticle;
import com.example.upgradedwolves.network.message.SyncWolfHandMessage;
import com.example.upgradedwolves.network.message.TrainingItemMessage;

import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

public class PacketHandler
{
    public static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = ChannelBuilder.named(UpgradedWolves.ModId + ":network")
    .networkProtocolVersion(1)
    .acceptedVersions((s, v) -> true)
    .simpleChannel();

    public static void register()
    {
        // INSTANCE = NetworkRegistry.ChannelBuilder
        //         .named(UpgradedWolves.getId("network"))
        //         .networkProtocolVersion(() -> PROTOCOL_VERSION)
        //         .clientAcceptedVersions(PROTOCOL_VERSION::equals)
        //         .serverAcceptedVersions(PROTOCOL_VERSION::equals)
        //         .simpleChannel();

        register(TrainingItemMessage.class,new TrainingItemMessage());
        register(RenderMessage.class, new RenderMessage());
        register(MovePlayerMessage.class, new MovePlayerMessage());
        register(SpawnLevelUpParticle.class, new SpawnLevelUpParticle());
        register(SyncWolfHandMessage.class, new SyncWolfHandMessage());
        register(CreateParticleForMobMessage.class, new CreateParticleForMobMessage());
        //register(ItemPacket.class,new ItemPacket(0,0));
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message)
    {
        INSTANCE.messageBuilder(clazz).encoder(message::encode).decoder(message::decode).consumerNetworkThread(message::handle).add();
    }
}