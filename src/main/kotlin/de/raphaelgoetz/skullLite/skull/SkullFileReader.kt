package de.raphaelgoetz.skullLite.skull

import de.raphaelgoetz.astralis.items.builder.SmartItem
import de.raphaelgoetz.astralis.items.createSmartItem
import de.raphaelgoetz.astralis.items.data.InteractionType
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.meta.SkullMeta
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI
import java.util.UUID

class SkullFileReader(
    path: String,
) {

    private val skulls: MutableList<SkullMetaData> = mutableListOf()
    private val categories: List<SkullMetaData>

    init {

        val inputStream =
            javaClass.classLoader.getResourceAsStream(path) ?: throw NullPointerException("ResourceSteam is empty!")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val categoryMap: MutableMap<String, SkullMetaData> = mutableMapOf()

        var line: String? = reader.readLine()
        while (line != null) {
            val skullData = line.parseToSkullMetaData()

            if (skullData != null) {
                categoryMap.putIfAbsent(skullData.category, skullData)
                skulls.add(skullData)
            }

            line = reader.readLine()

        }

        categories = categoryMap.map { category ->
            val item = createSmartItem<SkullMeta>(
                name = category.key,
                material = Material.PLAYER_HEAD,
                interactionType = InteractionType.DISPLAY_CLICK
            ) {
                val meta = category.value.item.itemStack.itemMeta as SkullMeta
                this.playerProfile = meta.playerProfile
            }
            SkullMetaData(category.key, item, category.value.query)
        }
    }

    private fun String.parseToSkullMetaData(): SkullMetaData? {
        val data = split(";")

        if (data.size != 4) return null

        val category = data[0]
        val name = data[1]
        val url = data[2]
        val query = data[3].split("|")
        val item = url.toSmartItem(name)

        return SkullMetaData(category, item, query)
    }

    private fun String.toSmartItem(display: String): SmartItem {
        val fakePlayerProfile = Bukkit.createProfile(UUID.randomUUID())
        val playerTextures = fakePlayerProfile.textures

        playerTextures.skin = URI.create("https://textures.minecraft.net/texture/$this").toURL()
        fakePlayerProfile.setTextures(playerTextures)

        return createSmartItem<SkullMeta>(display, Material.PLAYER_HEAD, interactionType = InteractionType.SUCCESS) {
            playerProfile = fakePlayerProfile
        }
    }

    fun getSkulls(): List<SkullMetaData> = skulls
    fun getCategories(): List<SkullMetaData> = categories
}