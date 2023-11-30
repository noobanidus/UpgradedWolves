package com.example.upgradedwolves.command;

import java.util.Collection;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.example.upgradedwolves.capabilities.WolfType;
import com.example.upgradedwolves.network.PacketHandler;
import com.example.upgradedwolves.network.message.RenderMessage;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.network.PacketDistributor;

public class WolfTypeCommand {
    static final String COMMAND_KEY = "setWolfType";
    static final String ARG_1_NAME = "targets";
    static final String ARG_2_NAME = "wolfType";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        RequiredArgumentBuilder<CommandSourceStack, EntitySelector> requiredArguementBuilder = Commands.argument(ARG_1_NAME, EntityArgument.entities());
        
        for(WolfType wolfType : WolfType.values()){
            requiredArguementBuilder.then(
                Commands.literal(wolfType.toString())
                    .executes(commandContext -> setWolfType(commandContext.getSource(),
                    EntityArgument.getEntities(commandContext, ARG_1_NAME),
                    wolfType.getValue()))
            );
        }

        dispatcher.register(Commands.literal(COMMAND_KEY)
        .requires(user -> user.hasPermission(Commands.LEVEL_GAMEMASTERS))
        .then(requiredArguementBuilder));
    }

    private static int setWolfType(CommandSourceStack source,Collection<? extends Entity> targets, int wolfType){
        for(Entity entity : targets){
            if(entity instanceof Wolf){
                Wolf wolf = (Wolf)entity;
                IWolfStats handler = WolfStatsHandler.getHandler(wolf);
                WolfType type = WolfType.values()[wolfType];
                handler.setWolfType(wolfType);
                handler.setWolffur(wolf.level().random.nextInt(3));
                handler.addGoals();
                handler.handleWolfGoals();
                if(Thread.currentThread().getName() == "Server thread")
                            PacketHandler.INSTANCE.send(new RenderMessage(wolf.getId(),WolfStatsHandler.getHandler(wolf).getWolfType(),handler.getWolfFur()),PacketDistributor.TRACKING_ENTITY.with(wolf));
                if(source.getEntity() != null){
                    source.getEntity().sendSystemMessage(Component.translatable("command.set_wolf_type_success",wolf.getName(),type.toString()));
                }
            }
        }
        return 1;
    }
}
