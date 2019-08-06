package vukan.com.notebook.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var date: Date?,
    var text: String?
) {
    @Ignore
    constructor(noteDate: Date, noteText: String) : this(date = noteDate, text = noteText)
}