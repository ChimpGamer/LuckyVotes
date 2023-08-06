package nl.chimpgamer.luckyvotes.hooks

import nl.chimpgamer.luckyvotes.LuckyVotesPlugin
import nl.chimpgamer.luckyvotes.extensions.parse
import nl.chimpgamer.luckyvotes.extensions.registerEvents
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import teozfrank.ultimatevotes.events.VoteRewardEvent

class UltimateVotesHook(private val plugin: LuckyVotesPlugin) : Listener {

    private val isPluginEnabled get() = plugin.server.pluginManager.isPluginEnabled("UltimateVotes")

    fun load() {
        if (isPluginEnabled) {
            plugin.registerEvents(this)
            plugin.logger.info("Successfully loaded UltimateVotes hook!")
        }
    }

    fun unload() {
        HandlerList.unregisterAll(this)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun VoteRewardEvent.onVoteReward() {
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