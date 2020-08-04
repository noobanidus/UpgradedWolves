package com.example.upgradedwolves.network;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.network.message.IMessage;
import com.example.upgradedwolves.network.message.ItemPacket;
import com.example.upgradedwolves.network.message.MessageRender;

import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler
{
    public static final String PROTOCOL_VERSION = "1";

    public static SimpleChannel instance;
    private static int nextId = 0;

    public static void register()
    {
        instance = NetworkRegistry.ChannelBuilder
                .named(UpgradedWolves.getId("network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();

        register(MessageRender.class,new MessageRender());
        //register(ItemPacket.class,new ItemPacket(0,0));
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message)
    {
        instance.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }
}