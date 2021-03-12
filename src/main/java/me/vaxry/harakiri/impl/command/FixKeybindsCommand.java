package me.vaxry.harakiri.impl.command;

import me.vaxry.harakiri.Harakiri;
import me.vaxry.harakiri.framework.command.Command;
import me.vaxry.harakiri.impl.module.hidden.KeybindsModule;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public final class FixKeybindsCommand extends Command {

    public FixKeybindsCommand() {
        super("FixKeybinds", new String[]{"fixkeybinds", "fixkey"}, "Fixes the keybinds if broken.", "fixkeybinds");
    }

    @Override
    public void exec(String input) {
        KeybindsModule keybindsModule = (KeybindsModule) Harakiri.get().getModuleManager().find(KeybindsModule.class);
        Harakiri.get().getEventManager().removeEventListener(keybindsModule);
        Harakiri.get().getEventManager().removeEventListener(keybindsModule);
        Harakiri.get().getEventManager().addEventListener(keybindsModule);
        keybindsModule.setEnabled(true);
    }
}