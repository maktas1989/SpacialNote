package com.firsatbilisim.spacialnote.data.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.firsatbilisim.spacialnote.data.model.NoteModel
import com.firsatbilisim.spacialnote.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository = NoteRepository(application)
    val noteList: StateFlow<List<NoteModel>> get() = noteRepository.noteList

    private val _selectedNote = MutableStateFlow<NoteModel?>(null)
    val selectedNote: StateFlow<NoteModel?> get() = _selectedNote

    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> get() = _showDialog

    fun addNote(note: NoteModel) {
        viewModelScope.launch {
            noteRepository.addNote(note)
        }
    }

    fun loadNotes(categoryId: Int) {
        viewModelScope.launch {
            noteRepository.getNote(categoryId)
        }
    }

    fun loadNoteDetail(noteId: Int) {
        viewModelScope.launch {
            _selectedNote.value = noteRepository.getNoteById(noteId)
        }
    }

    fun updateNote(note: NoteModel) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

    fun deleteNote(note: NoteModel) {
        viewModelScope.launch {
            val notes = noteRepository.getNoteById(note.id) // Silmek istediğin notu al
            notes?.let {
                noteRepository.deleteNote(note) // Notu sil
                loadNotes(it.categoryId) // Kategorisine göre listeyi güncelle
                _showDialog.value = false
            }
        }
    }

    fun searchNotesByTitle(title: String, categoryId: Int) {
        viewModelScope.launch {
            noteRepository.searchNotesByTitle(title, categoryId)
        }
    }

    fun showDeleteDialog() {
        _showDialog.value = true
    }

    fun hideDeleteDialog() {
        _showDialog.value = false
    }
}