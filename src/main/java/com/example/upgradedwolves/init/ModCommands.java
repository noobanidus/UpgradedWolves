package com.example.upgradedwolves.init;

import com.example.upgradedwolves.command.LevelCommand;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCommands {

    
    @SubscribeEvent
    public void onEntityTypeRegistration(RegisterCommandsEvent event){
        LevelCommand.regsiter(event.getDispatcher());
    }
}
