package com.example.upgradedwolves.powerup;


public class PowerUpList {
    public static final PowerUp[] StrengthWolf = {     
        new AutoAttackPowerUp(5),
        new EnhanceAttackPowerUp(5, 1, 1),
        new FleeHealthPowerUp(6),
        new EnhanceSpeedPowerUp(7, 1, 0.05D),
        new FleeCreeperPowerUp(8),
        new UseSwordPowerUp(9),
        new PickUpItemPowerUp(10),
        new EnhanceAttackPowerUp(10, 2, 1),
        new BarkStunPowerUp(13),
        new DisarmEnemyPowerUp(14),
        new EnhanceAttackPowerUp(15, 3, 1),
        new ArrowInterceptPowerUp(18),
        new EnhanceSpeedPowerUp(18, 2, 0.05D),
        new EnhanceAttackPowerUp(20,4,1),
        new WolfTossArrowPowerUp(21)
    };
    public static final PowerUp[] ScavengerWolf = {
        new FleeHealthPowerUp(2),
        new PickUpItemPowerUp(2),
        new EnhanceSpeedPowerUp(5,1,0.05D),
        new FleeCreeperPowerUp(7),
        new DetectEnemyPowerUp(8),
        new EnhanceSpeedPowerUp(10,2,0.05D),
        new SelfPreservationPowerUp(11),
        new RetrieveOnDeathPowerUp(12),
        new EnhanceSpeedPowerUp(15,3,0.05D), 
        new DigForItemPowerUp(17),
        new EnhanceSpeedPowerUp(20,4,0.05D),
        new LootBonusPowerUp(22),
        new EnhanceSpeedPowerUp(25,5,0.05D)
    };
    public static final PowerUp[] newWolf = { /* Oh what's this? */
        new ShareItemPowerUp(4),
        new FishForItemPowerUp(7),
        new ThrowPotionPowerUp(11)
    };
    public static final PowerUp[] notSet = {
        new FleeHealthPowerUp(5),
        new FleeCreeperPowerUp(10),
    };

}
