package vukan.com.notebook.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import vukan.com.notebook.database.AppRepository
import vukan.com.notebook.database.NoteEntity
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class EditorViewModel(application: Application) : AndroidViewModel(application) {
    val mLiveNote: MutableLiveData<NoteEntity> = MutableLiveData()
    private val mRepository: AppRepository? = AppRepository.getInstance(getApplication())
    private val executor: Executor = Executors.newSingleThreadExecutor()

    fun loadData(id: Int) {
        executor.execute {
            val note: NoteEntity? = mRepository?.getNoteById(id)
            mLiveNote.postValue(note)
        }
    }

    fun saveNote(noteText: String) {
        var note: NoteEntity? = mLiveNote.value

        if (note == null) {
            if (TextUtils.isEmpty(noteText.trim())) return
            note = NoteEntity(Date(), noteText.trim())
        } else {
            note.text = noteText.trim()
        }

        mRepository?.insertNote(note)
    }

    fun deleteNote() {
        mRepository?.deleteNote(mLiveNote.value)
    }
}