package id.ac.unpas.mynote

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.unpas.mynote.dao.NoteDao
import id.ac.unpas.mynote.models.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteDao: NoteDao) : ViewModel() {

    val list: LiveData<List<Note>> = noteDao.getAllNotes()

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteDao.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.deleteNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
    }
}