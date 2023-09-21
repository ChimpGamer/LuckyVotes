package nl.chimpgamer.luckyvotes.hooks

import com.bencodez.votingplugin.events.PlayerPostVoteEvent
import nl.chimpgamer.luckyvotes.LuckyVotesPlugin
import nl.chimpgamer.luckyvotes.extensions.parse
import nl.chimpgamer.luckyvotes.extensions.registerEvents
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class VotingPluginHook(private val plugin: LuckyVotesPlugin) : Listener {

    private val isPluginEnabled get() = plugin.server.pluginManager.isPluginEnabled("VotingPlugin")

    fun load() {
        if (isPluginEnabled) {
            plugin.registerEvents(this)
            plugin.logger.info("Successfully loaded VotingPlugin hook!")
        }
    }

    fun unload() {
        HandlerList.unregisterAll(this)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun PlayerPostVoteEvent.onPlayerPostVote() {
        val player = user.player ?: return
        val luckyReward = plugin.getLuckyReward(player)
        if (luckyReward == null) {
            println("${player.name} was not lucky")
            return
        }
        luckyReward.executeCommands(player)
        luckyReward.message?.let { player.sendRichMessage(it) }
        plugin.server.broadcast(plugin.settingsConfig.luckyAnnouncement.parse(mapOf("displayname" to player.displayName(), "playername" to player.name)))
    }
}