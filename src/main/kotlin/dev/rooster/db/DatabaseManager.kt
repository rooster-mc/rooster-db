package dev.rooster.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

//TODO: Make sqlite agnostic with provider system, but make sqlite default
fun initDatabase(tables: List<Table>) {
    val allTables = (tables + RoosterDb.tables).toSet().toList()
    if (allTables.isEmpty()) return

    val dbFile = DatabaseSettings.overrideDatabasePath?.let { java.io.File(it) }
        ?: RoosterDb.plugin.dataFolder.resolve("database.db")

    dbFile.parentFile?.mkdirs()

    val path = dbFile.absolutePath

    Database.connect("jdbc:sqlite:${path}", "org.sqlite.JDBC")
    // TODO: Add a warning system someday, if schema is not up to date but tables cant be modified
    transaction {
        SchemaUtils.createMissingTablesAndColumns(*allTables.toTypedArray())
    }
}

