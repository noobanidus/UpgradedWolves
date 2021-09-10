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
    protected int defaultPriority;
    
    private PowerUpData POWER_UP_DATA;

    public PowerUp(int levelRequirement, String resourceLocationName){
        initializePowerUp(levelRequirement, resourceLocationName, null);
    }

    public PowerUp(int levelRequirement, String resourceLocationName,Class<? extends Goal> goal){
        initializePowerUp(levelRequirement, resourceLocationName, goal);
    }

    private void initializePowerUp(int levelRequirement, String resourceLocationName,Class<? extends Goal> goal){
        ResourceLocation resourceLocation = UpgradedWolves.getId("powerups/" + resourceLocationName + ".json");
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
            this.defaultPriority = POWER_UP_DATA.priority;
            this.effectiveLevel = 0;

            this.relevantGoal = goal;
            this.levelRequirement = levelRequirement;

        }
        catch(IOException e){
            LogManager.getLogger().error("Failed to load powerup: " + resourceLocation.getPath() + '\n' + e.getMessage());
        }
    }

    public Goal OnLevelUp(WolfEntity wolf, WolfStatsEnum type, int number){
        if(type == statType && number > levelRequirement){
            try{
                return goalConstructor(wolf);
            } catch(Exception e){
                LogManager.getLogger().error("Failed to instantiate Goal! \n" + e.getMessage() + e.getStackTrace());
                return null;
            }   
        }
        return null;
    }

    public ITextComponent getName(){
        Style style = Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.BLUE));
        return new TranslationTextComponent(name,effectiveLevel > 0 ? effectiveLevel : "").setStyle(style);
    }

    public ITextComponent getDescription(WolfEntity wolf){
        return new TranslationTextComponent(description,wolf.getName());
    }

    public Goal fetchRelevantGoal(WolfEntity wolf){
        IWolfStats handler = WolfStatsHandler.getHandler(wolf);
        int statLevel = handler.getLevel(statType);
        if(statLevel >= levelRequirement){
            try{
                return goalConstructor(wolf);
            } catch(Exception e){
                LogManager.getLogger().error("Failed to instantiate Goal! \n" + e.getMessage());
                return null;
            }   
        }
        return null;
    }
    
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
    
    public int priority(){
        return defaultPriority;
    }

    protected Goal genericGoalConstructor(WolfEntity wolf) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
        return (Goal)relevantGoal.getDeclaredConstructors()[0].newInstance(wolf);
    }

    protected abstract Goal goalConstructor(WolfEntity wolf)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException;
    
}
