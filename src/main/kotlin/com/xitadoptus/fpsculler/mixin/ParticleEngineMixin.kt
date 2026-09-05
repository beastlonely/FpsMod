package com.xitadoptus.fpsculler.mixin

import com.xitadoptus.fpsculler.FpsSettingsStore
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleEngine
import net.minecraft.core.particles.ParticleOptions
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

/** Stops new client particles before they enter the renderer. */
@Mixin(ParticleEngine::class)
abstract class ParticleEngineMixin {
    @Inject(method = ["add"], at = [At("HEAD")], cancellable = true)
    private fun fpsculler_discardParticle(particle: Particle, callback: CallbackInfo) {
        if (FpsSettingsStore.settings.hideParticles) callback.cancel()
    }

    @Inject(method = ["createParticle"], at = [At("HEAD")], cancellable = true)
    private fun fpsculler_skipParticleCreation(
        options: ParticleOptions,
        x: Double,
        y: Double,
        z: Double,
        velocityX: Double,
        velocityY: Double,
        velocityZ: Double,
        callback: CallbackInfoReturnable<Particle?>
    ) {
        if (FpsSettingsStore.settings.hideParticles) callback.returnValue = null
    }
}
