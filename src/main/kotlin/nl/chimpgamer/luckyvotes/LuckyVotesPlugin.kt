package nl.chimpgamer.luckyvotes

import nl.chimpgamer.luckyvotes.commands.CloudCommandManager
import nl.chimpgamer.luckyvotes.configurations.SettingsConfig
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException
import java.nio.file.Files
import java.util.logging.Level

class LuckyVotesPlugin : JavaPlugin() {

    private val cloudCommandManager = CloudCommandManager(this)

    val settingsConfig = SettingsConfig(this)

    override fun onLoad() {
        // Make sure that the LuckyVotes folder exists.
        try {
            val dataFolderPath = dataFolder.toPath()
            if (!Files.isDirectory(dataFolderPath)) {
                Files.createDirectories(dataFolderPath)
            }
        } catch (ex: IOException) {
            logger.log(Level.SEVERE, "Unable to create plugin directory", ex)
        }
    }

    override fun onEnable() {
        cloudCommandManager.initialize()
        cloudCommandManager.loadCommands()
    }

    override fun onDisable() {

    }
}