package com.example.upgradedwolves.powerup;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;
import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;

import net.minecraft.resources.SimpleResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class PowerUp {    
    //Must be a constant size
    public final int xSize = 100;
    public final int ySize = 100;

    public int uLocation;
    public int vLocation;

    protected WolfEntity wolf;
    protected Class<? extends Goal> relevantGoal;
    
    protected int levelRequirement;
    //Possibly won't be used Not sure how this will work
    protected int effectiveLevel;
    protected String description;
    protected boolean active;
    protected String name;
    protected ResourceLocation image;
    protected WolfStatsEnum statType;
    protected int givenLevel;

    private PowerUpData POWER_UP_DATA;

    public PowerUp(int levelRequirement, ResourceLocation resourceLocation){
        initializePowerUp(levelRequirement, resourceLocation, null);
    }

    public PowerUp(int levelRequirement, ResourceLocation resourceLocation,Class<? extends Goal> goal){
        initializePowerUp(levelRequirement, resourceLocation, goal);
    }

    private void initializePowerUp(int levelRequirement, ResourceLocation resourceLocation,Class<? extends Goal> goal){
        try{
            SimpleResource iresource = (SimpleResource) Minecraft.getInstance().getResourceManager().getResource(resourceLocation);
            Gson itemData =  new Gson();
            POWER_UP_DATA = itemData.fromJson( new InputStreamReader(iresource.getInputStream()),PowerUpData.class);
            String powerUpName = resourceLocation.getPath().replace("powerups/", "").replace(".json", "");
            this.name = "powerup." + resourceLocation.getNamespace() + "." + powerUpName + ".name";
            this.description = "powerup." + resourceLocation.getNamespace() + "." + powerUpName + ".description";
            this.active = POWER_UP_DATA.active;
            this.image = UpgradedWolves.getId(POWER_UP_DATA.image);
            this.uLocation = POWER_UP_DATA.uLocation;
            this.vLocation = POWER_UP_DATA.vLocation;
            this.statType = WolfStatsEnum.values()[POWER_UP_DATA.statType];

            this.relevantGoal = goal;
            this.levelRequirement = levelRequirement;

        }
        catch(IOException e){
            LogManager.getLogger().error("Failed to load powerup: " + resourceLocation.getPath() + '\n' + e.getMessage());
        }
    }

    public Goal OnLevelUp(WolfEntity wolf, WolfStatsEnum type, int number){        
        LevelUpAction(wolf,type,number);
        if(type == statType && number > levelRequirement){
            try{
                return goalConstructor(wolf);
            } catch(Exception e){
                LogManager.getLogger().error("Failed to instantiate Goal! \n" + e.getMessage());
                return null;
            }   
        }
        return null;
    }

    public ITextComponent getName(){
        Style style = Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.BLUE));
        return new TranslationTextComponent(name).setStyle(style);
    }

    public ITextComponent getDescription(){
        return new TranslationTextComponent(description);
    }

    public abstract void LevelUpAction(WolfEntity wolf, WolfStatsEnum type, int number);

    protected abstract Goal goalConstructor(WolfEntity wolf)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException;
    
    public int iconType(int level){
        int id = 0;        
        if(level >= levelRequirement)
            id += 3;
        if(active)
            id += 1;
        else if(relevantGoal != null)
            id += 2;
        return id;
    }

    public int requiredLevel(){
        return levelRequirement;
    }

    public WolfStatsEnum levelType(){
        return statType;
    }
}
