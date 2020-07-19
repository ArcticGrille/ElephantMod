package com.github.arcticgrille.common.entity;

import com.github.arcticgrille.ElephantMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.IntRange;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.UUID;


// TODO: Make them carpetable!
// fuck you intellij carpetable is not a typo
// TODO: Also give them a chest inventory ??? maybe?
public class ElephantEntity extends HorseBaseEntity implements Angerable
{
	private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.HAY_BLOCK); // This is not necessarily the permanent breeding ingredient -Jolkert 2020-07-15
	
	private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
	private int angerTime;
	private UUID targetUuid;
	
	public ElephantEntity(EntityType<? extends HorseBaseEntity> entityType, World world)
	{
		super(entityType, world);
	}
	
	
	@Override
	protected void initGoals()
	{
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new HorseBondWithPlayerGoal(this, 1.2D));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(3, new TemptGoal(this, 1.25D, BREEDING_INGREDIENT, false));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(7, new LookAroundGoal(this));
		
		// IntelliJ please shut up I will not give in to your demands
		this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge());
		this.targetSelector.add(2, new MeleeAttackGoal(this, 3.0d, true)); // careful with that speed pardner -Jolkert 2020-07-16
		this.targetSelector.add(3, new FollowTargetGoal(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
		this.targetSelector.add(4, new UniversalAngerGoal(this, true));
	}
	
	// Overrides for breeding
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
	
	// Overrides for horse-like behavior
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand)
	{
		ItemStack itemStack = player.getStackInHand(hand);
		if(!this.isBaby())
		{
			if(this.isTame() && player.shouldCancelInteraction())
			{// If the elephant is tame, and the player is sneaking, open the inventory (is if statement actually really confusing, or am I just stupid at 4am? -Jolkert 2020-07-19)
				this.openInventory(player);
				return ActionResult.success(this.world.isClient);
			}
			
			if(this.hasPassengers())
				return super.interactMob(player, hand);
		}
		if(!itemStack.isEmpty())
		{
			if(this.isBreedingItem(itemStack))
				return this.method_30009(player, itemStack); // Not entirely sure what method_30009 is. Pretty sure it's just a GP feed method? -Jolkert 2020-07-17
			
			ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
			if(actionResult.isAccepted())
				return actionResult;
			
			if(!this.isTame())
			{
				this.playAngrySound();
				return ActionResult.success(this.world.isClient);
			}
			
			boolean shouldEquipSaddle = !this.isBaby() && !this.isSaddled() && itemStack.getItem() == Items.SADDLE;
			if(this.canEquip(itemStack) || shouldEquipSaddle)
			{
				this.openInventory(player);
				return ActionResult.success(this.world.isClient);
			}
		}
		
		if(this.isBaby())
			return super.interactMob(player, hand);
		else
		{
			this.putPlayerOnBack(player);
			return ActionResult.success(this.world.isClient);
		}
	}
	
	// Overrides from Angerable
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
	
	// ??????
	/*
	 * lambda machine broke
	 * Actually tho I cannot understand for the life of me why tf I need to do this
	 * It literally just casts obj as a LivingEntity and calls the super method
	 * This makes no damn sense but we need it
	 * -Jolkert 2020-07-16
	 */
	public boolean shouldAngerAt(Object obj)
	{
		return Angerable.super.shouldAngerAt((LivingEntity)obj);
	}
}
