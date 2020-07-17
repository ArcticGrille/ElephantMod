package com.github.arcticgrille;

import com.github.arcticgrille.common.entity.ElephantEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElephantMod implements ModInitializer {
    
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "elephantmod";
    public static final String MOD_NAME = "Elephant Mod";
    
    public static final EntityType<ElephantEntity> ELEPHANT_ENTITY = Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "elephant"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ElephantEntity::new).dimensions(EntityDimensions.fixed(.75f, .75f)).build());
    
    @Override
    public void onInitialize()
    {
        log(Level.INFO, "Initializing...");
    
        FabricDefaultAttributeRegistry.register(ELEPHANT_ENTITY, ElephantEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224d) // This is a cow's speed lol -Jolkert 2020-07-16
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0d)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0d) // We gonna make this higher later -Jolkert 2020-07-16
        );
    }

    public static void log(Level level, String message)
    {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

}