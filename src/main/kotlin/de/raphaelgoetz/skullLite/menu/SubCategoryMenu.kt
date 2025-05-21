package de.raphaelgoetz.skullLite.menu

import de.raphaelgoetz.astralis.items.smartTransItem
import de.raphaelgoetz.astralis.ui.builder.SmartClick
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import de.raphaelgoetz.astralis.ui.data.InventorySlots
import de.raphaelgoetz.astralis.ui.openTransPageInventory
import de.raphaelgoetz.skullLite.skull.SkullMetaData
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.meta.ItemMeta
import java.util.function.Consumer

fun Player.openSubCategoryMenu(skulls:  List<SkullMetaData>) {
    val items = skulls.map { skull ->

        fun onClick(): Consumer<InventoryClickEvent> {
            return Consumer<InventoryClickEvent> { event ->
                event.isCancelled = true

                if (event.currentItem != null) {
                    inventory.addItem(event.currentItem!!)
                }

                closeInventory()
            }
        }

        SmartClick(skull.item, onClick())
    }

    openTransPageInventory("gui.subcategory.title", "Select a skull", InventoryRows.ROW6, items, InventorySlots.SLOT1ROW1, InventorySlots.SLOT9ROW5) {
        val left = smartTransItem<ItemMeta>("gui.item.arrow.left" , material = Material.ARROW)
        val right = smartTransItem<ItemMeta>("gui.item.arrow.right" , material = Material.ARROW)
        pageLeft(InventorySlots.SLOT1ROW6, left.itemStack)
        pageRight(InventorySlots.SLOT9ROW6, right.itemStack)

    }

}

