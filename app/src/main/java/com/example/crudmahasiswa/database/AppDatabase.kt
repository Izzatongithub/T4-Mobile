package com.example.crudmahasiswa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.crudmahasiswa.database.dao.StudentDao
import com.example.crudmahasiswa.database.entity.StudentEntity


// @Database mendaftarkan semua Entity dan menentukan versi database.
// Jika struktur tabel berubah (tambah kolom, dll), naikkan version dan buat Migration.
@Database(
    entities = [StudentEntity::class],  // daftar semua tabel
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Room akan generate implementasi fungsi ini secara otomatis.
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Singleton: pastikan hanya ada satu instance database di seluruh app.
        // @Volatile memastikan perubahan INSTANCE langsung terlihat oleh semua thread.
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"  // nama file .db yang tersimpan di /data/data/package/databases/
                )
                    .fallbackToDestructiveMigration()  // untuk development: hapus & buat ulang jika versi naik
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}