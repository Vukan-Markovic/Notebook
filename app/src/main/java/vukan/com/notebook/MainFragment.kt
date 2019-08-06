package vukan.com.notebook

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import vukan.com.notebook.MainFragment.DeleteDialog.OnYesNoClick
import vukan.com.notebook.database.NoteEntity
import vukan.com.notebook.ui.NotesAdapter
import vukan.com.notebook.viewmodel.MainViewModel


class MainFragment : Fragment() {
    private var notesData: MutableList<NoteEntity> = ArrayList()
    private var mAdapter: NotesAdapter? = null
    private val mViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                recycler_view.context,
                layoutManager.orientation
            )
        )

        mViewModel.mNotes?.observe(this, Observer {
            notesData.clear()
            notesData.addAll(it)

            if (mAdapter == null) {
                mAdapter = NotesAdapter(notesData)
                recycler_view.adapter = mAdapter
            } else mAdapter!!.notifyDataSetChanged()
        })

        fabAddNote.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.mainToEditorFragmentAction())
        }
    }

    override fun onResume() {
        super.onResume()
        mAdapter = NotesAdapter(notesData)
        recycler_view.adapter = mAdapter
        recycler_view.invalidate()
        mAdapter!!.notifyDataSetChanged()
        (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            view?.windowToken,
            0
        )
        view?.clearFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all -> {
                if (notesData.isNotEmpty()) {
                    val dialog = DeleteDialog()
                    dialog.show(requireFragmentManager(), "tag")
                    dialog.setOnYesNoClick(object : OnYesNoClick {
                        override fun onYesClicked() {
                            mViewModel.deleteAllNotes()
                        }

                        override fun onNoClicked() {
                            dialog.dismiss()
                        }
                    })
                } else Toast.makeText(
                    context,
                    getString(R.string.note_list_empty),
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class DeleteDialog : DialogFragment() {
        private var yesNoClick: OnYesNoClick? = null

        fun setOnYesNoClick(yesNoClick: OnYesNoClick) {
            this.yesNoClick = yesNoClick
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setMessage("Are you sure?")
                    .setPositiveButton(
                        "Yes"
                    ) { _, _ ->
                        yesNoClick?.onYesClicked()
                    }
                    .setNegativeButton(
                        "No"
                    ) { _, _ ->
                        yesNoClick?.onNoClicked()
                    }
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }

        interface OnYesNoClick {
            fun onYesClicked()
            fun onNoClicked()
        }
    }
}