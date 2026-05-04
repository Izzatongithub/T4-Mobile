package com.example.crudmahasiswa

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.crudmahasiswa.utils.PrefManager

class ProfileFragment : Fragment() {

    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            prefManager.logout()

            startActivity(Intent(requireContext(), LoginActivity::class.java))

            requireActivity().finish()
        }
    }
}