package de.raphaelgoetz.skullLite.skull

import de.raphaelgoetz.astralis.items.builder.SmartItem

data class SkullMetaData(
    val category: String,
    val item: SmartItem,
    val query: List<String>
)