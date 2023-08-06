package nl.chimpgamer.luckyvotes.commands

import cloud.commandframework.bukkit.CloudBukkitCapabilities
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator
import cloud.commandframework.paper.PaperCommandManager
import nl.chimpgamer.luckyvotes.LuckyVotesPlugin
import org.bukkit.command.CommandSender
import java.lang.Exception
import java.util.function.Function
import java.util.logging.Level


class CloudCommandManager(private val plugin: LuckyVotesPlugin) {
    private lateinit var paperCommandManager: PaperCommandManager<CommandSender>

    fun initialize() {
        val executionCoordinatorFunction = AsynchronousCommandExecutionCoordinator.builder<CommandSender>().build()

        try {
            paperCommandManager = PaperCommandManager(
                plugin,
                executionCoordinatorFunction,
                Function.identity(),
                Function.identity()
            )

            if (paperCommandManager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
                paperCommandManager.registerBrigadier()
                val brigadierManager = paperCommandManager.brigadierManager()
                brigadierManager?.setNativeNumberSuggestions(false)
            }
            if (paperCommandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
                paperCommandManager.registerAsynchronousCompletions()
            }
        } catch (ex: Exception) {
            plugin.logger.log(Level.SEVERE, "Failed to initialize the command manager", ex)
        }
    }

    fun loadCommands() {
        LuckyVotesCommand(plugin).registerCommands(paperCommandManager, "luckyvotes")
    }
}