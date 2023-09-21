package nl.chimpgamer.luckyvotes.hooks

import nl.chimpgamer.luckyvotes.LuckyVotesPlugin

class HooksManager(plugin: LuckyVotesPlugin) {
    private val ultimateVotesHook = UltimateVotesHook(plugin)
    private val votingPluginHook = VotingPluginHook(plugin)

    fun load() {
        ultimateVotesHook.load()
        votingPluginHook.load()
    }

    fun unload() {
        ultimateVotesHook.unload()
        votingPluginHook.unload()
    }
}