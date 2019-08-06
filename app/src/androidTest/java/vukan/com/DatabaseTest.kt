package vukan.com

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import vukan.com.notebook.database.AppDatabase
import vukan.com.notebook.database.NoteDao
import vukan.com.notebook.utilities.SampleData
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var mDb: AppDatabase
    private lateinit var mDao: NoteDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        mDao = mDb.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun createAndRetrieveNotes() {
        mDao.insertAll(SampleData.notes)
        assertEquals(SampleData.notes.size, mDao.getCount())
    }

    @Test
    fun compareNotesContent() {
        mDao.insertAll(SampleData.notes)
        val fromDb = mDao.getNoteById(1)
        assertEquals(SampleData.notes[0].text, fromDb.text)
        assertEquals(1, fromDb.id)
    }
}