package vukan.com.notebook

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import vukan.com.notebook.database.NoteEntity
import vukan.com.notebook.ui.NotesAdapter
import vukan.com.notebook.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    var notesData: MutableList<NoteEntity> = ArrayList()
    private var mAdapter: NotesAdapter? = null
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recycler_view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        val divider = DividerItemDecoration(recycler_view.context, layoutManager.orientation)
        recycler_view.addItemDecoration(divider)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mViewModel.mNotes?.observe(this, Observer {
            notesData.clear()
            notesData.addAll(it)

            if (mAdapter == null) {
                mAdapter = NotesAdapter(notesData)
                recycler_view.adapter = mAdapter
            } else mAdapter!!.notifyDataSetChanged()
        })

        fabAddNote.setOnClickListener {}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                mViewModel.getSimpleData()
                true
            }
            R.id.delete_all -> {
                deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllNotes() {
        mViewModel.deleteAllNotes()
    }
}