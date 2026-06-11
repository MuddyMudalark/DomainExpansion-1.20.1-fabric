package muddy.domain.client.entity;

import muddy.domain.DomainExpansion;
import muddy.domain.entity.custom.DomainEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DomainEntityRenderer extends EntityRenderer<DomainEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(DomainExpansion.MOD_ID, "textures/entity/domain_entity.png");

    public DomainEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DomainEntity entity) {
        return TEXTURE;
    }
}
