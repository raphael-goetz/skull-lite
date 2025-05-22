package de.raphaelgoetz.skullLite.command

import com.mojang.brigadier.arguments.StringArgumentType
import de.raphaelgoetz.astralis.command.AstralisCommand
import de.raphaelgoetz.astralis.command.registerCommand
import de.raphaelgoetz.astralis.items.data.InteractionType
import de.raphaelgoetz.astralis.text.communication.CommunicationType
import de.raphaelgoetz.astralis.text.translation.sendTransText
import de.raphaelgoetz.skullLite.menu.openSubCategoryMenu
import de.raphaelgoetz.skullLite.skull.SkullServer
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.entity.Player

fun SkullServer.registerQueryMenuCommand() {
    val command = Commands.literal("query")
        .requires { it.sender.hasPermission("skulllite.player.query") }
        .then(
            Commands.argument("query", StringArgumentType.string())
                .executes { context ->
                    val player = context.source.sender as? Player ?: return@executes 0
                    val query = StringArgumentType.getString(context, "query")
                    val skulls = query(query)

                    if (skulls.isNotEmpty()) {
                        player.openSubCategoryMenu(skulls)
                    } else {
                        player.sendTransText("command.skull.not.found") {
                            type = CommunicationType.WARNING
                        }
                    }
                    1
            }
        )
        .build()

    registerCommand(
        AstralisCommand(
            command = command,
            description = "Will search for a specific list of skulls.",
            aliases = listOf("query-skull", "search", "search-skull"),
        )
    )
}