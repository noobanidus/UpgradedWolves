package com.example.upgradedwolves.network.message;

import java.util.Random;
import java.util.function.Supplier;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class SpawnLevelUpParticle implements IMessage<SpawnLevelUpParticle> {
    int wolfId;
    int statId;

    public SpawnLevelUpParticle(){
        wolfId = 0;
        statId = 0;
    }

    public SpawnLevelUpParticle(int wolf, int stat) {
        wolfId = wolf;
        statId = stat;
    }

    @Override
    public void encode(SpawnLevelUpParticle message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.wolfId);
        buffer.writeInt(message.statId);
    }

    @Override
    public SpawnLevelUpParticle decode(FriendlyByteBuf buffer) {
        
        return new SpawnLevelUpParticle(buffer.readInt(),buffer.readInt());
    }

    @Override
    public void handle(SpawnLevelUpParticle message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Wolf wolf = (Wolf)mc.level.getEntity(message.wolfId);
            WolfStatsEnum stat = WolfStatsEnum.values()[message.statId];
            if(wolf.getOwner() == mc.player && message.statId != 3)
                mc.player.sendMessage((Component)new TranslatableComponent("chat.upgradedwolves.level_up",wolf.getName(),stat.toString()),
            Util.DUMMY_UUID);
            Random r = new Random();
            BasicParticleType pt = ParticleTypes.FLASH;
            switch(stat){
                case Strength:
                pt = ParticleTypes.FLAME;
                break;
                case Intelligence:
                pt = ParticleTypes.ENTITY_EFFECT;
                break;
                case Speed:
                pt = ParticleTypes.FIREWORK;
                break;
                case Love:
                pt = ParticleTypes.HEART;
                break;
            }
            for(int i = 0; i < 15; i++)
                mc.level.addParticle(pt, false, wolf.getPosition(1).getX() + r.nextDouble(), wolf.getPosition(1).getY() + r.nextDouble(), wolf.getPosition(1).getZ() + r.nextDouble(), r.nextDouble()/5, r.nextDouble()/5, r.nextDouble()/5);
        });
        supplier.get().setPacketHandled(true);
    }
    
}
