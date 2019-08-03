package vukan.com.notebook

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.content_editor.*
import vukan.com.notebook.viewmodel.EditorViewModel

class EditorActivity : AppCompatActivity() {

    private var mNewNote: Boolean = false
    private var mEditing: Boolean = false
    lateinit var mViewModel: EditorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_launcher_background)
        if (savedInstanceState == null && !mEditing) mEditing =
            savedInstanceState?.getBoolean("KEY")!!
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel::class.java)
        mViewModel.mLiveNote.observe(this, Observer {
            edit_note_text.setText(it.text)
        })

        // get extras
        val extra = null
        if (extra == null) {
            title = "New note"
            mNewNote = true
        } else {
            title = "Edit note"
            // get ID
            val id = 0
            mViewModel.loadData(id)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            saveAndReturn()
            return true
        } else if (item.itemId == R.id.action_delete) {
            mViewModel.deleteNote()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!mNewNote) menuInflater.inflate(R.menu.menu_editor, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun saveAndReturn() {
        mViewModel.saveNote(edit_note_text.text.toString())
        finish()
    }

    override fun onBackPressed() {
        saveAndReturn()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("KEY", true)
        super.onSaveInstanceState(outState)
    }
}