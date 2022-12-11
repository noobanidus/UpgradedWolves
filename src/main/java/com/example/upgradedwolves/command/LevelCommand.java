package com.example.upgradedwolves.command;

import java.util.Collection;

import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;

public class LevelCommand {
    static final String COMMAND_KEY = "setWolfLevel";
    static final String ARG_1_NAME = "targets";
    // final String ARG_2_NAME = "wolfStat";
    static final String ARG_3_NAME = "value";

    public static void regsiter(CommandDispatcher<CommandSourceStack> dispatcher){
        RequiredArgumentBuilder<CommandSourceStack, EntitySelector> requiredArguementBuilder = Commands.argument(ARG_1_NAME, EntityArgument.entities());
        //Commands.literal(COMMAND_KEY);

        for(WolfStatsEnum wse : WolfStatsEnum.values()){
            requiredArguementBuilder.then(
                Commands.literal(wse.getName())
                .then(Commands.argument(ARG_3_NAME,IntegerArgumentType.integer(0))
                    .executes(commandContext -> {return setWolfLevel(
                        commandContext.getSource(),
                        EntityArgument.getEntities(commandContext, ARG_1_NAME), 
                        wse.getName(), 
                        IntegerArgumentType.getInteger(commandContext, ARG_3_NAME));}
                        
                    )
                )
            );
        }

        dispatcher.register(Commands.literal(COMMAND_KEY)
        .requires(user -> user.hasPermission(Commands.LEVEL_GAMEMASTERS))
        .then(requiredArguementBuilder));
    }

    private static int setWolfLevel(CommandSourceStack source,Collection<? extends Entity> targets, String wolfStat, int value){
        for(Entity entity : targets){
            if(entity instanceof Wolf){
                Wolf wolf = (Wolf)entity;
                IWolfStats handler = WolfStatsHandler.getHandler(wolf);
                WolfStatsEnum stat = WolfStatsEnum.valueOf(wolfStat);
                handler.setLevel(stat, value);
                if(source.getEntity() != null){
                    source.getEntity().sendSystemMessage(Component.translatable("command.set_wolf_level_success",wolf.getName(),wolfStat,value));
                }
            }
        }
        return 1;
    }
}
