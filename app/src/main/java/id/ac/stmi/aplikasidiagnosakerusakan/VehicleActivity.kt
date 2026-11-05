package id.ac.stmi.aplikasidiagnosakerusakan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import id.ac.stmi.aplikasidiagnosakerusakan.model.VehicleData

class VehicleActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_VEHICLE = "extra_vehicle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vehicle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom) // Top inset handled by toolbar
            insets
        }

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        val rgVehicleType = findViewById<RadioGroup>(R.id.rgVehicleType)
        val etBrand = findViewById<TextInputEditText>(R.id.etBrand)
        val etModel = findViewById<TextInputEditText>(R.id.etModel)
        val etYear = findViewById<TextInputEditText>(R.id.etYear)
        val btnNext = findViewById<Button>(R.id.btnNextToSymptoms)

        btnNext.setOnClickListener {
            val selectedId = rgVehicleType.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Silakan pilih jenis motor terlebih dahulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val type = when (selectedId) {
                R.id.rbMatic -> "Motor Matic (Skutik)"
                R.id.rbManual -> "Motor Manual / Bebek"
                R.id.rbSport -> "Motor Sport / Trail"
                R.id.rbElectric -> "Motor Listrik"
                else -> "Motor Matic (Skutik)"
            }

            val brand = etBrand.text.toString().trim()
            val model = etModel.text.toString().trim()
            val year = etYear.text.toString().trim()

            val vehicleData = VehicleData(type, brand, model, year)

            val intent = Intent(this, SymptomActivity::class.java).apply {
                putExtra(EXTRA_VEHICLE, vehicleData)
            }
            startActivity(intent)
        }
    }
}
