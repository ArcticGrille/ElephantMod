package com.github.arcticgrille.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PassiveEntity.class)
public abstract class PassiveEntityMixin extends LivingEntityMixin
{
	public PassiveEntityMixin(EntityType<?> type, World world)
	{
		super(type, world);
	}
	
	@Override
	public void tick(CallbackInfo info)
	{
		if(this.getName().asString().equalsIgnoreCase("brian"))
			setBaby(true);
		super.tick(info);
	}
	
	@Shadow public abstract void setBaby(boolean baby);
}
