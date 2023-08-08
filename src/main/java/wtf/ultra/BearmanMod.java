package wtf.ultra;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class BearmanMod implements ModInitializer {
    public static boolean state = false;

    @Override
    public void onInitialize() {
        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            if (state && mc.currentScreen == null) {
                mc.options.backKey.setPressed(false);
                state = false;
            }
        });
    }
}