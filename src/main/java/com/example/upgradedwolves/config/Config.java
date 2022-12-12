package com.example.upgradedwolves.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.example.upgradedwolves.personality.Behavior;
import com.example.upgradedwolves.powerup.PowerUpList;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public final class Config {
    public static final ForgeConfigSpec commonSpec;
    public static final Config.Common COMMON;

    static
    {
        final Pair<Common, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(Config.Common::new);
        commonSpec = commonPair.getRight();
        COMMON = commonPair.getLeft();
    }
    
    public static class Common{

        public final WolfType wolfType;
        public final WolfLevelling wolfLevelling;
        public final WolfPersonality wolfPersonality;
        public final PowerUpConfig powerUps;

        Common(ForgeConfigSpec.Builder builder){
            builder.comment("Common configuration settings").push("common");
            this.wolfType = new WolfType(builder, true, true, true);
            this.wolfLevelling = new WolfLevelling(builder, 2, 1, 1, 2, 1, 2, 1.0, 2);
            this.wolfPersonality = new WolfPersonality(builder, true, true, true, true, true, true, true, true, true);
            this.powerUps = new PowerUpConfig(builder, .05, 2D, 1D, PowerUpList.StrengthWolfDefault, PowerUpList.ScavengerWolfDefault, PowerUpList.ShowWolfDefault, PowerUpList.notSetDefault);
            builder.pop();
        }

        public static class WolfType{
            static final String NAME = "Wolf Type Settings";
            static final String KEY = "wolfType";

            public final ForgeConfigSpec.BooleanValue fighterWolfEnabled;
            public final ForgeConfigSpec.BooleanValue scavengerWolfEnabled;
            public final ForgeConfigSpec.BooleanValue showWolfEnabled;

            public WolfType(ForgeConfigSpec.Builder builder, boolean fighterWolf, boolean scavengerWolf, boolean showWolf){
                builder.comment(NAME).push(KEY);
                this.fighterWolfEnabled = builder.comment("Allows the fighter wolf type")
                    .define("fighterWolfEnabled", fighterWolf);
                this.scavengerWolfEnabled = builder.comment("Allows the scavenger wolf type")
                    .define("scavengerWolfEnabled", scavengerWolf);
                this.showWolfEnabled = builder.comment("Allows the show wolf type")
                    .define("showWolfEnabled", showWolf);
                builder.pop();
            }
        }

        public static class WolfLevelling{
            static final String NAME = "Wolf level up settings";
            static final String KEY = "levelSettings";
            public final ConfigValue<Integer> wolfAttackXp;
            public final ConfigValue<Integer> fighterWolfAttackBonusXp;
            public final ConfigValue<Integer> wolfKillIntXp;
            public final ConfigValue<Integer> wolfKillSpeedXp;
            public final ConfigValue<Integer> scavengerWolfSpeedXp;
            public final ConfigValue<Integer> tugOfWarStrengthBonus;
            public final ConfigValue<Double> fetchSpeedModifier;
            public final ConfigValue<Integer> plushyIntelligenceBonus;

            public WolfLevelling(ForgeConfigSpec.Builder builder, int wolfAttackXp, int fighterWolfAttackBonusXp, int getWolfKillIntXp,
                    int getWollKillSpeedXp, int scavengerWolfSpeedXp, int tugOfWarStrengthBonus,
                    double fetchSpeedModifier, int plushyIntelligenceBonus) {
                builder.comment(NAME).push(KEY);
                this.wolfAttackXp = builder.comment("How much STR XP a wolf gets for attacking.").define("wolfAttackXp",wolfAttackXp);
                this.fighterWolfAttackBonusXp = builder.comment("How much STR bonus XP a fighter wolf gets for attacking.").define("fighterWolfAttackBonusXp",fighterWolfAttackBonusXp);
                this.wolfKillIntXp = builder.comment("How much INT XP a wolf gets for a kill").define("getWolfKillIntXp",getWolfKillIntXp);
                this.wolfKillSpeedXp = builder.comment("How much SPD XP a wolf gets for a kill").define("getWollKillSpeedXp",getWollKillSpeedXp);
                this.scavengerWolfSpeedXp = builder.comment("How much SPD XP a scavenger wolf gets for moving").define("scavengerWolfSpeedXp",scavengerWolfSpeedXp);
                this.tugOfWarStrengthBonus = builder.comment("How much STR XP a wolf gets for playing tug of war").define("tugOfWarStrengthBonus",tugOfWarStrengthBonus);
                this.fetchSpeedModifier = builder.comment("How much SPD XP a wolf gets for playing fetch").define("fetchSpeedModifier",fetchSpeedModifier);
                this.plushyIntelligenceBonus = builder.comment("How much INT xp a wolf gets for playing with a plush toy.").define("plushyIntelligenceBonus",plushyIntelligenceBonus);
                builder.pop();
            }

        }

        public static class WolfPersonality{
            final String NAME = "Wolf Personality Settings";
            final String KEY = "personalitySettings";
            public final ForgeConfigSpec.BooleanValue personalityTypesEnabled;
            public final ForgeConfigSpec.BooleanValue subBehaviorEnabled;
            public final ForgeConfigSpec.BooleanValue aggressivePersonalityEnabled;
            public final ForgeConfigSpec.BooleanValue socialPersonalityEnabled;
            public final ForgeConfigSpec.BooleanValue affectionatePersonalityEnabled;
            public final ForgeConfigSpec.BooleanValue playfulPersonalityEnabled;
            public final ForgeConfigSpec.BooleanValue dominantPersonalityEnabled;
            public final ForgeConfigSpec.BooleanValue lazyPersonalityEnabled;
            public final ForgeConfigSpec.BooleanValue shyPersonalityEnabled;

            public WolfPersonality(ForgeConfigSpec.Builder builder, boolean personalityTypesEnabled, boolean subBehaviorEnabled,
                    boolean aggressivePersonalityEnabled, boolean socialPersonalityEnabled,
                    boolean affectionatePersonalityEnabled, boolean playfulPersonalityEnabled,
                    boolean dominantPersonalityEnabled, boolean lazyPersonalityEnabled,
                    boolean shyPersonalityEnabled) {
                builder.comment(NAME).push(KEY);
                this.personalityTypesEnabled = builder.comment("Enables personality types").define("personalityTypesEnabled",personalityTypesEnabled);
                this.subBehaviorEnabled = builder.comment("Enables personality sub behaviors").define("subBehaviorEnabled",subBehaviorEnabled);
                this.aggressivePersonalityEnabled = builder.comment("Enables the aggressive personality type").define("aggressivePersonalityEnabled",aggressivePersonalityEnabled);
                this.socialPersonalityEnabled = builder.comment("Enables the social personality type").define("socialPersonalityEnabled",socialPersonalityEnabled);
                this.affectionatePersonalityEnabled = builder.comment("Enables the affectionate personality type").define("affectionatePersonalityEnabled",affectionatePersonalityEnabled);
                this.playfulPersonalityEnabled = builder.comment("Enables the playful personality type").define("playfulPersonalityEnabled",playfulPersonalityEnabled);
                this.dominantPersonalityEnabled = builder.comment("Enables the dominant personality type").define("dominantPersonalityEnabled",dominantPersonalityEnabled);
                this.lazyPersonalityEnabled = builder.comment("Enables the lazy personality type").define("lazyPersonalityEnabled",lazyPersonalityEnabled);
                this.shyPersonalityEnabled = builder.comment("Enables the shy personality type").define("shyPersonalityEnabled",shyPersonalityEnabled);
                builder.pop();
            }

            public List<Behavior> getAllowedTypes() {
                List<Behavior> behaviorTypes = new ArrayList<Behavior>();
                if(aggressivePersonalityEnabled.get()) behaviorTypes.add(Behavior.Aggressive);
                if(socialPersonalityEnabled.get()) behaviorTypes.add(Behavior.Social);
                if(affectionatePersonalityEnabled.get()) behaviorTypes.add(Behavior.Affectionate);
                if(playfulPersonalityEnabled.get()) behaviorTypes.add(Behavior.Playful);
                if(dominantPersonalityEnabled.get()) behaviorTypes.add(Behavior.Dominant);
                if(lazyPersonalityEnabled.get()) behaviorTypes.add(Behavior.Lazy);
                if(shyPersonalityEnabled.get()) behaviorTypes.add(Behavior.Shy);
                behaviorTypes.add(Behavior.EMPTY);
                
                return behaviorTypes;
            }

        }

        public static class PowerUpConfig{
            final String NAME = "Power UPs";
            final String KEY = "powerUp";
            public final ConfigValue<Double> speedBonusDefault; //.05
            public final ConfigValue<Double> detectBonusDefault;//2
            public final ConfigValue<Double> attackBonusDefault; //1
            public final ForgeConfigSpec.ConfigValue<List<? extends String>> strengthWolfPowerUps;
            public final ForgeConfigSpec.ConfigValue<List<? extends String>> scavengerWolfPowerUps;
            public final ForgeConfigSpec.ConfigValue<List<? extends String>> showWolfPowerUps;
            public final ForgeConfigSpec.ConfigValue<List<? extends String>> unsetWolfPowerUps;

            public PowerUpConfig(ForgeConfigSpec.Builder builder,Double speedBonusDefault, Double detectBonusDefault,
                    Double attackBonusDefault, List<String> strengthWolfPowerUps,
                    List<String> scavengerWolfPowerUps, List<String> showWolfPowerUps,
                    List<String> unsetWolfPowerUps) {
                builder.comment(NAME).push(KEY);
                this.speedBonusDefault = builder.comment("Default bonus for enhance speed powerup").define("speedBonusDefault",speedBonusDefault);
                this.detectBonusDefault = builder.comment("Default bonus for enhance detect powerup").define("detectBonusDefault",detectBonusDefault);
                this.attackBonusDefault = builder.comment("Default bonus for enhance attack powerup").define("attackBonusDefault",attackBonusDefault);
                this.strengthWolfPowerUps = builder.comment("The power up list for the strength wolf").defineList("strengthWolfPowerUps",strengthWolfPowerUps, obj -> obj instanceof String);
                this.scavengerWolfPowerUps = builder.comment("The power up list for the scavenger wolf").defineList("scavengerWolfPowerUps",scavengerWolfPowerUps, obj -> obj instanceof String);
                this.showWolfPowerUps = builder.comment("The power up list for the show wolf").defineList("showWolfPowerUps",showWolfPowerUps, obj -> obj instanceof String);
                this.unsetWolfPowerUps = builder.comment("The power up list for the default wolf").defineList("unsetWolfPowerUps",unsetWolfPowerUps, obj -> obj instanceof String);
                builder.pop();
            }
        }

    }
}
