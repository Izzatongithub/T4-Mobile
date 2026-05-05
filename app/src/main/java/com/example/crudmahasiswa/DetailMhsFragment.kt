// D:/AndroidStudioProjects/CRUDMahasiswa/app/src/main/java/com/example/crudmahasiswa/DetailMhsFragment.kt

package com.example.crudmahasiswa

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.crudmahasiswa.utils.FileHelper

class DetailMhsFragment : Fragment(R.layout.fragment_detailmhs) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nama = arguments?.getString("nama") ?: ""
        val nim = arguments?.getString("nim") ?: ""

        // Hubungkan ke ID yang benar sesuai XML
        val tvNama = view.findViewById<TextView>(R.id.tvDetailName)
        val tvNim = view.findViewById<TextView>(R.id.tvDetailNimProdi)
        val etNote = view.findViewById<EditText>(R.id.etNote)
        val btnSave = view.findViewById<Button>(R.id.btnSaveNote)
        val btnDelete = view.findViewById<Button>(R.id.btnLoadNote) // Kita akan ubah ini menjadi tombol hapus di XML
        val tvBack = view.findViewById<TextView>(R.id.tvBack)

        tvNama.text = nama
        tvNim.text = "NIM: $nim"

        // Tombol kembali
        tvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Load note saat pertama dibuka
        val savedNote = FileHelper.loadNote(requireContext(), nim)
        etNote.setText(savedNote)

        // Save note
        btnSave.setOnClickListener {
            val note = etNote.text.toString()
            FileHelper.saveNote(requireContext(), nim, note)
            Toast.makeText(requireContext(), "Catatan disimpan", Toast.LENGTH_SHORT).show()
        }

        // Delete note (Menggunakan btnLoadNote dari XML sebagai tombol hapus)
        btnDelete.setOnClickListener {
            val deleted = FileHelper.deleteNote(requireContext(), nim)
            if (deleted) {
                etNote.setText("")
                Toast.makeText(requireContext(), "Catatan dihapus", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Tidak ada catatan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}