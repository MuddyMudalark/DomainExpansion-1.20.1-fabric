package muddy.domain.entity.custom;

import muddy.domain.DomainExpansion;
import muddy.domain.util.DomainBlockBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DomainEntity extends LivingEntity {
    List<Block> savedBlocks = List.of();

    CompoundTag hasSpawned = new CompoundTag();

    int ticksInBetweenExpansion = 0;
    int maxRadius = 15;
    int radius = 1;

    // For making the domain disappear after a certain time.
    int age = 0;
    // 15 Seconds
    int lifetime = 300;

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
        super.tick();
        if (!this.level().isClientSide) {
            if (this.firstTick) {
                this.domainSaveBlocks();
            } else {
                if (this.expandTick) {
                    if (this.radius <= maxRadius) {
                        if (this.radius < 4) {
                            this.firstTicksDomainExpansion();
                        } else {
                            this.domainExpansion();
                        }
                        this.radius++;
                        this.expandTick=false;
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
    }

    private void firstTicksDomainExpansion() {
        DomainBlockBuilder.buildHollowInside(this.level(), this.blockPosition(), this.radius);

        DomainBlockBuilder.buildStandingSurface(this.level(), this.blockPosition(), this.radius);
    }

    public void domainSaveBlocks() {
        DomainBlockBuilder.saveExistingBlocks(this.level(), this.blockPosition(), 15, this.savedBlocks);
    }



    public void domainExpansion() {
        DomainBlockBuilder.buildHollowInside(this.level(), this.blockPosition(), this.radius);

        DomainBlockBuilder.buildStandingSurface(this.level(), this.blockPosition(), this.radius);
        DomainBlockBuilder.buildHollowSphere(this.level(), this.blockPosition(), this.radius);
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
