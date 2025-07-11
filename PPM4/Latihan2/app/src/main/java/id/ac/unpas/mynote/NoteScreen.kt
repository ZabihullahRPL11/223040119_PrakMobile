package id.ac.unpas.mynote

import androidx. compose. foundation. layout. Column
import androidx. compose. foundation. layout. Spacer
import androidx. compose. foundation. layout. fillMaxWidth
import androidx. compose. foundation. layout.height
import androidx. compose. foundation. layout.padding
import androidx.compose. foundation. lazy.LazyColumn
import androidx. compose. foundation. lazy.items
import androidx. compose.material3.Button
import androidx.compose.material3.Card
import androidx. compose. material3. MaterialTheme
import androidx.compose.material3.Text
import androidx. compose. material3. TextField
import androidx. compose.runtime. Composable
import androidx. compose.runtime. getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx. compose.runtime. setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx. lifecycle. LiveData
import com.benasher44.uuid.uuid4
import id.ac.unpas.mynote.models.Note
import kotlinx. coroutines. launch
import androidx.compose.foundation.clickable
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement


@Composable
fun NoteScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dao = NoteDatabase.getDatabase(context).noteDao()
    val list: LiveData<List<Note>> = dao.getAllNotes()
    val notes: List<Note> by list.observeAsState(initial = listOf())
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    var editingNoteId by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    scope.launch {
                        if (isEditing && editingNoteId != null) {
                            // Update note
                            dao.updateNote(Note(editingNoteId!!, title, description))
                            isEditing = false
                            editingNoteId = null
                        } else {
                            // Tambah baru
                            dao.insertNote(
                                Note(id = uuid4().toString(), title = title, description = description)
                            )
                        }
                        title = ""
                        description = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditing) "Update Note" else "Save Note")
        }


        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            scope.launch {
                                dao.deleteNote(note)
                            }
                        }

                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(note.description, style = MaterialTheme.typography.bodyMedium)

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TextButton(onClick = {
                                title = note.title
                                description = note.description
                                editingNoteId = note.id
                                isEditing = true
                            }) {
                                Text("Edit")
                            }

                            TextButton(onClick = {
                                scope.launch {
                                    dao.deleteNote(note)
                                }
                            }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
