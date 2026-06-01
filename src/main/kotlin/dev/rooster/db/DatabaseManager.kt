package dev.rooster.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

fun initDatabase(tables: List<Table>) {
    if (tables.isEmpty()) return

    val dbFile = DatabaseSettings.overrideDatabasePath?.let { java.io.File(it) }
        ?: RoosterDb.plugin.dataFolder.resolve("database.db")

    // Ensure the parent directory exists
    dbFile.parentFile?.mkdirs()

    val path = dbFile.absolutePath

    Database.connect("jdbc:sqlite:${path}", "org.sqlite.JDBC")
    // TODO: Add a warning system someday, if schema is not up to date but tables cant be modified
    transaction {
        SchemaUtils.createMissingTablesAndColumns(*tables.toSet().toTypedArray())
    }
}

fun <T : IntEntity> IntEntityClass<T>.findEntry(query: Op<Boolean>): T? {
    return transaction { this@findEntry.find(query).firstOrNull() }
}
