package dev.rooster.db.utility_tables

import dev.rooster.db.RoosterDb
import org.jetbrains.exposed.sql.Table

abstract class UtilityTable(table: Table) {
    init {
        RoosterDb.tables += table
    }
}
