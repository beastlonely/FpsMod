package com.xitadoptus.fpsculler.mixin

import com.xitadoptus.fpsculler.FpsSettingsStore
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.blockentity.SpawnerRenderer
import net.minecraft.client.renderer.blockentity.state.SpawnerRenderState
import net.minecraft.client.renderer.state.CameraRenderState
import com.mojang.blaze3d.vertex.PoseStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/** Cancels only the display entity that rotates inside a spawner block. */
@Mixin(SpawnerRenderer::class)
abstract class SpawnerRendererMixin {
    @Inject(method = ["submit"], at = [At("HEAD")], cancellable = true)
    private fun fpsculler_hideSpawnerDisplay(
        state: SpawnerRenderState,
        poseStack: PoseStack,
        collector: SubmitNodeCollector,
        camera: CameraRenderState,
        callback: CallbackInfo
    ) {
        if (FpsSettingsStore.settings.hideSpawnerDisplayMob) callback.cancel()
    }
}
