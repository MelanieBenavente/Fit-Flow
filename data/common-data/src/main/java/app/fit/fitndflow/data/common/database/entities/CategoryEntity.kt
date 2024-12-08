package app.fit.fitndflow.data.common.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "nameEs") val nameEs: String,
    @ColumnInfo(name = "nameEn") val nameEn: String
)

//todo CREAR DAO DE CATEGORIAS ---->  SEGUIR VIENDO VIDEO ROOM ARISTDEV -> https://www.youtube.com/watch?v=lYBb4QedYH8