package com.fravokados.techmobs.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelConservationUnit extends ModelBase {

    ModelRenderer head;
    ModelRenderer body;

    ModelRenderer arm_right;
    ModelRenderer arm_left;
    ModelRenderer leg_right;
    ModelRenderer leg_left;

    ModelRenderer wall_back;
	ModelRenderer wall_base;
	ModelRenderer wall_top;
	ModelRenderer wall_side_1;
	ModelRenderer wall_side_2;

    ModelRenderer chest;
    ModelRenderer pipe1;
	ModelRenderer pipe2;

    public ModelConservationUnit() {
	    textureWidth = 128;
	    textureHeight = 64;

	    head = new ModelRenderer(this, 0, 0);
	    head.addBox(-4F, -8F, -4F, 8, 8, 2);
	    head.setRotationPoint(0F, -2F, 0F);
	    head.mirror = true;

	    body = new ModelRenderer(this, 0, 10);
	    body.addBox(-4F, 0F, -2F, 8, 12, 1);
	    body.setRotationPoint(0F, -2F, -2F);
	    body.mirror = true;

	    arm_right = new ModelRenderer(this, 19, 10);
	    arm_right.addBox(-3F, -2F, -2F, 4, 12, 1);
	    arm_right.setRotationPoint(-5F, 0F, -2F);
	    arm_right.mirror = true;

	    arm_left = new ModelRenderer(this, 19, 10);
	    arm_left.addBox(-1F, -2F, -2F, 4, 12, 1);
	    arm_left.setRotationPoint(5F, 0F, -2F);
	    arm_left.mirror = true;

	    leg_right = new ModelRenderer(this, 19, 23);
	    leg_right.addBox(-2F, 0F, -2F, 4, 12, 1);
	    leg_right.setRotationPoint(-2F, 10F, -2F);
	    leg_right.mirror = true;

	    leg_left = new ModelRenderer(this, 19, 23);
	    leg_left.addBox(-2F, 0F, -2F, 4, 12, 1);
	    leg_left.setRotationPoint(2F, 10F, -2F);
	    leg_left.mirror = true;

	    wall_back = new ModelRenderer(this, 84, 0);
	    wall_back.addBox(0F, 0F, 0F, 16, 34, 6);
	    wall_back.setRotationPoint(-8F, -12F, -10F);
	    wall_back.mirror = true;

	    chest = new ModelRenderer(this, 0, 44);
	    chest.addBox(0F, 0F, 0F, 10, 10, 10);
	    chest.setRotationPoint(-5F, 12F, -3F);
	    chest.mirror = true;

	    wall_base = new ModelRenderer(this, 64, 46);
	    wall_base.addBox(0F, 0F, 0F, 16, 2, 16);
	    wall_base.setRotationPoint(-8F, 22F, -9F);
	    wall_base.mirror = true;

	    wall_top = new ModelRenderer(this, 65, 47);
	    wall_top.addBox(0F, 0F, 0F, 16, 1, 15);
	    wall_top.setRotationPoint(-8F, -13F, -8F);
	    wall_top.mirror = true;

	    wall_side_1 = new ModelRenderer(this, 31, 0);
	    wall_side_1.addBox(0F, 0F, 0F, 1, 34, 15);
	    wall_side_1.setRotationPoint(8F, -12F, -8F);
	    wall_side_1.mirror = true;

	    wall_side_2 = new ModelRenderer(this, 47, 0);
	    wall_side_2.addBox(0F, 0F, 0F, 1, 34, 15);
	    wall_side_2.setRotationPoint(-9F, -12F, -8F);
	    wall_side_2.mirror = true;

	    pipe1 = new ModelRenderer(this, 0, 23);
	    pipe1.addBox(0F, 0F, 0F, 3, 3, 3);
	    pipe1.setRotationPoint(5F, 15F, 0F);
	    pipe1.mirror = true;

	    pipe2 = new ModelRenderer(this, 0, 23);
	    pipe2.addBox(0F, 0F, 0F, 3, 3, 3);
	    pipe2.setRotationPoint(-8F, 15F, 0F);
	    pipe2.mirror = true;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    head.render(f5);
	    body.render(f5);
	    arm_right.render(f5);
	    arm_left.render(f5);
	    leg_right.render(f5);
	    leg_left.render(f5);
	    wall_back.render(f5);
	    chest.render(f5);
	    wall_base.render(f5);
	    wall_top.render(f5);
	    wall_side_1.render(f5);
	    wall_side_2.render(f5);
	    pipe1.render(f5);
	    pipe2.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
