package me.vaxry.harakiri.impl.module.hidden;

import me.vaxry.harakiri.Harakiri;
import me.vaxry.harakiri.api.event.minecraft.EventKeyPress;
import me.vaxry.harakiri.api.macro.Macro;
import me.vaxry.harakiri.api.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 5/7/2019 @ 9:49 PM.
 */
public final class MacroModule extends Module {

    public MacroModule() {
        super("Macros", new String[]{"mac"}, "Allows you to bind macros to keys", "NONE", -1, ModuleType.HIDDEN);
        this.setHidden(true);
        this.setEnabled(true);
        this.onEnable();
    }

    @Listener
    public void keyPress(EventKeyPress event) {
        for (Macro macro : Harakiri.INSTANCE.getMacroManager().getMacroList()) {
            if (event.getKey() == Keyboard.getKeyIndex(macro.getKey()) && Keyboard.getKeyIndex(macro.getKey()) != Keyboard.KEY_NONE) {
                final String[] split = macro.getMacro().split(";");

                for (String s : split) {
                    Minecraft.getMinecraft().player.sendChatMessage(s);
                }
            }
        }
    }

}
