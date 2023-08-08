package wtf.ultra.bearman;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = "bearman", version = "1.8.9")
public class Bearman {
    private final Minecraft mc = Minecraft.getMinecraft();
    private int forwardKeyCode, forwardState;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Registering event handlers to the Event Bus
        MinecraftForge.EVENT_BUS.register(this);
    }

    /** Listen to when the sprint key is released. Set forward key state to unpressed */
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        // If this input is the sprint key and that key is being released (key up).
        if (Keyboard.getEventKey() == mc.gameSettings.keyBindSprint.getKeyCode() && !Keyboard.getEventKeyState()) {
            forwardKeyCode = mc.gameSettings.keyBindForward.getKeyCode();
            KeyBinding.setKeyBindState(forwardKeyCode, false);
            forwardState = 1;
        }
    }

    /** After forward key was set to unpressed, wait one tick then repress. */
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Logic to wait a tick then reset.
        if (forwardState == 2) {
            forwardState = 0;
            // Set forward key state to pressed if it is held down.
            if (Keyboard.isKeyDown(forwardKeyCode)) {
                KeyBinding.setKeyBindState(forwardKeyCode, true);
            }
        } else if (forwardState == 1) forwardState = 2;
    }

    /** Stops player from opening a GUI while forward movement is being handled. This occurrence is detectable. */
    @SubscribeEvent public void onGuiOpen(GuiOpenEvent event) { if (forwardState > 0) event.setCanceled(true); }
}
