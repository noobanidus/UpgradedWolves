package com.example.upgradedwolves.network.message;

import java.util.Random;
import java.util.function.Supplier;

import com.example.upgradedwolves.capabilities.WolfStatsEnum;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

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
    public void encode(SpawnLevelUpParticle message, PacketBuffer buffer) {
        buffer.writeInt(message.wolfId);
        buffer.writeInt(message.statId);
    }

    @Override
    public SpawnLevelUpParticle decode(PacketBuffer buffer) {
        
        return new SpawnLevelUpParticle(buffer.readInt(),buffer.readInt());
    }

    @Override
    public void handle(SpawnLevelUpParticle message, Supplier<Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            WolfEntity wolf = (WolfEntity)mc.world.getEntityByID(message.wolfId);
            WolfStatsEnum stat = WolfStatsEnum.values()[message.statId];
            if(wolf.getOwner() == mc.player && message.statId != 3)
                mc.player.sendMessage((ITextComponent)new StringTextComponent((wolf.hasCustomName() ? wolf.getCustomName().getString() : "Wolf") + " has leveled up " + stat.toString()),
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
                mc.world.addParticle(pt, false, wolf.getPosition().getX() + r.nextDouble(), wolf.getPosition().getY() + r.nextDouble(), wolf.getPosition().getZ() + r.nextDouble(), r.nextDouble()/5, r.nextDouble()/5, r.nextDouble()/5);
        });
        supplier.get().setPacketHandled(true);
    }
    
}
