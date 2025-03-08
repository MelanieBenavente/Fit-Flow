package app.fit.fitndflow.data.common.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "series_table",
    foreignKeys = [ForeignKey(
        entity = ExerciseEntity::class,
        parentColumns = ["id"],
        childColumns = ["exerciseId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("exerciseId")]
)
data class SerieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id: Int = 0,
    @ColumnInfo val exerciseId: Int,
    @ColumnInfo val reps: Int?,
    @ColumnInfo val weight: Double?,
    @ColumnInfo val date: String
)

//todo CREAR DAO DE SERIES