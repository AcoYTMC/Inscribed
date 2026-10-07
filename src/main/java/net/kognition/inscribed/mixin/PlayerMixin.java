package net.kognition.inscribed.mixin;

import net.kognition.inscribed.api.menu.TickableMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author AcoYT
 */
@Mixin(Player.class)
public abstract class PlayerMixin {
    @Shadow public AbstractContainerMenu containerMenu;

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Avatar;tick()V"
            )
    )
    private void inscribed$tickMenus(CallbackInfo ci) {
        if (this.containerMenu instanceof TickableMenu menu) {
            menu.tick((Player)(Object)this);
        }
    }
}
