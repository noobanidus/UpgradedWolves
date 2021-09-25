package com.example.upgradedwolves.entities.plushy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


public class SkeletonPlushyModel extends EntityModel<Entity> {
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bb_main;

	public SkeletonPlushyModel() {
		textureWidth = 32;
		textureHeight = 32;

		bone = new ModelPart(this);
		bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bone, 1.5708F, 0.0F, 0.0F);
		bone.setTextureOffset(8, 15).addBox(-1.5F, 5.0F, -1F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		bone.setTextureOffset(4, 15).addBox(0.5F, 5.0F, -1F, 1.0F, 5.0F, 1.0F, 0.0F, false);

		bone2 = new ModelPart(this);
		bone2.setRotationPoint(0.0F, 0.5F, 0.0F);
		setRotationAngle(bone2, 1.5708F, 0.0F, 0.0F);
		bone2.setTextureOffset(12, 8).addBox(-3.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		bone2.setTextureOffset(0, 15).addBox(2.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		bb_main = new ModelPart(this);
		bb_main.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(bb_main, 1.5708F, 0.0F, 0.0F);
		bb_main.setTextureOffset(0, 0).addBox(-2.0F, -4.0F, -2.5F, 4.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 8).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 5.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(PoseStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bone.render(matrixStack, buffer, packedLight, packedOverlay);
		bone2.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}