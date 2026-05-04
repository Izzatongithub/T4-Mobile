package com.example.crudmahasiswa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import database.AppDatabase
import database.dao.StudentDao
import database.entity.StudentEntity
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
        val rv = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvMahasiswa)
        rv.layoutManager = LinearLayoutManager(requireContext())

        adapter = StudentAdapter()
        rv.adapter = adapter

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
}