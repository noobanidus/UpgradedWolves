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
        new FleeCreeperPowerUp(7),
        new DetectEnemyPowerUp(8)
    };
public static final PowerUp[] newWolf = { /* Oh what's this? */};
    public static final PowerUp[] notSet = {
        new FleeHealthPowerUp(5),
        new FleeCreeperPowerUp(10),
    };

}
