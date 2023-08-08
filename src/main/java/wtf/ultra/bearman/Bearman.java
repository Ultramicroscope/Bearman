package wtf.ultra.bearman;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.event.*;
import org.lwjgl.input.Keyboard;

public class Bearman implements ModInitializer {
    private int forwardKeyCode, forwardState;

    @Override
    public void preInit() {
        EventBus.subscribe(KeyboardEvent.class, event -> {
            if (Keyboard.getEventKey() == Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode() && !Keyboard.getEventKeyState()) {
                forwardKeyCode = Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode();
                KeyBinding.setKeyBindState(forwardKeyCode, false);
                forwardState = 1;
            }
        });
        EventBus.subscribe(TickEvent.class, event -> {
            if (forwardState == 2) {
                forwardState = 0;
                if (Keyboard.isKeyDown(forwardKeyCode)) {
                    KeyBinding.setKeyBindState(forwardKeyCode, true);
                }
            } else if (forwardState == 1) forwardState = 2;
        });
        EventBus.subscribe(GuiOpenEvent.class, event -> {
            if (forwardState > 0) event.setCancelled(true);
        });
    }
}