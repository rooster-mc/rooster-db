package dev.rooster.db.utility_tables.attributes

import dev.rooster.core.RoosterService
import dev.rooster.db.utility_tables.PlayerManager
import dev.rooster.db.utility_tables.PlayerManager.Companion.dbPlayer
import org.bukkit.entity.Player
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import kotlin.reflect.KClass

class PlayerAttributeManager(playerManager: PlayerManager) : AttributeManager<Player, EntityID<Int>>(PlayerAttributes),
    RoosterService {
    object PlayerAttributes : Attributes("RoosterPlayerAttributes") {
        val player = reference("player", PlayerManager.Players, onDelete = ReferenceOption.CASCADE)
    }

    override fun fieldInfo(value: Player): Pair<Column<EntityID<Int>>, EntityID<Int>> =
        PlayerAttributes.player to value.dbPlayer().id

    override fun targetClass(): KClass<out RoosterService> {
        return PlayerAttributeManager::class
    }
}
