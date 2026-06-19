package muddy.domain;

import jdk.jfr.Event;
import muddy.domain.block.ModBlocks;
import muddy.domain.entity.ModEntities;
import muddy.domain.entity.custom.DomainEntity;
import muddy.domain.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.mixin.object.builder.DefaultAttributeRegistryAccessor;
import net.minecraft.util.profiling.jfr.event.ServerTickTimeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomainExpansion implements ModInitializer {
	public static final String MOD_ID = "domain-expansion";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModBlocks.initialize();

		FabricDefaultAttributeRegistry.register(ModEntities.DOMAIN_ENTITY, DomainEntity.createDomainAttributes());

		LOGGER.info("Domain Expansion: Malevolent Codebase");
	}
}