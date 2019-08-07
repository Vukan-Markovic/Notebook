package vukan.com.notebook.utilities

import vukan.com.notebook.database.NoteEntity
import java.util.*

object SampleData {
    private const val SAMPLE_TEXT_1 = "First note"
    private const val SAMPLE_TEXT_2 = "Second note"
    private const val SAMPLE_TEXT_3 = "Third note"

    val notes: List<NoteEntity>
        get() {
            val notes = ArrayList<NoteEntity>()
            notes.add(NoteEntity(getDate(0), SAMPLE_TEXT_1))
            notes.add(NoteEntity(getDate(-1), SAMPLE_TEXT_2))
            notes.add(NoteEntity(getDate(-2), SAMPLE_TEXT_3))
            return notes
        }

    private fun getDate(diff: Int): Date {
        val cal = GregorianCalendar()
        cal.add(Calendar.MILLISECOND, diff)
        return cal.time
    }
}