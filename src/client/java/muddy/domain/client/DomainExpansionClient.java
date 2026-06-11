package muddy.domain.client;

import muddy.domain.client.entity.DomainEntityRenderer;
import muddy.domain.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DomainExpansionClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.DOMAIN_ENTITY, DomainEntityRenderer::new);
	}
}