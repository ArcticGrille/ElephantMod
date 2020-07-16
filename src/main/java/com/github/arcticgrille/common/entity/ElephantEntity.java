package com.github.arcticgrille.common.entity;

import com.github.arcticgrille.ElephantMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;

// FIXME: These boys are way too fast please someone help!!
public class ElephantEntity extends AnimalEntity
{
	private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.HAY_BLOCK); // This is not necessarily the permanent breeding ingredient -Jolkert 2020-07-15
	
	public ElephantEntity(EntityType<? extends AnimalEntity> entityType, World world)
	{
		super(entityType, world);
	}
	
	/*
	 * The body of initGoals() is mostly just copy-pasted from CowEntity so it might have to change later
	 * Though it seems to be standard for all animals to have this
	 * This might just be the permanent solution lol
	 * -Jolkert 2020-07-15
	 */

	//haha funny test
	@Override
	protected void initGoals()
	{
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0D));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(3, new TemptGoal(this, 1.25D, BREEDING_INGREDIENT, false));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(7, new LookAroundGoal(this));
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack)
	{
		return BREEDING_INGREDIENT.test(stack);
	}
	
	@Override
	public ElephantEntity createChild(PassiveEntity mate)
	{
		return ElephantMod.ELEPHANT_ENTITY.create(this.world);
	}
}
