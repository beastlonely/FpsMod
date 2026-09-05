package com.xitadoptus.fpsculler.gui

import com.xitadoptus.fpsculler.FpsSettingsStore
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

class FpsCullerScreen(private val previous: Screen?) : Screen(Component.literal("FPS Culler")) {
    override fun init() {
        val left = width / 2 - 118
        val top = height / 2 - 74
        addRenderableWidget(toggleButton(left, top, "Mob dentro do spawner") {
            FpsSettingsStore.settings.hideSpawnerDisplayMob = !FpsSettingsStore.settings.hideSpawnerDisplayMob
        })
        addRenderableWidget(toggleButton(left, top + 26, "Partículas") {
            FpsSettingsStore.settings.hideParticles = !FpsSettingsStore.settings.hideParticles
        })
        addRenderableWidget(toggleButton(left, top + 52, "Itens dropados") {
            FpsSettingsStore.settings.hideDroppedItems = !FpsSettingsStore.settings.hideDroppedItems
        })
        addRenderableWidget(Button.builder(Component.literal("Escala -")) { FpsSettingsStore.changeScale(-0.05f); rebuildWidgets() }
            .bounds(left, top + 86, 114, 20).build())
        addRenderableWidget(Button.builder(Component.literal("Escala +")) { FpsSettingsStore.changeScale(0.05f); rebuildWidgets() }
            .bounds(left + 122, top + 86, 114, 20).build())
        addRenderableWidget(Button.builder(Component.literal("Voltar")) { onClose() }
            .bounds(left, top + 120, 236, 20).build())
    }

    private fun toggleButton(x: Int, y: Int, label: String, action: () -> Unit): Button =
        Button.builder(Component.literal("$label: ${if (enabledFor(label)) "Ligado" else "Desligado"}")) {
            action(); FpsSettingsStore.save(); rebuildWidgets()
        }.bounds(x, y, 236, 20).build()

    private fun enabledFor(label: String) = when (label) {
        "Mob dentro do spawner" -> FpsSettingsStore.settings.hideSpawnerDisplayMob
        "Partículas" -> FpsSettingsStore.settings.hideParticles
        else -> FpsSettingsStore.settings.hideDroppedItems
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(graphics, mouseX, mouseY, partialTick)
        val center = width / 2
        val top = height / 2 - 102
        graphics.drawCenteredString(font, "FPS Culler", center, top, 0xFFFFFF)
        graphics.drawCenteredString(font, "Apenas visual: não altera o servidor.", center, top + 14, 0xA9B4C4)
        graphics.drawCenteredString(font, "Escala dos drops: ${"%.2f".format(java.util.Locale.ROOT, FpsSettingsStore.settings.droppedItemScale)}", center, top + 156, 0xFFFFFF)
    }

    override fun onClose() { minecraft?.setScreen(previous) }
}
