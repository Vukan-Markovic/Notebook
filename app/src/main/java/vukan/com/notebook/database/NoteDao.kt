package vukan.com.notebook.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(notes: List<NoteEntity>)

    @Delete
    fun deleteNote(noteEntity: NoteEntity?)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): NoteEntity

    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAll(): LiveData<List<NoteEntity>>

    @Query("DELETE FROM notes")
    fun deleteAll(): Int

    @Query("SELECT COUNT(*) FROM notes")
    fun getCount(): Int
}