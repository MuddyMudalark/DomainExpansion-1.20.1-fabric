package muddy.domain.entity;

import muddy.domain.DomainExpansion;
import muddy.domain.entity.custom.DomainEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final EntityType<DomainEntity> DOMAIN_ENTITY = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(DomainExpansion.MOD_ID, "domain_entity"),
            FabricEntityTypeBuilder.create(MobCategory.MISC, DomainEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());


}
