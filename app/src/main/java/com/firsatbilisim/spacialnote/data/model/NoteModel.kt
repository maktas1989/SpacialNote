package com.firsatbilisim.spacialnote.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "notes")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "imagePath")
    var imagePath: String? = null,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "categoryId") // Kategori ID'si
    var categoryId: Int = 0, // Varsayılan olarak 0 (kategorisiz)

    @ColumnInfo(name = "createdAt")
    var createdAt: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),

    @ColumnInfo(name = "updatedAt")
    var updatedAt: String? = null
)