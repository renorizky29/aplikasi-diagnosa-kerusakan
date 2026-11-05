package id.ac.stmi.aplikasidiagnosakerusakan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ivHamburger = findViewById<ImageView>(R.id.ivHamburger)
        val cardDiagnosis = findViewById<MaterialCardView>(R.id.cardDiagnosis)
        val cardInfo = findViewById<MaterialCardView>(R.id.cardInfo)
        val cardGuide = findViewById<MaterialCardView>(R.id.cardGuide)
        val cardHistory = findViewById<MaterialCardView>(R.id.cardHistory)

        ivHamburger.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menu.add(0, 1, 0, "Riwayat Diagnosa")
            popup.menu.add(0, 2, 1, "Panduan Penggunaan")
            popup.menu.add(0, 3, 2, "Informasi Kerusakan")
            popup.menu.add(0, 4, 3, "Tentang Aplikasi")
            popup.menu.add(0, 5, 4, "Keluar (Logout)")

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> {
                        startActivity(Intent(this, HistoryActivity::class.java))
                        true
                    }
                    2 -> {
                        startActivity(Intent(this, GuideActivity::class.java))
                        true
                    }
                    3 -> {
                        startActivity(Intent(this, InfoActivity::class.java))
                        true
                    }
                    4 -> {
                        startActivity(Intent(this, AboutActivity::class.java))
                        true
                    }
                    5 -> {
                        val prefs = getSharedPreferences("motocare_login_pref", Context.MODE_PRIVATE)
                        prefs.edit().clear().apply()
                        val intent = Intent(this, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                        finish()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        cardDiagnosis.setOnClickListener {
            startActivity(Intent(this, VehicleActivity::class.java))
        }

        cardInfo.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
        }

        cardGuide.setOnClickListener {
            startActivity(Intent(this, GuideActivity::class.java))
        }

        cardHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}
