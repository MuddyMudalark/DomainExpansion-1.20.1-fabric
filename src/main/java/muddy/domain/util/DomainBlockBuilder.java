package muddy.domain.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class DomainBlockBuilder {
    
    public static void saveExistingBlocks(Level level, BlockPos centerPos, int radius, List<Block> blockList) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    int distanceSquare = x * x + y * y + z * z;

                    if (distanceSquare <= radius * radius) {
                        BlockPos pos = centerPos.offset(x, y, z);

                        blockList.add(level.getBlockState(pos).getBlock());
                    }
                }
            }
        }
    }

    public static void buildStandingSurface(Level level, BlockPos centerPos, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {

                int distanceSquare = x * x + z * z;

                if (distanceSquare <= radius * radius) {
                    BlockPos pos = centerPos.offset(x, -1, z);

                    level.setBlockAndUpdate(pos, Blocks.VERDANT_FROGLIGHT.defaultBlockState());
                }
            }
        }
    }

    public static void buildHollowSphere(Level level,BlockPos centerPos, int radius) {
        int outerSquare = radius * radius;
        int innerSquare = (radius - 1) * (radius - 1);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    int distanceSquare = x * x + y * y + z * z;

                    if (distanceSquare <= outerSquare && distanceSquare >= innerSquare) {
                        BlockPos pos = centerPos.offset(x, y, z);

                        level.setBlockAndUpdate(pos, Blocks.VERDANT_FROGLIGHT.defaultBlockState());
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

                        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }
    }
}
