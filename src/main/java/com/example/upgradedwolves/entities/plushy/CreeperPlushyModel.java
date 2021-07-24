package com.example.upgradedwolves.entities.plushy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings


public class CreeperPlushyModel extends EntityModel<Entity> {
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer bb_main;

	public CreeperPlushyModel() {
		textureWidth = 32;
		textureHeight = 32;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 0F, 0.0F);
		setRotationAngle(bone, 1.5708F, 0.0F, 0.0F);
		bone.setTextureOffset(0, 17).addBox(-0.0F, 7.0F, 0.5F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		bone.setTextureOffset(16, 0).addBox(-2.0F, 7.0F, 0.5F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 0F, 0.0F);
		setRotationAngle(bone2, 1.5708F, 0.0F, 0.0F);
		bone2.setTextureOffset(12, 8).addBox(-2.0F, 7.0F, -3.5F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		bone2.setTextureOffset(12, 13).addBox(0.0F, 7.0F, -3.5F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bb_main, 1.5708F, 0.0F, 0.0F);
		bb_main.setTextureOffset(0, 0).addBox(-2.0F, -4.0F, -2.5F, 4.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 8).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 7.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bone.render(matrixStack, buffer, packedLight, packedOverlay);
		bone2.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}