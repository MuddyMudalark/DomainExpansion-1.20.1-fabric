package muddy.domain.client.entity;

import muddy.domain.DomainExpansion;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation DOMAIN_ENTITY =
            new ModelLayerLocation(new ResourceLocation(DomainExpansion.MOD_ID, "domain_entity"), "main");


}
