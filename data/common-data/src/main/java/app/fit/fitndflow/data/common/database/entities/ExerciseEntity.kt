package app.fit.fitndflow.data.common.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercises_table",
    foreignKeys = [ForeignKey(
        entity = CategoryEntity::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE // Borra los ejercicios si se elimina su categoría
    )],
    indices = [Index("categoryId")]
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id: Int = 0,
    @ColumnInfo val categoryId: Int,
    @ColumnInfo val nameEn: String,
    @ColumnInfo val nameEs: String,
)

//todo CREAR DAO DE EJERCICIOS