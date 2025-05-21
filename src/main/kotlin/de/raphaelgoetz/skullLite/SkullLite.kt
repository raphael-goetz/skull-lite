package de.raphaelgoetz.skullLite

import de.raphaelgoetz.astralis.Astralis
import de.raphaelgoetz.skullLite.command.registerMenuCommand
import de.raphaelgoetz.skullLite.command.registerPlayerSkullCommand
import de.raphaelgoetz.skullLite.command.registerQueryMenuCommand
import de.raphaelgoetz.skullLite.skull.SkullServer

class SkullLite : Astralis() {

    override fun enable() {
        val server = SkullServer()

        server.registerMenuCommand()
        server.registerQueryMenuCommand()
        server.registerPlayerSkullCommand()
    }

}
