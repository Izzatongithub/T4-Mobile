package com.example.crudmahasiswa

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudmahasiswa.database.AppDatabase
import com.example.crudmahasiswa.database.dao.StudentDao
import com.example.crudmahasiswa.database.entity.StudentEntity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var studentDao: StudentDao
    private lateinit var adapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi DAO
        val db = AppDatabase.getDatabase(requireContext())
        studentDao = db.studentDao()

        // Setup RecyclerView
        val rvMahasiswa = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvMahasiswa)


        adapter = StudentAdapter(

            onEdit = { student ->
                val fragment = FormFragment().apply {
                    arguments = Bundle().apply {
                        putInt("studentId", student.idMhs)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            },

            onDelete = { student ->
                // Langkah 7: Tampilkan dialog konfirmasi hapus
                showDeleteConfirmationDialog(student.idMhs, student.namaMhs)
            },

            onClick = { student ->
                val fragment: DetailMhsFragment = DetailMhsFragment().apply {
                    arguments = Bundle().apply {
                        putString("nama", student.namaMhs)
                        putString("nim", student.nim)
                    }
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        )

        rvMahasiswa.layoutManager = LinearLayoutManager(requireContext())
        rvMahasiswa.adapter = adapter

        // Load data dari Room
        viewLifecycleOwner.lifecycleScope.launch {
            studentDao.getAllStudents().collect { list ->
                adapter.setData(list)
            }
        }

        // Insert sample data jika kosong
        viewLifecycleOwner.lifecycleScope.launch {
            val count = studentDao.getStudentCount()
            if (count == 0) {
                studentDao.insert(
                    StudentEntity(
                        namaMhs = "Budi",
                        nim = "12345",
                        prodi = "infor",
                        email = "ggg",
                        semester = 2,
                        createdAt = System.currentTimeMillis()
                    )
                )
            }
        }

        // FAB tambah data
//        val fab = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAdd)
//        fab.setOnClickListener {
//            findNavController().navigate(R.id.formFragment)
//        }

        // FAB tambah data
        val fab = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FormFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showDeleteConfirmationDialog(studentId: Int, studentName: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Hapus Data?")
        builder.setMessage("Hapus \"$studentName\"? Tindakan ini tidak dapat dibatalkan.")

        builder.setPositiveButton("Hapus") { dialog, _ ->
            // Proses hapus dari database menggunakan background thread
            lifecycleScope.launch {
                studentDao.deleteById(studentId)
                Toast.makeText(requireContext(), "$studentName berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss() // Tutup pop-up jika batal
        }

        val dialog = builder.create()
        dialog.show()
    }
}
