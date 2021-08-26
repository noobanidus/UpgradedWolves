package com.example.upgradedwolves.powerup;


public class PowerUpList {
    public static final PowerUp[] StrengthWolf = {
        new AutoAttackPowerUp(5),
        new FleeHealthPowerUp(6),
        new FleeCreeperPowerUp(8),
        new PickUpItemPowerUp(10)
    };
    public static final PowerUp[] ScavengerWolf = {
        new FleeHealthPowerUp(2),
        new PickUpItemPowerUp(2),
        new EnhanceSpeedPowerUp(5,1,0.5D),
        new FleeCreeperPowerUp(7),
        new DetectEnemyPowerUp(8),
        new EnhanceSpeedPowerUp(10,2,0.5D),
        new EnhanceSpeedPowerUp(15,3,0.5D),
        new EnhanceSpeedPowerUp(20,4,0.5D),
        new EnhanceSpeedPowerUp(25,5,0.5D)
    };
    public static final PowerUp[] newWolf = { /* Oh what's this? */};
    public static final PowerUp[] notSet = {
        new FleeHealthPowerUp(5),
        new FleeCreeperPowerUp(10),
    };

}
