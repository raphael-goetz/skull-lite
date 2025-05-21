package de.raphaelgoetz.skullLite.skull

class SkullServer() {

    private val skulls: List<SkullMetaData>
    private val categories: List<SkullMetaData>

    init {
        val reader = SkullFileReader("skulls")

        skulls = reader.getSkulls()
        categories = reader.getCategories()
    }

    fun getCategories(): List<SkullMetaData> = categories

    fun getCategory(category: String): List<SkullMetaData> {
        return skulls.filter { it.category == category }
    }

    fun query(query: String): List<SkullMetaData> {
        return skulls.filter { skull ->
            skull.query.any { queryParam ->
                queryParam.contains(query, ignoreCase = true)
            }
        }
    }
}