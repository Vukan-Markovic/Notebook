package vukan.com.notebook.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var date: Date = Date(),
    var text: String = ""
) {
    constructor(date: Date, text: String) : this()
}