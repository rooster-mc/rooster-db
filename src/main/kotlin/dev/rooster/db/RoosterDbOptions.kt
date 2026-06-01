package dev.rooster.db

import dev.rooster.core.RoosterSettings
import dev.rooster.core.WarningScaffold

enum class DatabaseWarnings(
    override var warningMethod: (Any) -> String = { "" },
    override var parents: List<WarningScaffold> = emptyList(),
    override var defaultValue: Boolean = true
) : WarningScaffold {
    ;

    constructor(vararg parents: DatabaseWarnings) : this(warningMethod = { "" }, parents = parents.toList())
    constructor(defaultValue: Boolean, vararg parents: DatabaseWarnings) : this(warningMethod = { "" }, defaultValue = defaultValue, parents = parents.toList())
    constructor(method: (Any) -> String, vararg parents: DatabaseWarnings) : this(warningMethod = method, parents = parents.toList())
    constructor(defaultValue: Boolean, method: (Any) -> String, vararg parents: DatabaseWarnings) : this(warningMethod = method, defaultValue = defaultValue, parents = parents.toList())

    override fun disable() = DatabaseSettings.setWarningOption(this, false)
    override fun enable() = DatabaseSettings.setWarningOption(this, true)
    internal fun warn(obj: Any = -1) = warnScaffold(this.name, RoosterDb.logger, DatabaseSettings, obj)
}

object DatabaseSettings : RoosterSettings<DatabaseWarnings>(DatabaseWarnings.entries) {
    val overrideDatabasePath: String? = null
}
