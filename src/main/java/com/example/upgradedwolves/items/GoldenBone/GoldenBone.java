package com.example.upgradedwolves.items.GoldenBone;

import com.example.upgradedwolves.UpgradedWolves;
import com.example.upgradedwolves.capabilities.IWolfStats;

import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.ChatFormatting;

public class GoldenBone extends GoldenBoneAbstract {

    public GoldenBone() {
        super(new Item.Properties());
    }

    @Override
    public Component getName(ItemStack stack) {
        Style style = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.AQUA));
        Component component = Component.literal(super.getName(stack).getString()).setStyle(style);
        return component;
    }

    @Override
    public void rightClickWolf(Wolf wolf,IWolfStats handler) {
        handler.forceLevelUp(1);
        handler.showParticle(3);
    }
}
