package me.vaxry.harakiri.impl.fml.core;

import me.vaxry.harakiri.Harakiri;
import me.vaxry.harakiri.framework.patch.ClassPatch;
import me.vaxry.harakiri.framework.patch.MethodPatch;
import me.vaxry.harakiri.framework.util.ASMUtil;
import me.vaxry.harakiri.impl.management.PatchManager;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * Author Seth
 * 4/4/2019 @ 11:49 PM.
 */

/*
public final class HarakiriClassTransformer implements IClassTransformer {
    public static PatchManager PATCH_MANAGER = null;

    /**
     * Every time a class is loaded we can intercept it and modify the bytecode
     *
     * @param name
     * @param transformedName
     * @param basicClass
     * @return
     *//*
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        if (PATCH_MANAGER == null)
            return basicClass;

        try {
            //find a patch based on the class name
            final ClassPatch patch = PATCH_MANAGER.findClassPatch(name);

            if (patch != null) {
                final ClassNode classNode = ASMUtil.getNode(basicClass);

                if (classNode != null) {

                    if (patch.isDebug()) {
                        Harakiri.get().getLogger().log(Level.INFO, "Methods for class " + classNode.name);
                        for (FieldNode fieldNode : classNode.fields) {
                            Harakiri.get().getLogger().log(Level.INFO, "Field " + fieldNode.access + " " + fieldNode.name + " " + fieldNode.desc);
                        }
                        for (MethodNode method : classNode.methods) {
                            Harakiri.get().getLogger().log(Level.INFO, "Method " + method.access + " " + method.name + " " + method.desc);
                        }
                    }

                    if (patch.getAccessPatch() != null) {

                        final InputStream stream = this.getClass().getResourceAsStream("/" + patch.getAccessPatch().getFile());

                        if (stream != null) {
                            Harakiri.get().getLogger().log(Level.INFO, "Access transformer found " + patch.getAccessPatch().getFile());

                            String line = "";
                            final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                            while ((line = reader.readLine()) != null) {
                                final String[] split = line.split(" ");

                                for (FieldNode field : classNode.fields) {
                                    if (field.name.equals(split[0]) && field.desc.equals(split[1])) {
                                        field.access = 1;

                                        if (patch.isDebug()) {
                                            Harakiri.get().getLogger().log(Level.INFO, "Changed access modifier for field " + field.name);
                                        }
                                    }
                                }

                                for (MethodNode method : classNode.methods) {
                                    if (method.name.equals(split[0]) && split.length > 0) {
                                        if (split[1] != null && method.desc.equals(split[1])) {
                                            method.access = 1;

                                            if (patch.isDebug()) {
                                                Harakiri.get().getLogger().log(Level.INFO, "Changed access modifier for method " + method.name + " " + method.desc);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }


                    //loop though methods in our patch class
                    for (Method method : patch.getClass().getDeclaredMethods()) {
                        //check if the method contains our @MethodPatch annotation
                        //TODO make sure params are MethodNode, Environment
                        if (method.isAnnotationPresent(MethodPatch.class) && method.getParameterCount() > 0) {
                            //get MethodPatch annotation from the method
                            final MethodPatch methodPatch = method.getAnnotation(MethodPatch.class);

                            if (methodPatch != null) {
                                String methodName = methodPatch.mcpName();
                                String methodDesc = methodPatch.mcpDesc();

                                //if we are in obfuscated environment we must use NOTCH mappings
                                if (PATCH_MANAGER.getEnv() == PatchManager.Environment.RELEASE) {
                                    if (methodPatch.notchName().length() > 0) {
                                        methodName = methodPatch.notchName();
                                    }

                                    if (methodPatch.notchDesc().length() > 0) {
                                        methodDesc = methodPatch.notchDesc();
                                    }
                                }

                                //find a method matching our mapping in the class currently being loaded
                                final MethodNode methodNode = ASMUtil.findMethod(classNode, methodName, methodDesc);

                                if (methodNode != null) {
                                    //once we have found a matching MethodNode
                                    //we invoke our patch method and pass the MethodNode into the params

                                    //if our patch method is private, allow us to access it
                                    if (!method.isAccessible()) {
                                        method.setAccessible(true);
                                    }

                                    //invoke our patch method
                                    method.invoke(patch, methodNode, PATCH_MANAGER.getEnv());

                                    if (PATCH_MANAGER.getEnv() == PatchManager.Environment.IDE) {
                                        Harakiri.get().getLogger().log(Level.INFO, "Patched " + patch.getMcpName().replace(".", "/") + "." + methodPatch.mcpName());
                                    }
                                }
                            }
                        }
                    }

                    //once we are done, we convert the modified bytecode to a byte array
                    return ASMUtil.toBytes(classNode);
                }
            }
        } catch (Throwable e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            //Harakiri.get().logChat("StorageESP Threw an Error: " + errors.toString());
            JOptionPane.showMessageDialog(null, errors.toString(), "Error in init()!", JOptionPane.INFORMATION_MESSAGE);
        }
        return basicClass;
    }

}
*/