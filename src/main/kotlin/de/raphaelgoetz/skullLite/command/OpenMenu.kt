package de.raphaelgoetz.skullLite.command

import de.raphaelgoetz.astralis.command.AstralisCommand
import de.raphaelgoetz.astralis.command.registerCommand
import de.raphaelgoetz.skullLite.menu.openCategoryMenu
import de.raphaelgoetz.skullLite.skull.SkullServer
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.entity.Player

fun SkullServer.registerMenuCommand() {
    val command = Commands.literal("skulls")
        .requires { it.sender.hasPermission("skulllite.player.menu") }
        .executes { context ->

            val player = context.source.sender as? Player ?: return@executes 0
            player.openCategoryMenu(this)
            1
        }
        .build()

    registerCommand(
        AstralisCommand(
            command = command,
            description = "Will open the skull category menu to then select a skull.",
            aliases = listOf("skullmenu", "skull", "skull-menu"),
        )
    )
}