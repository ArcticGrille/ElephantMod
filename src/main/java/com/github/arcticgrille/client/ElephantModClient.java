package com.github.arcticgrille.client;

import com.github.arcticgrille.ElephantMod;
import com.github.arcticgrille.client.entity.renderer.ElephantEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class ElephantModClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		EntityRendererRegistry.INSTANCE.register(ElephantMod.ELEPHANT_ENTITY, (dispatcher, context) -> {return new ElephantEntityRenderer(dispatcher);});
	}
}
