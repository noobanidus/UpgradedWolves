package com.example.upgradedwolves.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

/**
 * Author: MrCrayfish
 */
public interface IMessage<T>
{
    T encode(T message, FriendlyByteBuf buffer);

    T decode(FriendlyByteBuf buffer);

    T handle(T message, CustomPayloadEvent.Context context);
}