package com.example.crudmahasiswa

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import database.AppDatabase
import database.entity.StudentEntity
import kotlinx.coroutines.launch

class FormFragment : Fragment(R.layout.fragment_form) {

    private var studentId: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etNamaMhs = view.findViewById<EditText>(R.id.etNamaMhs)
        val etNim = view.findViewById<EditText>(R.id.etNim)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etSemester = view.findViewById<EditText>(R.id.etSemester)
        val spProdi = view.findViewById<Spinner>(R.id.spProdi)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        val dao = AppDatabase.getDatabase(requireContext()).studentDao()

        // Isi Spinner
        val prodiList = listOf("Informatika", "Sistem Informasi", "Teknik Komputer")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, prodiList)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spProdi.adapter = adapterSpinner

        // Ambil ID (untuk mode edit)
        studentId = arguments?.getInt("studentId") ?: -1

        // Kalau edit → load data
        if (studentId != -1) {
            lifecycleScope.launch {
                val student = dao.getStudentById(studentId)
                student?.let {
                    etNamaMhs.setText(it.namaMhs)
                    etNim.setText(it.nim)
                    etEmail.setText(it.email)
                    etSemester.setText(it.semester.toString())

                    val index = prodiList.indexOf(it.prodi)
                    spProdi.setSelection(index)
                }
            }
        }

        // Tombol SIMPAN
        btnSave.setOnClickListener {

            val namaMhs = etNamaMhs.text.toString()
            val nim = etNim.text.toString()
            val email = etEmail.text.toString()
            val prodi = spProdi.selectedItem.toString()
            val semester = etSemester.text.toString()

            // VALIDASI
            if (namaMhs.isEmpty() || nim.isEmpty() || email.isEmpty() || semester.isEmpty()) {
                Toast.makeText(requireContext(), "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                if (studentId == -1) {
                    // INSERT
                    dao.insert(
                        StudentEntity(
                            namaMhs = namaMhs,
                            nim = nim,
                            email = email,
                            semester = semester.toInt(),
                            prodi = prodi,
                            createdAt = System.currentTimeMillis()
                        )
                    )
                } else {
                    // UPDATE
                    dao.update(
                        StudentEntity(
                            idMhs = studentId,
                            namaMhs = namaMhs,
                            nim = nim,
                            email = email,
                            semester = semester.toInt(),
                            prodi = prodi,
                            createdAt = System.currentTimeMillis()
                        )
                    )
                }

                parentFragmentManager.popBackStack()
            }
        }
    }
}