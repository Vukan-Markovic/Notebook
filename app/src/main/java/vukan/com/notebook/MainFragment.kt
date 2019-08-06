package vukan.com.notebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import vukan.com.notebook.ui.NotesAdapter
import vukan.com.notebook.utilities.DeleteDialog
import vukan.com.notebook.viewmodel.MainViewModel

class MainFragment : Fragment() {
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

        mAdapter = NotesAdapter()

        mViewModel.mNotes?.observe(this, Observer {
            mAdapter!!.submitList(it)
        })

        recycler_view.adapter = mAdapter

        fabAddNote.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.mainToEditorFragmentAction())
        }
    }

    override fun onResume() {
        super.onResume()

        (context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
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
                if (mViewModel.mNotes?.value?.size != 0) {
                    val dialog = DeleteDialog()
                    dialog.show(requireFragmentManager(), "tag")
                    dialog.setOnYesNoClick(object : DeleteDialog.OnYesNoClick {
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
}