package vukan.com.notebook.utilities

import vukan.com.notebook.database.NoteEntity
import java.util.*
import kotlin.collections.ArrayList

class SampleData {
    companion object {
        private const val sample_text_1: String = "Vukan"
        private const val sample_text_2: String = "Markovic"
        private const val sample_text_3: String = "97"

        private fun getDate(diff: Int): Date {
            val calendar = GregorianCalendar()
            calendar.add(Calendar.MILLISECOND, diff)
            return calendar.time
        }

        fun getNotes(): List<NoteEntity> {
            val notes: MutableList<NoteEntity> = ArrayList()
            notes.add(NoteEntity(getDate(0), sample_text_1))
            notes.add(NoteEntity(getDate(-1), sample_text_2))
            notes.add(NoteEntity(getDate(-2), sample_text_3))
            return notes
        }
    }
}