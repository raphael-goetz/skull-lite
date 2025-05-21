package de.raphaelgoetz.skullLite.command

import com.mojang.brigadier.arguments.StringArgumentType
import de.raphaelgoetz.astralis.command.AstralisCommand
import de.raphaelgoetz.astralis.command.registerCommand
import de.raphaelgoetz.astralis.items.createSmartItem
import de.raphaelgoetz.skullLite.skull.SkullServer
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta

fun SkullServer.registerPlayerSkullCommand() {
    val command = Commands.literal("playerskull")
        .requires { it.sender.hasPermission("skulllite.player.get") }
        .then(
            Commands.argument("player", StringArgumentType.string())
                .executes { context ->
                    val player = context.source.sender as? Player ?: return@executes 0
                    val query = StringArgumentType.getString(context, "player")

                    val offlinePlayer = Bukkit.getOfflinePlayer(query)

                    val skull = createSmartItem<SkullMeta>(offlinePlayer.name ?: "Player Not Found" , Material.PLAYER_HEAD) {
                        owningPlayer = offlinePlayer
                        playerProfile = offlinePlayer.playerProfile
                    }

                    player.inventory.addItem(skull.itemStack)
                    1
                }
        )
        .build()

    registerCommand(
        AstralisCommand(
            command = command,
            description = "Will search for a specific list of skulls.",
            aliases = listOf("pskull", "player-skull", "phead", "player-head", "playerhead"),
        )
    )
}