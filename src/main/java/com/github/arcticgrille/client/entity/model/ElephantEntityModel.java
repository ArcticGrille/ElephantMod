package com.github.arcticgrille.client.entity.model;

import com.github.arcticgrille.common.entity.ElephantEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;


// IF YOU HAVE COMMENTS COLLAPSED PLEASE READ THIS ONE THX
/*
 * Literally none of this is permanent. This is just a placeholder so that we can have literally anything to test with
 * Whenever we have an actual elephant model it will replace this one and the texture will replace the current one
 * GO TO: resources/assets/elephantmod/textures/entity/elephant/elephant.ong to replace it
 * Thanks -Jolkert 2020-07-14
 */

public class ElephantEntityModel extends EntityModel<ElephantEntity>
{
	private final ModelPart base;
	
	public ElephantEntityModel()
	{
		this.textureHeight = 64;
		this.textureWidth = 64;
		
		base = new ModelPart(this, 0, 0);
		base.addCuboid(-6, -6 , -6, 12, 12, 12);
	}
	
	@Override
	public void setAngles(ElephantEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch){}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha)
	{
		matrices.translate(0, 1.125, 0);
		base.render(matrices, vertices, light, overlay);
	}
}
