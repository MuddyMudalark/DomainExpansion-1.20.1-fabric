package muddy.domain.item;

import muddy.domain.DomainExpansion;
import muddy.domain.item.custom.DomainItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {
    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        ResourceLocation itemID = new ResourceLocation(DomainExpansion.MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(BuiltInRegistries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }

    public static final Item DOMAIN_TEST = register(
            new DomainItem(new FabricItemSettings()), "domain_test_item"
    );

    public static void initialize() {

    }
}
