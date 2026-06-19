package muddy.domain.block;

import muddy.domain.DomainExpansion;
import muddy.domain.block.custom.DomainAirBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;


public class ModBlocks {
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        ResourceLocation id = new ResourceLocation(DomainExpansion.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static final Block DOMAIN_BARRIER_BLOCK = register(
            new Block(BlockBehaviour.Properties.of()
                    .strength(-1.0F, 3600000.0F)
                    .lightLevel((blockState) -> 15)
                    .sound(SoundType.GLASS)),
            "domain_barrier",
            false
    );



    public static final Block MUDDY_BRANDED_AIR_BLOCK = register(
            new DomainAirBlock(BlockBehaviour.Properties.of()
                    .lightLevel((blockState) -> 15)
                    .air()),
            "domain_air",
            false);

    public static void initialize() {}
}


