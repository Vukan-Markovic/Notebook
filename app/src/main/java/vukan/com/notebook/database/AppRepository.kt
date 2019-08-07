package vukan.com.notebook.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppRepository private constructor(context: Context) {
    private var mDb: AppDatabase? = AppDatabase.getInstance(context)
    var mNotes: LiveData<PagedList<NoteEntity>>? = getAllNotes()
    private var executor: Executor = Executors.newSingleThreadExecutor()

    companion object {
        private var instance: AppRepository? = null

        fun getInstance(context: Context): AppRepository? {
            if (instance == null) instance = AppRepository(context)
            return instance
        }
    }

    private fun getAllNotes(): LiveData<PagedList<NoteEntity>>? {
        return mDb?.noteDao()?.getAll()?.toLiveData(5)
    }

    fun deleteAllNotes() {
        executor.execute {
            mDb?.noteDao()?.deleteAll()
        }
    }

    fun getNoteById(id: Int): NoteEntity? {
        return mDb?.noteDao()?.getNoteById(id)
    }

    fun insertNote(note: NoteEntity?) {
        executor.execute {
            mDb?.noteDao()?.insertNote(note)
        }
    }

    fun deleteNote(note: NoteEntity?) {
        executor.execute {
            mDb?.noteDao()?.deleteNote(note)
        }
    }
}