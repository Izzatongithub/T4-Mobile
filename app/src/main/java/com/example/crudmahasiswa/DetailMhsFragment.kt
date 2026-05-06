package com.example.crudmahasiswa

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import java.io.FileNotFoundException

class DetailMhsFragment : Fragment(R.layout.fragment_detailmhs) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nama = arguments?.getString("nama") ?: ""
        val nim = arguments?.getString("nim") ?: ""

        val tvNama = view.findViewById<TextView>(R.id.tvDetailName)
        val tvNim = view.findViewById<TextView>(R.id.tvDetailNimProdi)
        val etNote = view.findViewById<EditText>(R.id.etNote)
        val btnSimpan = view.findViewById<Button>(R.id.btnSaveNote)
        val btnMuat = view.findViewById<Button>(R.id.btnLoadNote)
        val tvBack = view.findViewById<TextView>(R.id.tvBack)
        val tvStatus = view.findViewById<TextView>(R.id.tvNoteStatus) // tambahin di XML ya

        tvNama.text = nama
        tvNim.text = "NIM: $nim"

        tvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val fileName = "catatan_$nim.txt"

        // SIMPAN CATATAN
        btnSimpan.setOnClickListener {
            val note = etNote.text.toString()
            try {
                requireContext().openFileOutput(fileName, Context.MODE_PRIVATE).use {
                    it.write(note.toByteArray())
                }

                tvStatus.visibility = View.VISIBLE
                tvStatus.text = "✓ Tersimpan (${note.toByteArray().size} bytes)"

                Toast.makeText(requireContext(), "Catatan disimpan", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Gagal menyimpan", Toast.LENGTH_SHORT).show()
            }
        }

        // MUAT CATATAN (BUKAN HAPUS LAGI)
        btnMuat.setOnClickListener {
            try {
                val input = requireContext().openFileInput(fileName)
                val content = input.bufferedReader().use { it.readText() }

                etNote.setText(content)

                tvStatus.visibility = View.VISIBLE
                tvStatus.text = "✓ Dimuat (${content.toByteArray().size} bytes)"

                Toast.makeText(requireContext(), "Catatan dimuat", Toast.LENGTH_SHORT).show()

            } catch (e: FileNotFoundException) {
                etNote.setText("")
                tvStatus.visibility = View.GONE
                Toast.makeText(requireContext(), "Belum ada catatan", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Gagal memuat", Toast.LENGTH_SHORT).show()
            }
        }
    }
}