package com.xitadoptus.fpsculler

import com.google.gson.GsonBuilder
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.round

data class FpsSettings(
    var hideSpawnerDisplayMob: Boolean = true,
    var hideParticles: Boolean = false,
    var hideDroppedItems: Boolean = false,
    var droppedItemScale: Float = 0.10f
)

object FpsSettingsStore {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val file: Path = FabricLoader.getInstance().configDir.resolve("fpsculler.json")

    @Volatile
    var settings: FpsSettings = FpsSettings()
        private set

    fun load() {
        settings = runCatching {
            if (Files.exists(file)) Files.newBufferedReader(file).use { gson.fromJson(it, FpsSettings::class.java) } else FpsSettings()
        }.getOrElse { FpsSettings() }
        save()
    }

    fun save() {
        Files.createDirectories(file.parent)
        Files.newBufferedWriter(file).use { gson.toJson(settings, it) }
    }

    fun changeScale(delta: Float) {
        settings.droppedItemScale = (round((settings.droppedItemScale + delta) * 100f) / 100f).coerceIn(0.05f, 1f)
        save()
    }
}
