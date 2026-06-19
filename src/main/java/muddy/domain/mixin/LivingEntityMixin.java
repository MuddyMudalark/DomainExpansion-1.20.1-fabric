package muddy.domain.mixin;

import muddy.domain.block.custom.DomainAirBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    public void domain$tick(CallbackInfo ci) {
        domain$inDomainAirBlock(((LivingEntity)(Object)this).level());
    }

    @Unique
    public void domain$inDomainAirBlock(Level level) {
        BlockPos entityBlockPos = ((LivingEntity)(Object)this).blockPosition();

        if (!((LivingEntity)(Object)this).hasEffect(MobEffects.REGENERATION)) {
            if (level.getBlockState(entityBlockPos).getBlock() instanceof DomainAirBlock) {
                ((LivingEntity)(Object)this).addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20));
            }
        }

    }
}
