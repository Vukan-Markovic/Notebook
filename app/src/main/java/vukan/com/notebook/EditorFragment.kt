package vukan.com.notebook

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_editor.*
import vukan.com.notebook.viewmodel.EditorViewModel

class EditorFragment : Fragment() {
    private var mNewNote: Boolean = false
    private var mEditing: Boolean = false
    private val mViewModel by viewModels<EditorViewModel>()
    private val args by navArgs<EditorFragmentArgs>()

    companion object {
        private const val key: String = "KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) mEditing = savedInstanceState.getBoolean(key)

        mViewModel.mLiveNote.observe(this, Observer {
            if (it != null && !mEditing) {
                edit_note_text.setText(it.text)
                note_date.text =
                    String.format(getString(R.string.note_date_creation), it.date.toString())
            }
        })

        if (args.noteId == 0) {
            mNewNote = true
            note_date.visibility = View.GONE
            separator.visibility = View.GONE
        } else mViewModel.loadData(args.noteId)

        fabSaveNote.setOnClickListener {
            if (TextUtils.isEmpty(edit_note_text.text.toString())) Snackbar.make(
                it,
                getString(R.string.empty_note),
                Snackbar.LENGTH_SHORT
            ).show()
            else {
                mViewModel.saveNote(edit_note_text.text.toString())
                findNavController()
                    .navigate(EditorFragmentDirections.editorToMainFragmentAction())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (args.noteId == 0) activity?.title = getString(R.string.new_note)
        else activity?.title = getString(R.string.note)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.action_delete -> {
                mViewModel.deleteNote()
                findNavController()
                    .navigate(EditorFragmentDirections.editorToMainFragmentAction())
                return true
            }
            item.itemId == R.id.menu_item_share -> {
                if (TextUtils.isEmpty(edit_note_text.text.toString())) Snackbar.make(
                    item.actionView,
                    getString(R.string.empty_note),
                    Snackbar.LENGTH_SHORT
                ).show()
                else startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, edit_note_text.text)
                    type = "text/plain"
                }, resources.getText(R.string.share)))

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!mNewNote) inflater.inflate(R.menu.menu_editor, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(key, true)
        super.onSaveInstanceState(outState)
    }
}