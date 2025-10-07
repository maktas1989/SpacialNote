package com.firsatbilisim.spacialnote.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.firsatbilisim.spacialnote.data.dao.CategoryDao
import com.firsatbilisim.spacialnote.data.dao.NoteDao
import com.firsatbilisim.spacialnote.data.model.CategoryModel
import com.firsatbilisim.spacialnote.data.model.NoteModel

@Database(entities = [NoteModel::class, CategoryModel::class], version = 1)
abstract class SpacialNoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao


    // Singleton
    companion object {
        @Volatile
        private var instance: SpacialNoteDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            SpacialNoteDatabase::class.java,
            "SpacialNoteDatabase"
        )
            // Veritabanını sıfırlama ve yeniden oluşturma
            //.fallbackToDestructiveMigration()  // Bu, eski veritabanını temizler ve yeni şemaya geçiş yapar
            //.addMigrations(MIGRATION_9_10)  // Migration burada ekleniyor.
            .build()
    }
}