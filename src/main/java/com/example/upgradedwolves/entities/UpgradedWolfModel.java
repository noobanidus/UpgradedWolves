package com.example.upgradedwolves.entities;

import java.util.ArrayList;

import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.Wolf;

public class UpgradedWolfModel extends WolfModel<Wolf> {
    private final ModelPart backPack;
    public UpgradedWolfModel(ModelPart modelPart){
        super(modelPart);
        backPack = modelPart.getChild("backpack");

    }

    
    public static LayerDefinition getBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        //float f = 13.5F;
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
        partdefinition1.addOrReplaceChild("real_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F).texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).texOffs(0, 10).addBox(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(43, 21).addBox(-2.0F, -5.0F, -4.0F, 4.0F, 2.0F, 8.0F), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
        partdefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-2.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder, PartPose.offset(0.5F, 16.0F, 7.0F));
        partdefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-2.5F, 16.0F, -4.0F));
        partdefinition.addOrReplaceChild("left_front_leg", cubelistbuilder, PartPose.offset(0.5F, 16.0F, -4.0F));
        PartDefinition partdefinition2 = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, ((float)Math.PI / 5F), 0.0F, 0.0F));
        partdefinition2.addOrReplaceChild("real_tail", CubeListBuilder.create().texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> bodyParts(){
        ArrayList<ModelPart> list = new ArrayList<ModelPart>();
        super.bodyParts().forEach(list::add);
        list.add(this.backPack);
        return list;
    }

    @Override
    public void prepareMobModel(Wolf entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
        if(entityIn.isInSittingPose()){
            this.backPack.setPos(0.0F, 18.0F, 0.0F);
            this.backPack.xRot = -((float)Math.PI / 4F);
        } else{
            this.backPack.setPos(0.0F, 14.0F, 2.0F);
            this.backPack.xRot = 0;
        }

        this.backPack.zRot = entityIn.getBodyRollAngle(partialTick, -0.16F);
    }
}