package com.xitadoptus.fpsculler.mixin

import com.mojang.blaze3d.vertex.PoseStack
import com.xitadoptus.fpsculler.FpsSettingsStore
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.ItemEntityRenderer
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState
import net.minecraft.client.renderer.state.CameraRenderState
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/** Hides drops completely or renders them at the configured client-side scale. */
@Mixin(ItemEntityRenderer::class)
abstract class ItemEntityRendererMixin {
    @Inject(method = ["submit"], at = [At("HEAD")], cancellable = true)
    private fun fpsculler_prepareDroppedItem(
        state: ItemEntityRenderState,
        poseStack: PoseStack,
        collector: SubmitNodeCollector,
        camera: CameraRenderState,
        callback: CallbackInfo
    ) {
        val settings = FpsSettingsStore.settings
        if (settings.hideDroppedItems) {
            callback.cancel()
            return
        }
        if (settings.droppedItemScale != 1f) {
            poseStack.pushPose()
            poseStack.scale(settings.droppedItemScale, settings.droppedItemScale, settings.droppedItemScale)
        }
    }

    @Inject(method = ["submit"], at = [At("RETURN")])
    private fun fpsculler_restoreDroppedItemPose(
        state: ItemEntityRenderState,
        poseStack: PoseStack,
        collector: SubmitNodeCollector,
        camera: CameraRenderState,
        callback: CallbackInfo
    ) {
        val settings = FpsSettingsStore.settings
        if (!settings.hideDroppedItems && settings.droppedItemScale != 1f) poseStack.popPose()
    }
}
