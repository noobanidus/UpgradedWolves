package com.example.upgradedwolves.powerup;

import java.lang.reflect.InvocationTargetException;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;
import com.example.upgradedwolves.capabilities.WolfStatsEnum;
import com.example.upgradedwolves.capabilities.WolfStatsHandler;

import org.apache.logging.log4j.LogManager;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class PowerUp {    
    //Must be a constant size
    public final int xSize = 100;
    public final int ySize = 100;
    
    public int uLocation;
    public int vLocation;
    
    protected Wolf wolf;
    protected Class<? extends Goal> relevantGoal;
    
    protected int levelRequirement;
    //Possibly won't be used Not sure how this will work
    protected int effectiveLevel;
    protected String description;
    protected boolean active;
    protected String name;
    protected net.minecraft.resources.ResourceLocation image;
    protected WolfStatsEnum statType;
    protected int givenLevel;
    protected int defaultPriority;        

    public PowerUp(int levelRequirement, String resourceLocationName){
        initializePowerUp(levelRequirement, resourceLocationName, null);
    }

    public PowerUp(int levelRequirement, String resourceLocationName,Class<? extends Goal> goal){
        initializePowerUp(levelRequirement, resourceLocationName, goal);
    }

    private void initializePowerUp(int levelRequirement, String resourceLocationName,Class<? extends Goal> goal){
        ResourceLocation resourceLocation = UpgradedWolves.getId("powerups/" + resourceLocationName + ".json");
        String powerUpName = resourceLocation.getPath().replace("powerups/", "").replace(".json", "");
        this.name = "powerup." + resourceLocation.getNamespace() + "." + powerUpName + ".name";
        this.description = "powerup." + resourceLocation.getNamespace() + "." + powerUpName + ".description";
        this.effectiveLevel = 0;

        this.relevantGoal = goal;
        this.levelRequirement = levelRequirement;    
    }

    public Goal OnLevelUp(Wolf wolf, WolfStatsEnum type, int number){
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

    public Component getName(){
        Style style = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.BLUE));
        return new TranslatableComponent(name,effectiveLevel > 0 ? effectiveLevel : "").setStyle(style);
    }

    public Component getDescription(Wolf wolf){
        return new TranslatableComponent(description,wolf.getName());
    }

    public Goal fetchRelevantGoal(Wolf wolf){
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

    protected Goal genericGoalConstructor(Wolf wolf) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
        return (Goal)relevantGoal.getDeclaredConstructors()[0].newInstance(wolf);
    }

    protected abstract Goal goalConstructor(Wolf wolf)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException;
    
}
