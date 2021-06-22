package com.example.upgradedwolves.items.GoldenBone;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class GoldenBone extends GoldenBoneAbstract {

    public GoldenBone() {
        super(new Item.Properties().group(ItemGroup.MISC));
        this.setRegistryName(UpgradedWolves.getId("golden_bone"));
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        Style style = Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.AQUA));
        ITextComponent component = new StringTextComponent(super.getDisplayName(stack).getString()).setStyle(style);
        return component;
    }

    @Override
    public void rightClickWolf(WolfEntity wolf,IWolfStats handler) {
        handler.forceLevelUp(1);
        handler.showParticle(3);
    }
}
