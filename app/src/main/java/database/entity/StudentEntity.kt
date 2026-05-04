package database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// tableName = nama tabel yang akan dibuat di SQLite (boleh berbeda dari nama class).
@Entity(tableName = "student")
data class StudentEntity (

    // @PrimaryKey(autoGenerate = true) = kolom id yang auto increment.
    // Sama seperti "id INTEGER PRIMARY KEY AUTOINCREMENT" di SQLite manual.
    @PrimaryKey(autoGenerate = true)
    val idMhs: Int = 0,

    // @ColumnInfo(name = "...") = nama kolom di tabel.
    // Jika nama property sama dengan nama kolom, annotation ini bisa dihilangkan.
    @ColumnInfo(name = "namaMhs")
    val namaMhs: String,

    @ColumnInfo(name = "NIM")
    val nim: String,

    @ColumnInfo(name = "prodi")
    val prodi: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "semester")
    val semester: Int,

    @ColumnInfo(name = "createdAt")
    val createdAt: Long,
)