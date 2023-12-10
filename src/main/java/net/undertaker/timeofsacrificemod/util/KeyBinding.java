package net.undertaker.timeofsacrificemod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_TOS = "key.category.timeofsacrifice.tos_options";
    public static final String KEY_MODE_1 = "key.timeofsacrifice.mode_1";
    public static final KeyMapping MODE_1_KEY = new KeyMapping(KEY_MODE_1, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_TOS);
    public static final String KEY_MODE_2 = "key.timeofsacrifice.mode_2";
    public static final KeyMapping MODE_2_KEY = new KeyMapping(KEY_MODE_2, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORY_TOS);
    public static final String KEY_MODE_3 = "key.timeofsacrifice.mode_3";
    public static final KeyMapping MODE_3_KEY = new KeyMapping(KEY_MODE_3, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY_TOS);
    public static final String KEY_MODE_4 = "key.timeofsacrifice.mode_4";
    public static final KeyMapping MODE_4_KEY = new KeyMapping(KEY_MODE_4, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_TOS);
    public static final String KEY_MODE_5 = "key.timeofsacrifice.mode_5";
    public static final KeyMapping MODE_5_KEY = new KeyMapping(KEY_MODE_5, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY_TOS);
    public static final String KEY_DASH = "key.timeofsacrifice.dash";
    public static final KeyMapping DASH_KEY = new KeyMapping(KEY_DASH, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, KEY_CATEGORY_TOS);


}
