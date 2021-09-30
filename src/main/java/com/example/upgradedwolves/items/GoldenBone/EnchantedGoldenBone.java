package com.example.upgradedwolves.items.GoldenBone;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class EnchantedGoldenBone extends GoldenBoneAbstract {

    public EnchantedGoldenBone() {
        super(new Item.Properties().group(ItemGroup.MISC));
        this.setRegistryName(UpgradedWolves.getId("enchanted_golden_bone"));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public Component getDisplayName(ItemStack stack) {
        Style style = Style.EMPTY.setColor(TextColor.fromTextFormatting(TextFormatting.LIGHT_PURPLE)).setItalic(true);
        Component component = new TextComponent(super.getDisplayName(stack).getString()).setStyle(style);
        return component;
    }

    @Override
    public void rightClickWolf(Wolf wolf,IWolfStats handler) {        
        handler.forceLevelUp(5);
    }
    
}
