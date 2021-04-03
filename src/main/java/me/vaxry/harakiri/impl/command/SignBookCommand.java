package me.vaxry.harakiri.impl.command;

import me.vaxry.harakiri.Harakiri;
import me.vaxry.harakiri.framework.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;

/**
 * Author Seth
 * 8/18/2019 @ 9:51 PM.
 */
public final class SignBookCommand extends Command {

    public SignBookCommand() {
        super("SignBook", new String[]{"SBook", "SignB"}, "Allows you to change the author of a signed book (creative)", "SignBook <Username>");
    }

    @Override
    public void run(String input) {
        if (!this.verifyInput(input, 2, 2)) {
            this.printUsage();
            return;
        }

        final Minecraft mc = Minecraft.getMinecraft();

        if (!mc.player.isCreative()) {
            Harakiri.get().errorChat("Creative mode is required to use this command.");
            return;
        }

        final ItemStack itemStack = mc.player.inventory.getCurrentItem();

        final String[] split = input.split(" ");

        if (itemStack.getItem() instanceof ItemWrittenBook) {
            final NBTTagCompound tagCompound = (itemStack.hasTagCompound()) ? itemStack.getTagCompound() : new NBTTagCompound();
            tagCompound.setTag("author", new NBTTagString(split[1]));
            mc.player.connection.sendPacket(new CPacketCreativeInventoryAction(36 + mc.player.inventory.currentItem, itemStack));
            Harakiri.get().logChat("Signed book with username " + split[1]);
        } else {
            Harakiri.get().errorChat("Please hold a signed book");
        }
    }
}
