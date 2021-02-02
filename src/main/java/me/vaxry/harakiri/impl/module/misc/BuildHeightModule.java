package me.vaxry.harakiri.impl.module.misc;

import me.vaxry.harakiri.api.event.EventStageable;
import me.vaxry.harakiri.api.event.network.EventSendPacket;
import me.vaxry.harakiri.api.module.Module;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

/**
 * Author Seth
 * 8/13/2019 @ 11:58 PM.
 */
public final class BuildHeightModule extends Module {

    public BuildHeightModule() {
        super("MaxHeight", new String[]{"BuildH", "BHeight"}, "Allows you to interact with blocks at y=255.", "NONE", -1, ModuleType.MISC);
    }

    @Listener
    public void sendPacket(EventSendPacket event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock) event.getPacket();
                if (packet.getPos().getY() >= 255 && packet.getDirection() == EnumFacing.UP) {
                    packet.placedBlockDirection = EnumFacing.DOWN;
                }
            }
        }
    }

}
