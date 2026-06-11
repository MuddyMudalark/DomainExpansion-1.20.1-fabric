package muddy.domain.item.custom;

import muddy.domain.entity.ModEntities;
import muddy.domain.entity.custom.DomainEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DomainItem extends Item {
    public DomainItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        DomainEntity domain = new DomainEntity(ModEntities.DOMAIN_ENTITY, level);

        domain.setPos(player.position());
        domain.setNoGravity(true);

        level.addFreshEntity(domain);

        return super.use(level, player, interactionHand);
    }
}
