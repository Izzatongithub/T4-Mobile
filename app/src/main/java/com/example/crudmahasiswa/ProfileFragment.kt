package com.example.crudmahasiswa

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.example.crudmahasiswa.utils.PrefManager
import com.example.crudmahasiswa.utils.SettingsManager

class ProfileFragment : Fragment() {

    private lateinit var prefManager: PrefManager
    private lateinit var settingsManager: SettingsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())
        settingsManager = SettingsManager(requireContext())

        val switchDarkMode = view.findViewById<Switch>(R.id.switchDarkMode)
        val switchFontSize = view.findViewById<Switch>(R.id.switchFontSize)
        val switchNotification = view.findViewById<Switch>(R.id.switchNotification)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        switchDarkMode.isChecked = settingsManager.isDarkMode
        switchFontSize.isChecked = settingsManager.isFontSizeEnabled
        switchNotification.isChecked = settingsManager.isNotificationEnabled

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.isDarkMode = isChecked
        }

        switchFontSize.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.isFontSizeEnabled = isChecked
        }

        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.isNotificationEnabled = isChecked
        }

        btnLogout.setOnClickListener {
            prefManager.logout()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}