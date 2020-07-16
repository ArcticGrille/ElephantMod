package com.github.arcticgrille.common.entity;

import com.github.arcticgrille.ElephantMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.IntRange;
import net.minecraft.world.World;

import java.util.UUID;

/* Ok so I think I fixed it they seem to be moving at a much more reasonable rate
 * I just added attributes other than defaults onto them
 * Check out ElephantMod.java to see what I'm on about
 * -Jolkert 2020-07-16
 */
// TODO: Make them carpetable!
// fuck you intellij carpetable is not a typo
// TODO: Also give them a chest inventory ??? maybe?
public class ElephantEntity extends AnimalEntity implements Angerable
{
	private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.HAY_BLOCK); // This is not necessarily the permanent breeding ingredient -Jolkert 2020-07-15
	private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
	
	private int angerTime;
	private UUID targetUuid;
	
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
		
		// FIXME: This doesn't work quite right? Not sure if this is even how this works or if I'm missing something
		//this.targetSelector.add(8, new UniversalAngerGoal(this, true));
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
	
	
	/*
	 * Overrides from Angerable
	 * Literally copied almost exactly from PolarBearEntity
	 * Hopefully this works correctly??
	 * -Jolkert 2020-07-16
	 */
	@Override
	public int getAngerTime()
	{
		return this.angerTime;
	}
	@Override
	public void setAngerTime(int ticks)
	{
		this.angerTime = ticks;
	}
	
	@Override
	public UUID getAngryAt()
	{
		return this.targetUuid;
	}
	@Override
	public void setAngryAt(UUID uuid)
	{
		this.targetUuid = uuid;
	}
	
	@Override
	public void chooseRandomAngerTime()
	{
		this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
	}
}
