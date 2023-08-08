package wtf.ultra.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static wtf.ultra.BearmanMod.state;

@Mixin({Keyboard.class})
public class KeyboardMixin {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long windowPointer, int key, int scanCode, int action,
                       int modifiers, CallbackInfo callbackInfo) {
        if (mc.currentScreen == null && action == 0
                && mc.options.forwardKey.isPressed()
                && mc.options.sprintKey.matchesKey(key, scanCode)) {
            mc.options.backKey.setPressed(true); state = true;
        }
    }
}
