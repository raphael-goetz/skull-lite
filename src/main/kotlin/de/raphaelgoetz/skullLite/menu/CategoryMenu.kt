package de.raphaelgoetz.skullLite.menu

import de.raphaelgoetz.astralis.ui.builder.SmartClick
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import de.raphaelgoetz.astralis.ui.openTransPageInventory
import de.raphaelgoetz.skullLite.skull.SkullServer
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.function.Consumer

fun Player.openCategoryMenu(server: SkullServer) {

    val items = server.getCategories().map { skull ->

        fun onClick(): Consumer<InventoryClickEvent> {
            return Consumer<InventoryClickEvent> { event ->
                event.isCancelled = true
                val skulls = server.getCategory(skull.category)
                openSubCategoryMenu(skulls)
            }
        }

        SmartClick(skull.item, onClick())
    }

    openTransPageInventory("gui.category.title", "Category Menu", InventoryRows.ROW3, items)

}