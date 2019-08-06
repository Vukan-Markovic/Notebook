package vukan.com.notebook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import vukan.com.notebook.database.AppRepository
import vukan.com.notebook.database.NoteEntity
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class EditorViewModel(application: Application) : AndroidViewModel(application) {
    var mLiveNote: MutableLiveData<NoteEntity> = MutableLiveData()
    private var mRepository: AppRepository? = AppRepository.getInstance(getApplication())
    private var executor: Executor = Executors.newSingleThreadExecutor()

    fun loadData(id: Int) {
        executor.execute {
            mLiveNote.postValue(mRepository?.getNoteById(id))
        }
    }

    fun saveNote(noteText: String) {
        var note: NoteEntity? = mLiveNote.value
        if (note == null) note = NoteEntity(Date(), noteText.trim())
        else note.text = noteText.trim()
        mRepository?.insertNote(note)
    }

    fun deleteNote() {
        mRepository?.deleteNote(mLiveNote.value)
    }
}