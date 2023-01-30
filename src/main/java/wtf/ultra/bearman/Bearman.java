package wtf.ultra.bearman;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
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
    private int forwardState = 0;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKey() == mc.gameSettings.keyBindSprint.getKeyCode() && !Keyboard.getEventKeyState()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            forwardState = 1;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (forwardState == 2) {
            forwardState = 0;
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
            }
        } else if (forwardState == 1) forwardState = 2;
    }
}
