package nl.chimpgamer.luckyvotes.configurations

import dev.dejvokep.boostedyaml.YamlDocument
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings
import nl.chimpgamer.luckyvotes.LuckyVotesPlugin
import nl.chimpgamer.luckyvotes.models.Reward

class SettingsConfig(private val plugin: LuckyVotesPlugin) {
    val config: YamlDocument

    val rewards: List<Reward> get() {
        val section = config.getSection("rewards")
        return section.keys.mapNotNull {
            Reward.deserialize(it.toString(), section.getSection(it.toString()).getStringRouteMappedValues(false))
        }
    }

    val luckyAnnouncement: String get() = config.getString("lucky_announcement")

    init {
        val file = plugin.dataFolder.resolve("settings.yml")
        val inputStream = plugin.getResource("settings.yml")
        val loaderSettings = LoaderSettings.builder().setAutoUpdate(true).build()
        val updaterSettings = UpdaterSettings.builder().setVersioning(BasicVersioning("config-version")).build()
        config = if (inputStream != null) {
            YamlDocument.create(file, inputStream, GeneralSettings.DEFAULT, loaderSettings, DumperSettings.DEFAULT, updaterSettings)
        } else {
            YamlDocument.create(file, GeneralSettings.DEFAULT, loaderSettings, DumperSettings.DEFAULT, updaterSettings)
        }
    }
}