package vukan.com.notebook.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import vukan.com.notebook.database.NoteEntity
import vukan.com.notebook.R
import kotlinx.android.synthetic.main.note_list_item.view.*
import vukan.com.notebook.MainFragmentDirections

class NotesAdapter(notesData: MutableList<NoteEntity>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val mNotes: List<NoteEntity> = notesData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note: NoteEntity = mNotes[position]
        holder.view.note_text.text = note.text

        holder.view.note_text.setOnClickListener {
            it.findNavController()
                .navigate(MainFragmentDirections.mainToEditorFragmentAction(note.id))
        }
    }

    class NoteViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}