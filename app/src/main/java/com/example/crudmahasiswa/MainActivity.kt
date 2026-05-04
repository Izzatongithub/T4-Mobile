package com.example.crudmahasiswa

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.crudmahasiswa.utils.PrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment())  // default

        findViewById<BottomNavigationView>(R.id.bottomNav)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home    -> loadFragment(HomeFragment())
                    R.id.nav_search    -> loadFragment(SearchFragment())
                    R.id.nav_profile -> loadFragment(ProfileFragment())
                }
                true
            }
    }

    // Helper: ganti Fragment di dalam container
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()                    // mulai transaksi
            .replace(R.id.fragmentContainer, fragment) // ganti isi container
            .commit()                              // jalankan
    }
}