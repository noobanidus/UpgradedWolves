package com.example.upgradedwolves.powerup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.WolfType;
import com.example.upgradedwolves.config.Config;

public class PowerUpListBuilder {

    private static Map<WolfType,List<PowerUp>> powerUpMap = new HashMap<>();
    
    public static List<PowerUp> buildOrRetrieve(WolfType type) throws Exception{
        if(powerUpMap.containsKey(type)){
            return powerUpMap.get(type);
        }
        try{
            List<String> definition = getDefinitionByWolfType(type);
            List<PowerUp> powerUps = build(definition);
            powerUpMap.put(type, powerUps);

            return powerUps;
        } catch (IllegalArgumentException e){
            UpgradedWolves.LOGGER.error(e.getMessage() + e.getStackTrace());
            List<String> definition = getDefaultDefinitionByWolfType(type);
            List<PowerUp> powerUps = build(definition);
            powerUpMap.put(type, powerUps);
            return powerUps;
        }
    }

    private static List<String> getDefaultDefinitionByWolfType(WolfType type) {
        switch(type){
            case Fighter:
                return PowerUpList.StrengthWolfDefault;
            case NotSet:
                return PowerUpList.notSetDefault;
            case Scavenger:
                return PowerUpList.ScavengerWolfDefault;
            case Show:
                return PowerUpList.ShowWolfDefault;
            default:
                return null;
        }
    }

    private static List<String> getDefinitionByWolfType(WolfType type) {
        switch(type){
            case Fighter:
                return Config.COMMON.powerUps.strengthWolfPowerUps.get();
            case NotSet:
                return Config.COMMON.powerUps.unsetWolfPowerUps.get();
            case Scavenger:
                return Config.COMMON.powerUps.scavengerWolfPowerUps.get();
            case Show:
                return Config.COMMON.powerUps.showWolfPowerUps.get();
            default:
                return null;
        }
    }

    private static List<PowerUp> build(List<String> definition) throws Exception, IllegalArgumentException{
        List<PowerUp> powerUpList = new ArrayList<PowerUp>();
        for(String item : definition){
            String[] split = item.split(":");
            if(split.length != 2){
                throw new IllegalArgumentException("Invalid argument " + item + ". String must be in format \"<number>:<resourceName>\"");
            }
            int level = Integer.parseInt(split[0]);
            Class powerUpClass = PowerUpList.PowerUpIdMap.get(split[1]);
            if(powerUpClass == null){
                throw new IllegalArgumentException("resource not found for " + item + ".");
            }

            if(powerUpClass.isAssignableFrom(BonusStatPowerUp.class)){
                int count = (int)powerUpList.stream().filter(x -> x.getClass().equals(powerUpClass.getClass())).count();
                powerUpList.add((PowerUp)powerUpClass.getConstructors()[0].newInstance(split[1],count+1,getConfigBonus(powerUpClass)));
            }
            else if(powerUpClass.isAssignableFrom(PowerUp.class)){
                powerUpList.add((PowerUp)powerUpClass.getConstructors()[0].newInstance(split[1]));
            }
            else throw new IllegalArgumentException("resource not found for " + item + ".");
        }

        return powerUpList;
    }

    private static double getConfigBonus(Class clazz) throws Exception{
        if(clazz == EnhanceSpeedPowerUp.class){
            return Config.COMMON.powerUps.speedBonusDefault.get();
        }
        else if(clazz == EnhanceDetectionPowerUp.class) {
            return Config.COMMON.powerUps.detectBonusDefault.get();
        }
        else if (clazz == EnhanceAttackPowerUp.class){
            return Config.COMMON.powerUps.attackBonusDefault.get();
        }
        throw new Exception("How did we get here?");
    }
    
}
