package net.xanthian.camera_keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class CameraKeyBinds implements ClientModInitializer {
	public static final String MOD_ID = "camera_keybinds";
    public static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "keybinds"));

    private static CameraType lastPerspective = CameraType.FIRST_PERSON;

    private static KeyMapping setPerspectiveFirstPerson;
    private static KeyMapping setPerspectiveThirdPerson;
    private static KeyMapping setPerspectiveThirdPersonReverse;

    private static KeyMapping switchPerspectiveFirstPersonAndLast;
    private static KeyMapping switchPerspectiveThirdPersonAndLast;
    private static KeyMapping switchPerspectiveThirdPersonReverseAndLast;

    private static KeyMapping holdPerspectiveFirstPerson;
    private static KeyMapping holdPerspectiveThirdPerson;
    private static KeyMapping holdPerspectiveThirdPersonReverse;

    private static boolean heldPerspectiveFirstPerson = false;
    private static boolean heldPerspectiveThirdPerson = false;
    private static boolean heldPerspectiveThirdPersonReverse = false;

    private boolean holdPerspective(Minecraft client, KeyMapping key, CameraType perspective, boolean keyHeld) {
        if(!keyHeld && key.isDown()) {
            lastPerspective = client.options.getCameraType();
            client.options.setCameraType(perspective);
            keyHeld = true;
        }
        if(keyHeld && !key.isDown()) {
            CameraType currentPerspective = client.options.getCameraType();
            client.options.setCameraType(lastPerspective);
            lastPerspective = currentPerspective;
            keyHeld = false;
        }
        return keyHeld;
    }

    @Override
    public void onInitializeClient() {
        setPerspectiveFirstPerson = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.camera_keybinds.first_person_camera",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                KEY_CATEGORY));
        setPerspectiveThirdPerson = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.camera_keybinds.third_person_camera",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY));
        setPerspectiveThirdPersonReverse = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.camera_keybinds.third_person_reverse_camera",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                KEY_CATEGORY
        ));
        switchPerspectiveFirstPersonAndLast = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.camera_keybinds.switch_first_person_camera",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                KEY_CATEGORY
        ));
        switchPerspectiveThirdPersonAndLast = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.camera_keybinds.switch_third_person_camera",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                KEY_CATEGORY
        ));
        switchPerspectiveThirdPersonReverseAndLast = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.camera_keybinds.switch_third_person_reverse_camera",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                KEY_CATEGORY
        ));
        holdPerspectiveFirstPerson = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.camera_keybinds.hold_first_person_camera",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                KEY_CATEGORY
        ));
        holdPerspectiveThirdPerson = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.camera_keybinds.hold_third_person_camera",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                KEY_CATEGORY
        ));
        holdPerspectiveThirdPersonReverse = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.camera_keybinds.hold_third_person_reverse_camera",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                KEY_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(setPerspectiveFirstPerson.isDown()) {
                client.options.setCameraType(CameraType.FIRST_PERSON);
            }
            if(setPerspectiveThirdPerson.isDown()) {
                client.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            }
            if(setPerspectiveThirdPersonReverse.isDown()) {
                client.options.setCameraType(CameraType.THIRD_PERSON_FRONT);
            }

            if(switchPerspectiveFirstPersonAndLast.isDown()) {
                CameraType perspective = client.options.getCameraType();
                if (perspective != CameraType.FIRST_PERSON) {
                    lastPerspective = perspective;
                    client.options.setCameraType(CameraType.FIRST_PERSON);
                } else {
                    client.options.setCameraType(lastPerspective);
                }
            }
            if(switchPerspectiveThirdPersonAndLast.isDown()) {
                CameraType perspective = client.options.getCameraType();
                if (perspective != CameraType.THIRD_PERSON_BACK) {
                    lastPerspective = perspective;
                    client.options.setCameraType(CameraType.THIRD_PERSON_BACK);
                } else {
                    client.options.setCameraType(lastPerspective);
                }
            }
            if(switchPerspectiveThirdPersonReverseAndLast.isDown()) {
                CameraType perspective = client.options.getCameraType();
                if (perspective != CameraType.THIRD_PERSON_FRONT) {
                    lastPerspective = perspective;
                    client.options.setCameraType(CameraType.THIRD_PERSON_FRONT);
                } else {
                    client.options.setCameraType(lastPerspective);
                }
            }
            heldPerspectiveFirstPerson = holdPerspective(client, holdPerspectiveFirstPerson, CameraType.FIRST_PERSON, heldPerspectiveFirstPerson);
            heldPerspectiveThirdPerson = holdPerspective(client, holdPerspectiveThirdPerson, CameraType.THIRD_PERSON_BACK, heldPerspectiveThirdPerson);
            heldPerspectiveThirdPersonReverse = holdPerspective(client, holdPerspectiveThirdPersonReverse, CameraType.THIRD_PERSON_FRONT, heldPerspectiveThirdPersonReverse);
        });
    }
}