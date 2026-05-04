package database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import database.entity.StudentEntity
import kotlinx.coroutines.flow.Flow


// @Dao menandai interface ini sebagai Data Access Object.
// Room akan otomatis generate class implementasinya saat project di-build.
@Dao
interface StudentDao {

    // @Insert: Room generate query INSERT secara otomatis.
    // onConflict = REPLACE artinya jika ada data dengan id sama, timpa data lama.
    // suspend = harus dipanggil dari coroutine (tidak boleh di main thread).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: StudentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<StudentEntity>): List<Long>

    // @Query: untuk SELECT, kita tulis SQL-nya sendiri.
    // Room memvalidasi query ini saat compile time — typo nama kolom langsung error saat build.
    @Query("SELECT * FROM student ORDER BY namaMhs ASC")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM student WHERE idMhs = :id")
    suspend fun getStudentById(id: Int): StudentEntity?

    @Query("""
        SELECT * FROM student
        WHERE namaMhs LIKE '%' || :keyword || '%' 
        OR nim LIKE '%' || :keyword || '%'
        ORDER BY namaMhs ASC
    """)
    fun searchStudents(keyword: String): Flow<List<StudentEntity>>

    // @Update: Room generate UPDATE berdasarkan primary key (id) secara otomatis.
    // Mengembalikan jumlah baris yang berhasil diperbarui.
    @Update
    suspend fun update(student: StudentEntity): Int

    // delete by id
    @Query("DELETE FROM student WHERE idMhs = :id")
    suspend fun deleteById(id: Int): Int

    // hitung jml mhs
    @Query("SELECT COUNT(*) FROM student")
    suspend fun getStudentCount(): Int




}