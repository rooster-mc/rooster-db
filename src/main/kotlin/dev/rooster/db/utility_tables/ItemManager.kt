package dev.rooster.db.utility_tables

/*
class ItemManager : UtilityTable(Items), RoosterService {
    object Items : IntIdTable("RoosterItems") {
        val itemStackJson = text("item")
        val key = varchar("key", 50).nullable()

        val transformedItem = itemStackJson.transform(
            { itemStack -> Gson().toJson(itemStack.serialize()) },
            { json -> ItemStack.deserialize(Gson().fromJson<Map<String, Any>>(json)) }
        )
    }

    class Item(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<Item>(Items)

        var itemStack: ItemStack by Items.transformedItem

        var key: String? by Items.key
    }

    fun upsertItem(itemStack: ItemStack, key: String? = null, ignoreKeyItems: Boolean = false): Item {
        return transaction {
            val itemStackSerialized = itemStack.serialize()

            var query = Items.itemStackJson eq itemStackSerialized
            if (!ignoreKeyItems && key != null) query = query and (Items.key eq key)
            val item = Item.find { query }.firstOrNull()

            if (item != null) return@transaction item

            Item.new {
                this.itemStack = itemStack
                this.key = key
            }
        }
    }

    fun itemByKey(key: String): ItemStack? {
        return transaction {
            Item.find { Items.key eq key }.firstOrNull()?.itemStack
        }
    }

    override fun targetClass(): KClass<out RoosterService> = ItemManager::class
}*/
