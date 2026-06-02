package dev.rooster.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction

fun <T : IntEntity> IntEntityClass<T>.findEntry(query: Op<Boolean>): T? {
    return transaction { this@findEntry.find(query).firstOrNull() }
}
