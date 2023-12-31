package nl.chimpgamer.luckyvotes.models

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import kotlin.random.Random

class Reward(
    val id: String,
    val chance: Int,
    val permission: String? = null,
    val commands: List<String> = emptyList(),
    val message: String? = null
) {
    fun success(): Boolean = if (chance > 0) chance >= Random.nextInt(100) else true

    fun hasPermission(player: Player) = permission == null || player.hasPermission(permission)

    fun executeCommands(player: Player) {
        commands.forEach { command ->
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.name))
        }
    }

    companion object {
        fun deserialize(id: String, map: Map<String, Any>): Reward {
            var chance = 0
            var permission: String? = null
            var commands = emptyList<String>()
            var message: String? = null

            if (map.containsKey("chance")) {
                chance = map["chance"].toString().toIntOrNull() ?: 0
            }
            if (map.containsKey("permission")) {
                permission = map["permission"]?.toString()
            }
            if (map.containsKey("commands")) {
                val commandsObj = map["commands"]
                if (commandsObj is List<*>) {
                    commands = commandsObj.filterIsInstance(String::class.java)
                }
            }
            if (map.containsKey("message")) {
                message = map["message"]?.toString()
            }

            return Reward(id, chance, permission, commands, message)
        }
    }
}