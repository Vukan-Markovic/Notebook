package vukan.com.notebook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NoteEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME: String = "AppDatabase.db"
        @kotlin.jvm.Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null)
                synchronized(LOCK) {
                    if (instance == null)
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                }

            return instance
        }
    }

    abstract fun noteDao(): NoteDao
}