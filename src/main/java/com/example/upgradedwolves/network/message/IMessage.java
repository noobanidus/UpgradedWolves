package com.example.upgradedwolves.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public interface IMessage<T>
{
    T encode(T message, FriendlyByteBuf buffer);

    T decode(FriendlyByteBuf buffer);

    T handle(T message, Supplier<NetworkEvent.Context> supplier);
}