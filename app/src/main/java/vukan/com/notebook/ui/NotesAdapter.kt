package vukan.com.notebook.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import vukan.com.notebook.database.NoteEntity
import vukan.com.notebook.R
import kotlinx.android.synthetic.main.note_list_item.view.*
import vukan.com.notebook.MainFragmentDirections

class NotesAdapter : PagedListAdapter<NoteEntity, NotesAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note: NoteEntity? = getItem(position)
        holder.view.note_text.text = note?.text

        holder.view.note_text.setOnClickListener {
            it.findNavController()
                .navigate(MainFragmentDirections.mainToEditorFragmentAction(note?.id!!))
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<NoteEntity>() {
            override fun areItemsTheSame(
                oldNote: NoteEntity,
                newNote: NoteEntity
            ) = oldNote.id == newNote.id

            override fun areContentsTheSame(
                oldNote: NoteEntity,
                newNote: NoteEntity
            ) = oldNote == newNote
        }
    }

    class NoteViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}