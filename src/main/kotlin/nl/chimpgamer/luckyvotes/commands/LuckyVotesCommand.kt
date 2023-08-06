package nl.chimpgamer.luckyvotes.commands

import cloud.commandframework.CommandManager
import nl.chimpgamer.luckyvotes.LuckyVotesPlugin
import org.bukkit.command.CommandSender

class LuckyVotesCommand(private val plugin: LuckyVotesPlugin) {

    fun registerCommands(commandManager: CommandManager<CommandSender>, name: String, vararg aliases: String) {
        val basePermission = "luckyvotes.command.luckyvotes"

        val builder = commandManager.commandBuilder(name, *aliases)
            .permission(basePermission)

        commandManager.command(builder
            .literal("reload")
            .permission("$basePermission.reload")
            .handler { context ->
                plugin.reloadConfigs()
                context.sender.sendRichMessage("<green>Successfully reloaded the settings!")
            }
        )
    }
}