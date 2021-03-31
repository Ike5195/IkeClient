package me.vaxry.harakiri.impl.gui.hud.component;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.vaxry.harakiri.Harakiri;
import me.vaxry.harakiri.framework.gui.hud.component.DraggableHudComponent;
import me.vaxry.harakiri.framework.util.RenderUtil;
import me.vaxry.harakiri.framework.util.Timer;
import me.vaxry.harakiri.impl.module.combat.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;

import javax.vecmath.Vector3f;
import java.text.DecimalFormat;


public final class CombatInfoComponent extends DraggableHudComponent {

    private final int MODULES_DISPLAYED = 5;

    public CombatInfoComponent() {
        super("CombatInfo");
        this.setH(MODULES_DISPLAYED * Harakiri.get().getTTFFontUtil().FONT_HEIGHT);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getW(), this.getH() + this.getY(), 0x44000000);

        String finalDrawString = "";

        finalDrawString += ChatFormatting.GRAY + "CA: ";
        finalDrawString += Harakiri.get().getModuleManager().find(CrystalAuraModule.class).isEnabled() ?
                ChatFormatting.GREEN + "ON" :
                ChatFormatting.RED + "OFF";

        finalDrawString += ChatFormatting.RESET + " \n";

        finalDrawString += ChatFormatting.GRAY + "KA: ";
        finalDrawString += Harakiri.get().getModuleManager().find(KillAuraModule.class).isEnabled() ?
                ChatFormatting.GREEN + "ON" :
                ChatFormatting.RED + "OFF";

        finalDrawString += ChatFormatting.RESET + " \n";

        finalDrawString += ChatFormatting.GRAY + "ObsRepl: ";
        finalDrawString += Harakiri.get().getModuleManager().find(ObsidianReplaceModule.class).isEnabled() ?
                ChatFormatting.GREEN + "ON" :
                ChatFormatting.RED + "OFF";

        finalDrawString += ChatFormatting.RESET + " \n";

        finalDrawString += ChatFormatting.GRAY + "NoCrys: ";
        finalDrawString += Harakiri.get().getModuleManager().find(NoCrystalModule.class).isEnabled() ?
                ChatFormatting.GREEN + "ON" :
                ChatFormatting.RED + "OFF";

        finalDrawString += ChatFormatting.RESET + " \n";

        finalDrawString += ChatFormatting.GRAY + "AT: ";
        finalDrawString += ((AutoTotemModule)Harakiri.get().getModuleManager().find(AutoTotemModule.class)).getOverrideStatus() ?
                ChatFormatting.GREEN + "ON" :
                ChatFormatting.RED + "OFF";

        float w = 0;
        for(String s : finalDrawString.split("\n")){
            if(Harakiri.get().getTTFFontUtil().getStringWidth(s) > w)
                w = Harakiri.get().getTTFFontUtil().getStringWidth(s);
        }

        this.setW(w);
        Harakiri.get().getTTFFontUtil().drawStringWithShadow(finalDrawString, this.getX(), this.getY(), -1);
    }

}