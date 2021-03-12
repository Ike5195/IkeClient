package me.vaxry.harakiri.impl.patch;

import me.vaxry.harakiri.Harakiri;
import me.vaxry.harakiri.framework.event.world.EventCollideSoulSand;
import me.vaxry.harakiri.framework.patch.ClassPatch;
import me.vaxry.harakiri.framework.patch.MethodPatch;
import me.vaxry.harakiri.impl.management.PatchManager;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * Author Seth
 * 4/10/2019 @ 2:51 AM.
 */
public final class BlockSoulSandPatch extends ClassPatch {

    public BlockSoulSandPatch() {
        super("net.minecraft.block.BlockSoulSand", "atx");
    }

    /**
     * This is where minecraft slows you down when moving on soul sand
     *
     * @param methodNode
     * @param env
     */
    @MethodPatch(
            mcpName = "onEntityCollidedWithBlock",
            notchName = "a",
            mcpDesc = "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/Entity;)V",
            notchDesc = "(Lamu;Let;Lawt;Lvg;)V")
    public void onEntityCollidedWithBlock(MethodNode methodNode, PatchManager.Environment env) {
        //create a list of instructions and add the needed instructions to call our hook function
        final InsnList insnList = new InsnList();
        //call our hook function
        insnList.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(this.getClass()), "onEntityCollidedWithBlockHook", "()Z", false));
        //add a label to jump to
        final LabelNode jmp = new LabelNode();
        //add if equals and pass the label
        insnList.add(new JumpInsnNode(IFEQ, jmp));
        //add return so the rest of the function doesn't get called
        insnList.add(new InsnNode(RETURN));
        //add our label
        insnList.add(jmp);
        //insert the list of instructions at the top of the function
        methodNode.instructions.insert(insnList);
    }

    /**
     * Our onEntityCollidedWithBlock hook used to disable
     * the slowing of movement while on soul sand
     *
     * @return
     */
    public static boolean onEntityCollidedWithBlockHook() {
        final EventCollideSoulSand event = new EventCollideSoulSand();
        Harakiri.get().getEventManager().dispatchEvent(event);

        return event.isCanceled();
    }

}
