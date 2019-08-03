package vukan.com

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import vukan.com.notebook.database.AppDatabase
import vukan.com.notebook.database.NoteDao
import vukan.com.notebook.database.NoteEntity
import vukan.com.notebook.utilities.SampleData

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var mDb: AppDatabase
    private lateinit var mDao: NoteDao

    @Before
    fun createDb() {
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        mDao = mDb.noteDao()
    }

    @After
    fun closeDb() {
        mDb.close()
    }

    @Test
    fun createAndRetrieveNotes() {
        mDao.insertAll(SampleData.getNotes())
        val count: Int = mDao.getCount()
        assertEquals(SampleData.getNotes().size, count)
    }

    @Test
    fun compareStrings() {
        mDao.insertAll(SampleData.getNotes())
        val original: NoteEntity = SampleData.getNotes().get(0)
        val fromDb = mDao.getNoteById(1)
        assertEquals(original.text, fromDb.text)
        assertEquals(1, fromDb.id)
    }
}