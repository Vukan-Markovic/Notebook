package vukan.com.notebook.database

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppRepository private constructor(context: Context) {
    private var mDb: AppDatabase? = AppDatabase.getInstance(context)
    var mNotes: LiveData<List<NoteEntity>>? = getAllNotes()
    private var executor: Executor = Executors.newSingleThreadExecutor()

    companion object {
        private var instance: AppRepository? = null

        fun getInstance(context: Context): AppRepository? {
            if (instance == null) {
                instance = AppRepository(context)
            }
            return instance
        }
    }

    private fun getAllNotes(): LiveData<List<NoteEntity>>? {
        return mDb?.noteDao()?.getAll()
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