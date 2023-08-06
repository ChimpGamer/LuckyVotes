package nl.chimpgamer.luckyvotes

import nl.chimpgamer.luckyvotes.commands.CloudCommandManager
import nl.chimpgamer.luckyvotes.configurations.SettingsConfig
import nl.chimpgamer.luckyvotes.hooks.HooksManager
import nl.chimpgamer.luckyvotes.models.Reward
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException
import java.nio.file.Files
import java.util.logging.Level

class LuckyVotesPlugin : JavaPlugin() {

    private val cloudCommandManager = CloudCommandManager(this)

    val settingsConfig = SettingsConfig(this)
    val hooksManager = HooksManager(this)

    private var luckyRewards: List<Reward> = ArrayList()

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

        hooksManager.load()

        loadLuckyRewards()
    }

    override fun onDisable() {
        hooksManager.unload()
    }

    fun reloadConfigs() {
        settingsConfig.config.reload()
        loadLuckyRewards()
    }

    private fun loadLuckyRewards() {
        this.luckyRewards = settingsConfig.rewards
    }

    fun getLuckyReward(player: Player): Reward? {
        return luckyRewards.firstOrNull { it.hasPermission(player) && it.success() }
    }
}