package muddy.domain.util;

import muddy.domain.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;

public class DomainBlockBuilder {
    public static void buildStandingSurface(Level level, BlockPos centerPos, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {

                int distanceSquare = x * x + z * z;

                if (distanceSquare <= radius * radius) {
                    BlockPos pos = centerPos.offset(x, -1, z);

                    level.setBlockAndUpdate(pos, ModBlocks.DOMAIN_BARRIER_BLOCK.defaultBlockState());
                }
            }
        }
    }

    public static void buildHollowSphereDynamically(Level level, BlockPos centerPos, int radius, int yValue) {
        int outerSquare = radius * radius;
        int innerSquare = (radius - 1) * (radius - 1);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (y <= yValue) {
                    for (int z = -radius; z <= radius; z++) {

                        int distanceSquare = x * x + y * y + z * z;

                        if (distanceSquare <= outerSquare && distanceSquare >= innerSquare) {
                            BlockPos pos = centerPos.offset(x, y, z);

                            level.setBlockAndUpdate(pos, ModBlocks.DOMAIN_BARRIER_BLOCK.defaultBlockState());
                        }
                    }
                }
            }
        }

    }

    public static void buildHollowInside(Level level,BlockPos centerPos, int radius) {
        radius -= 1;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    int distanceSquare = x * x + y * y + z * z;

                    if (distanceSquare <= radius * radius) {
                        BlockPos pos = centerPos.offset(x, y, z);

                        level.setBlockAndUpdate(pos, ModBlocks.MUDDY_BRANDED_AIR_BLOCK.defaultBlockState());
                    }
                }
            }
        }

    }
}
