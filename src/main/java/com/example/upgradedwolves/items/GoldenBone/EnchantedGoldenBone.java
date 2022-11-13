package com.example.upgradedwolves.items.GoldenBone;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class EnchantedGoldenBone extends GoldenBoneAbstract {

    public EnchantedGoldenBone() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public Component getName(ItemStack stack) {
        Style style = net.minecraft.network.chat.Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.LIGHT_PURPLE)).withItalic(true);
        Component component = Component.literal(super.getName(stack).getString()).setStyle(style);
        return component;
    }

    @Override
    public void rightClickWolf(Wolf wolf,IWolfStats handler) {        
        handler.forceLevelUp(5);
    }
    
}
