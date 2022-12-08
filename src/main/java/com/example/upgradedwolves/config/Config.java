package com.example.upgradedwolves.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

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

        Common(ForgeConfigSpec.Builder builder){
            builder.comment("Common configuration settings").push("common");
            this.wolfType = new WolfType(builder, true, true, true);
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

        // public static class PowerUp{
        //     public final ForgeConfigSpec. test;
        // }
    }
}
