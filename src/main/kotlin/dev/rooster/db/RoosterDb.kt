package dev.rooster.db

import com.google.common.cache.CacheBuilder
import dev.rooster.core.RoosterCache
import dev.rooster.core.RoosterModuleBuilder
import dev.rooster.core.RoosterServices
import dev.rooster.core.initRooster
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Table
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

object RoosterDb {
    internal lateinit var plugin: JavaPlugin
    internal val logger: Logger = Logger.getLogger("RoosterDb")
    lateinit var cache: RoosterCache<String, Any>
    internal var services: RoosterServices = RoosterServices()
    val tables = mutableListOf<Table>()

    fun setup(
        plugin: JavaPlugin,
        services: RoosterServices? = null,
        cache: RoosterCache<String, Any>? = null
    ) {
        this.plugin = plugin
        if (services != null) this.services.byOther(services)
        this.cache = cache ?: RoosterCache(
            CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES)
        )
    }

    fun init(
        plugin: JavaPlugin,
        tables: List<Table>,
        services: RoosterServices? = null,
        cache: RoosterCache<String, Any>? = null
    ) {
        setup(plugin, services, cache)
        initDatabase(tables)
        initRooster(plugin, this.services, this.cache)
    }
}

fun RoosterModuleBuilder.db(tables: List<Table>) {
    RoosterDb.setup(plugin, services, cache)
    afterHooks += { initDatabase(tables) }
}
