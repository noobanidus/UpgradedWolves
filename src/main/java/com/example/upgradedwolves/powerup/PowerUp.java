package com.example.upgradedwolves.powerup;

import java.io.IOException;
import java.io.InputStreamReader;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

import net.minecraft.resources.SimpleResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class PowerUp {    
    //Must be a constant size
    public final int xSize = 100;
    public final int ySize = 100;

    public int xLocation;
    public int yLocation;

    protected WolfEntity wolf;
    protected Goal relevantGoal;
    
    protected int levelRequirement;
    //Possibly won't be used Not sure how this will work
    protected int effectiveLevel;
    protected String description;
    protected boolean active;
    protected String name;
    protected ResourceLocation image;
    protected WolfStatsEnum statType;

    private PowerUpData POWER_UP_DATA;

    public PowerUp(int levelRequirement, ResourceLocation resourceLocation){
        initializePowerUp(levelRequirement, resourceLocation, null);
    }

    public PowerUp(int levelRequirement, ResourceLocation resourceLocation,Goal goal){
        initializePowerUp(levelRequirement, resourceLocation, goal);
    }

    private void initializePowerUp(int levelRequirement, ResourceLocation resourceLocation,Goal goal){
        try{
            SimpleResource iresource = (SimpleResource) Minecraft.getInstance().getResourceManager().getResource(resourceLocation);
            Gson itemData =  new Gson();
            POWER_UP_DATA = itemData.fromJson( new InputStreamReader(iresource.getInputStream()),PowerUpData.class);
            
            this.name = resourceLocation.getNamespace() + "." + resourceLocation.getPath() + ".name";
            this.description = resourceLocation.getNamespace() + "." + resourceLocation.getPath() + ".description";
            this.active = POWER_UP_DATA.active;
            this.image = UpgradedWolves.getId(POWER_UP_DATA.image);
            this.xLocation = POWER_UP_DATA.xLocation;
            this.yLocation = POWER_UP_DATA.yLocation;
            this.statType = WolfStatsEnum.values()[POWER_UP_DATA.statType];

            this.relevantGoal = goal;
            this.levelRequirement = levelRequirement;

        }
        catch(IOException e){
            LogManager.getLogger().error("Failed to load powerup: " + resourceLocation.getPath() + '\n' + e.getMessage());
        }
    }
    
    public void setLocation(int xLocation, int yLocation){
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public Goal OnLevelUp(WolfEntity wolf, WolfStatsEnum type, int number){        
        LevelUpAction(wolf,type,number);
        if(type == statType && number > levelRequirement)
            return relevantGoal;
        return null;
    }

    public ITextComponent getName(){
        return new TranslationTextComponent(name);
    }

    public ITextComponent getDescription(){
        return new TranslationTextComponent(description);
    }

    public abstract void LevelUpAction(WolfEntity wolf, WolfStatsEnum type, int number);
}
