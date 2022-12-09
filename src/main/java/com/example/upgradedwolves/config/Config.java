package com.example.upgradedwolves.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

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

        Common(ForgeConfigSpec.Builder builder){
            builder.comment("Common configuration settings").push("common");
            this.wolfType = new WolfType(builder, true, true, true);
            this.wolfLevelling = new WolfLevelling(builder, 2, 1, 1, 2, 1, 2, 1.0, 2);
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
                this.wolfAttackXp = builder.comment("How much STR XP a wolf gets for attacking.").define("wolfAttackXp",wolfAttackXp);
                this.fighterWolfAttackBonusXp = builder.comment("How much STR bonus XP a fighter wolf gets for attacking.").define("fighterWolfAttackBonusXp",fighterWolfAttackBonusXp);
                this.wolfKillIntXp = builder.comment("How much INT XP a wolf gets for a kill").define("getWolfKillIntXp",getWolfKillIntXp);
                this.wolfKillSpeedXp = builder.comment("How much SPD XP a wolf gets for a kill").define("getWollKillSpeedXp",getWollKillSpeedXp);
                this.scavengerWolfSpeedXp = builder.comment("How much SPD XP a scavenger wolf gets for moving").define("scavengerWolfSpeedXp",scavengerWolfSpeedXp);
                this.tugOfWarStrengthBonus = builder.comment("How much STR XP a wolf gets for playing tug of war").define("tugOfWarStrengthBonus",tugOfWarStrengthBonus);
                this.fetchSpeedModifier = builder.comment("How much SPD XP a wolf gets for playing fetch").define("fetchSpeedModifier",fetchSpeedModifier);
                this.plushyIntelligenceBonus = builder.comment("How much INT xp a wolf gets for playing with a plush toy.").define("plushyIntelligenceBonus",plushyIntelligenceBonus);
            }

        }

        // public static class PowerUp{
        //     public final ForgeConfigSpec. test;
        // }
    }
}
