package com.example.upgradedwolves.entities.plushy;

import java.util.concurrent.Callable;

import com.example.upgradedwolves.items.MobPlushy;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;

public class TilePlushyEntity extends ItemStackTileEntityRenderer implements Callable<ItemStackTileEntityRenderer> {
    
    public static ItemStackTileEntityRenderer instance;
    protected EntityModel<Entity> model;

    public TilePlushyEntity(){
        instance = this;
    }

    @Override
    public void func_239207_a_(ItemStack stack, TransformType p_239207_2_, MatrixStack matrixStack,
            IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        super.func_239207_a_(stack, p_239207_2_, matrixStack, buffer, combinedLight, combinedOverlay);
        MobPlushy item = (MobPlushy)stack.getItem();
        if(model == null){            
            model = MobPlushy.getModelByPlushType(item);
        }
        matrixStack.push();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        IVertexBuilder ivertexbuilder1 = ItemRenderer.getEntityGlintVertexBuilder(buffer, model.getRenderType(MobPlushy.getPlushTexture(item)), false, stack.hasEffect());
        model.render(matrixStack, ivertexbuilder1, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        
    }

    @Override
    public ItemStackTileEntityRenderer call() throws Exception {
        
        return instance;
    }
}
