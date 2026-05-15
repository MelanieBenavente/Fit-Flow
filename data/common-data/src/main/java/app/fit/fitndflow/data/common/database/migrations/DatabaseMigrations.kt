package app.fit.fitndflow.data.common.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE exercises_table RENAME COLUMN record TO recordHeight")
            database.execSQL("ALTER TABLE exercises_table ADD COLUMN recordReps INTEGER NOT NULL DEFAULT 0")
        }
    }

    val ALL: Array<Migration> = arrayOf(MIGRATION_1_2)
}
