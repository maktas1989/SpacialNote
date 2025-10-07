package com.firsatbilisim.spacialnote.data.repository

import android.app.Application
import com.firsatbilisim.spacialnote.data.model.NoteModel
import com.firsatbilisim.spacialnote.data.room.SpacialNoteDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NoteRepository(application: Application) {
    private val _noteList = MutableStateFlow<List<NoteModel>>(emptyList())
    val noteList: StateFlow<List<NoteModel>> get() = _noteList

    private val db: SpacialNoteDatabase by lazy { SpacialNoteDatabase.invoke(application) }

    suspend fun addNote(note: NoteModel) {
        db.noteDao().insertNote(note)
    }

    suspend fun getNote(categoryId: Int) {
        val notes = db.noteDao().getAllNotes(categoryId)
        _noteList.value = notes
    }

    suspend fun getNoteById(noteId: Int): NoteModel? {
        return db.noteDao().getNoteById(noteId)
    }

    suspend fun updateNote(note: NoteModel){
        db.noteDao().updateNote(note)
    }

    suspend fun deleteNote(note: NoteModel) {
        db.noteDao().deleteNote(note)
    }

    suspend fun searchNotesByTitle(title: String, categoryId: Int) {
        val searchNote = db.noteDao().searchNotesByTitle(title, categoryId)
        _noteList.value = searchNote
    }
}