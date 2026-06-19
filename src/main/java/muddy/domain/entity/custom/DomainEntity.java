package muddy.domain.entity.custom;

import muddy.domain.DomainExpansion;
import muddy.domain.util.DomainBlockBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DomainEntity extends LivingEntity {
    private final Map<BlockPos, BlockState> savedBlocks = new HashMap<>();

    CompoundTag hasSpawned = new CompoundTag();

    int ticksInBetweenExpansion = 0;
    int maxRadius = 15;
    int radius = 1;
    int yRadius = -15;

    // For making the domain disappear after a certain time.
    int age = 0;
    // 5 Seconds
    int lifetime = 100;

    boolean expandTick = true;

    public DomainEntity(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    @Override
    public @NotNull ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    public static AttributeSupplier.Builder createDomainAttributes() {
        return AttributeSupplier.builder().add(Attributes.MAX_HEALTH);
    }


    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            if (this.firstTick) {
                saveDomainBlocks();
            } else {
                if (this.expandTick) {
                    if (this.radius <= this.maxRadius) {
                        if (this.radius < 4) {
                            this.firstTicksDomainExpansion();
                        } else {
                            this.domainExpansion();
                        }

                        if (this.radius >= 13) {
                            yRadius+=3;
                        } else {
                            this.yRadius+=2;
                        }
                        this.radius++;
                        this.expandTick=false;
                    }
                } else if (this.radius > this.maxRadius) {
                    this.age++;

                    if (this.age >= this.lifetime || this.isDeadOrDying()) {
                        closeDomain();

                        this.remove(RemovalReason.DISCARDED);
                    }
                } else {
                    this.ticksInBetweenExpansion++;

                    if (this.ticksInBetweenExpansion >= 4) {
                        this.ticksInBetweenExpansion=0;

                        this.expandTick=true;
                    }
                }
            }
        }
        super.tick();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.GLASS_BREAK;
    }

    private void firstTicksDomainExpansion() {
        DomainBlockBuilder.buildHollowInside(this.level(), this.blockPosition(), this.radius);

        DomainBlockBuilder.buildStandingSurface(this.level(), this.blockPosition(), this.radius);
    }

    public void domainExpansion() {
        DomainBlockBuilder.buildHollowInside(this.level(), this.blockPosition(), this.radius);

        DomainBlockBuilder.buildStandingSurface(this.level(), this.blockPosition(), this.radius);
        DomainBlockBuilder.buildHollowSphereDynamically(this.level(), this.blockPosition(), this.radius, this.yRadius);
    }

    public void saveDomainBlocks() {
        int thatRadius = maxRadius + 1;

        for (int x = -thatRadius; x <= thatRadius; x++) {
            for (int y = -thatRadius; y <= thatRadius; y++) {
                for (int z = -thatRadius; z <= thatRadius; z++) {

                    int distSq = x * x + y * y + z * z;

                    if (distSq <= thatRadius * thatRadius) {
                        BlockPos pos = this.blockPosition().offset(x, y, z);

                        savedBlocks.put(pos.immutable(), this.level().getBlockState(pos));
                    }
                }
            }
        }
    }

    public void closeDomain() {
        for (Map.Entry<BlockPos, BlockState> entry : savedBlocks.entrySet()) {
            BlockPos savedBlockPos = entry.getKey();
            BlockState oldState = entry.getValue();

            this.level().setBlockAndUpdate(savedBlockPos, oldState);
        }

    }

    @Override
    protected @NotNull AABB makeBoundingBox() {
        return AABB.ofSize(Vec3.ZERO, 0, 0, 0);
    }

    @Override
    public void setNoGravity(boolean bl) {
        bl = true;

        super.setNoGravity(true);
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
