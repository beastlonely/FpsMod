package com.xitadoptus.fpsculler

import com.mojang.blaze3d.platform.InputConstants
import com.xitadoptus.fpsculler.gui.FpsCullerScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.resources.Identifier
import org.lwjgl.glfw.GLFW

object FpsCullerMod : ClientModInitializer {
    const val MOD_ID = "fpsculler"
    private val openSettings = KeyMapping(
        "key.fpsculler.open_settings",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_F8,
        KeyMapping.Category(Identifier.fromNamespaceAndPath(MOD_ID, "keybindings"))
    )

    override fun onInitializeClient() {
        FpsSettingsStore.load()
        KeyBindingHelper.registerKeyBinding(openSettings)
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (openSettings.consumeClick()) {
                client.setScreen(FpsCullerScreen(client.screen))
            }
        }
    }
}
