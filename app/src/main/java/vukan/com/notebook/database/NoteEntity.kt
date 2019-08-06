package vukan.com.notebook.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var date: Date? = null
    var text: String? = null

    @Ignore
    constructor()

    constructor(id: Int, date: Date, text: String) {
        this.id = id
        this.date = date
        this.text = text
    }

    @Ignore
    constructor(date: Date, text: String) {
        this.date = date
        this.text = text
    }
}