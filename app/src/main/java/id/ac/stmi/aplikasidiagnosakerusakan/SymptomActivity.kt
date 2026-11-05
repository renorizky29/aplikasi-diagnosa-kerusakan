package id.ac.stmi.aplikasidiagnosakerusakan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import id.ac.stmi.aplikasidiagnosakerusakan.data.DiagnosticEngine
import id.ac.stmi.aplikasidiagnosakerusakan.data.HistoryRepository
import id.ac.stmi.aplikasidiagnosakerusakan.data.SymptomRepository
import id.ac.stmi.aplikasidiagnosakerusakan.model.DiagnosticResult
import id.ac.stmi.aplikasidiagnosakerusakan.model.Symptom
import id.ac.stmi.aplikasidiagnosakerusakan.model.VehicleData
import java.util.UUID

class SymptomActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }

    private lateinit var vehicleData: VehicleData
    private val symptoms = SymptomRepository.getAllSymptoms()
    private val selectedSymptomIds = mutableSetOf<String>()
    private lateinit var adapter: SymptomAdapter
    private lateinit var tvSelectedCount: TextView
    private lateinit var etManualSymptoms: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_symptom)

        vehicleData = intent.SerializableExtraCompat(VehicleActivity.EXTRA_VEHICLE) as? VehicleData
            ?: VehicleData("Motor Matic (Skutik)", "", "", "")

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        etManualSymptoms = findViewById(R.id.etManualSymptoms)
        tvSelectedCount = findViewById(R.id.tvSelectedCount)
        val rvSymptoms = findViewById<RecyclerView>(R.id.rvSymptoms)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val btnProcess = findViewById<Button>(R.id.btnProcessDiagnosis)

        adapter = SymptomAdapter(symptoms) { symptom, isChecked ->
            if (isChecked) {
                selectedSymptomIds.add(symptom.id)
            } else {
                selectedSymptomIds.remove(symptom.id)
            }
            updateSelectedCount()
        }

        rvSymptoms.layoutManager = LinearLayoutManager(this)
        rvSymptoms.adapter = adapter

        btnReset.setOnClickListener {
            etManualSymptoms.setText("")
            selectedSymptomIds.clear()
            adapter.notifyDataSetChanged()
            updateSelectedCount()
        }

        btnProcess.setOnClickListener {
            val manualText = etManualSymptoms.text.toString().trim()
            if (manualText.isEmpty() && selectedSymptomIds.isEmpty()) {
                Toast.makeText(this, "Silakan tulis keluhan manual atau pilih minimal satu gejala tambahan!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val evalResult = DiagnosticEngine.evaluateCombined(manualText, selectedSymptomIds.toList())
            val matchedRule = evalResult.matchedRule

            val ruleTitle: String
            val ruleDesc: String
            val risk: String
            val recs: List<String>
            val stopAdvice: String
            val mechanicNeeded: Boolean

            if (matchedRule != null) {
                ruleTitle = matchedRule.title
                ruleDesc = matchedRule.description
                risk = matchedRule.riskLevel
                recs = matchedRule.recommendations
                stopAdvice = matchedRule.stopVehicleAdvice
                mechanicNeeded = matchedRule.mechanicCheckNeeded
            } else {
                ruleTitle = "Belum Dapat Ditentukan"
                ruleDesc = "Kombinasi gejala manual dan pilihan belum cukup spesifik atau tidak cocok dengan aturan diagnosa yang tersedia."
                risk = "Rendah"
                recs = listOf("Lakukan pemeriksaan umum pada komponen motor.", "Konsultasikan dengan mekanik terdekat.")
                stopAdvice = "Kendaraan dapat dikendarai dengan hati-hati menuju bengkel."
                mechanicNeeded = true
            }

            val selectedSymptomsList = mutableListOf<Symptom>()
            if (manualText.isNotEmpty()) {
                selectedSymptomsList.add(
                    Symptom("M01", "Input Manual Pengguna", manualText, "Gejala yang diinput manual")
                )
            }
            selectedSymptomsList.addAll(symptoms.filter { selectedSymptomIds.contains(it.id) })

            val diagnosticResult = DiagnosticResult(
                id = UUID.randomUUID().toString(),
                timestamp = System.currentTimeMillis(),
                vehicleData = vehicleData,
                selectedSymptoms = selectedSymptomsList,
                matchedRuleTitle = ruleTitle,
                matchedRuleDescription = ruleDesc,
                riskLevel = risk,
                recommendations = recs,
                stopVehicleAdvice = stopAdvice,
                mechanicCheckNeeded = mechanicNeeded,
                matchScore = evalResult.matchScore,
                totalRuleSymptoms = evalResult.totalRuleSymptoms
            )

            HistoryRepository.saveResult(this, diagnosticResult)

            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra(EXTRA_RESULT, diagnosticResult)
            }
            startActivity(intent)
        }
    }

    private fun updateSelectedCount() {
        tvSelectedCount.text = "${selectedSymptomIds.size} gejala tambahan dipilih"
    }

    private inline fun <reified T : java.io.Serializable> Intent.SerializableExtraCompat(name: String): T? {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            getSerializableExtra(name, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            getSerializableExtra(name) as? T
        }
    }

    inner class SymptomAdapter(
        private val list: List<Symptom>,
        private val onCheckChanged: (Symptom, Boolean) -> Unit
    ) : RecyclerView.Adapter<SymptomAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cbSymptom: CheckBox = view.findViewById(R.id.cbSymptom)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_symptom, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val symptom = list[position]
            holder.cbSymptom.text = "[${symptom.id}] ${symptom.name}\nKategori: ${symptom.category}"
            holder.cbSymptom.setOnCheckedChangeListener(null)
            holder.cbSymptom.isChecked = selectedSymptomIds.contains(symptom.id)

            holder.cbSymptom.setOnCheckedChangeListener { _, isChecked ->
                onCheckChanged(symptom, isChecked)
            }

            holder.itemView.setOnClickListener {
                holder.cbSymptom.isChecked = !holder.cbSymptom.isChecked
            }
        }

        override fun getItemCount(): Int = list.size
    }
}
