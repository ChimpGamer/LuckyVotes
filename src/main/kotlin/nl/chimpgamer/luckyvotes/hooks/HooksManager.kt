package nl.chimpgamer.luckyvotes.hooks

import nl.chimpgamer.luckyvotes.LuckyVotesPlugin

class HooksManager(plugin: LuckyVotesPlugin) {
    private val ultimateVotesHook = UltimateVotesHook(plugin)

    fun load() {
        ultimateVotesHook.load()
    }

    fun unload() {
        ultimateVotesHook.unload()
    }
}