package com.firsatbilisim.spacialnote.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.firsatbilisim.spacialnote.data.model.CategoryModel
import com.firsatbilisim.spacialnote.data.model.NoteModel

@Dao
interface NoteDao {
    // Not eklemek için insert fonksiyonu
    @Insert
    suspend fun insertNote(note: NoteModel)

    // Notları listelemek için (isteğe bağlı)
    @Query("SELECT * FROM notes WHERE categoryId = :categoryId")
    suspend fun getAllNotes(categoryId:Int): List<NoteModel>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NoteModel?

    @Update
    suspend fun updateNote(note: NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)

    // Başlığa göre arama yapacak sorgu
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :title || '%' AND categoryId = :categoryId")
    suspend fun searchNotesByTitle(title: String, categoryId: Int): List<NoteModel>
}