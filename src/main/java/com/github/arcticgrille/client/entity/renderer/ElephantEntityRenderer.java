package com.github.arcticgrille.client.entity.renderer;

import com.github.arcticgrille.ElephantMod;
import com.github.arcticgrille.client.entity.model.ElephantEntityModel;
import com.github.arcticgrille.common.entity.ElephantEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ElephantEntityRenderer extends MobEntityRenderer<ElephantEntity, ElephantEntityModel>
{
	public ElephantEntityRenderer(EntityRenderDispatcher entityRenderDispatcher)
	{
		super(entityRenderDispatcher, new ElephantEntityModel(), .5f);
	}
	
	@Override
	public Identifier getTexture(ElephantEntity entity)
	{
		//FIXME: Get rid of the hardcoded path at some point? Probably a good idea
		return new Identifier(ElephantMod.MOD_ID, "textures/entity/elephant/elephant.png");
	}
}
