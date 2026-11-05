package id.ac.stmi.aplikasidiagnosakerusakan

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.stmi.aplikasidiagnosakerusakan.data.HistoryRepository
import id.ac.stmi.aplikasidiagnosakerusakan.model.DiagnosticResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryActivity : AppCompatActivity() {

    private lateinit var rvHistory: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var svHistory: SearchView
    private lateinit var adapter: HistoryAdapter
    private var fullHistoryList = listOf<DiagnosticResult>()
    private var filteredHistoryList = listOf<DiagnosticResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        rvHistory = findViewById(R.id.rvHistory)
        tvEmpty = findViewById(R.id.tvEmptyHistory)
        svHistory = findViewById(R.id.svHistory)
        val btnClear = findViewById<Button>(R.id.btnClearHistory)

        loadHistory()

        svHistory.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        btnClear.setOnClickListener {
            if (fullHistoryList.isEmpty()) return@setOnClickListener
            AlertDialog.Builder(this)
                .setTitle("Hapus Riwayat")
                .setMessage("Apakah Anda yakin ingin menghapus seluruh riwayat diagnosa?")
                .setPositiveButton("Hapus") { _, _ ->
                    HistoryRepository.clearHistory(this)
                    loadHistory()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun loadHistory() {
        fullHistoryList = HistoryRepository.getHistory(this)
        filteredHistoryList = fullHistoryList
        updateListUI()
    }

    private fun filterList(query: String?) {
        val q = query?.lowercase(Locale.getDefault())?.trim() ?: ""
        filteredHistoryList = if (q.isEmpty()) {
            fullHistoryList
        } else {
            fullHistoryList.filter { item ->
                item.matchedRuleTitle.lowercase(Locale.getDefault()).contains(q) ||
                        item.vehicleData.type.lowercase(Locale.getDefault()).contains(q) ||
                        item.vehicleData.brand.lowercase(Locale.getDefault()).contains(q) ||
                        item.vehicleData.model.lowercase(Locale.getDefault()).contains(q) ||
                        item.selectedSymptoms.any { it.name.lowercase(Locale.getDefault()).contains(q) }
            }
        }
        updateListUI()
    }

    private fun updateListUI() {
        if (filteredHistoryList.isEmpty()) {
            rvHistory.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        } else {
            rvHistory.visibility = View.VISIBLE
            tvEmpty.visibility = View.GONE

            adapter = HistoryAdapter(filteredHistoryList) { result ->
                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra(SymptomActivity.EXTRA_RESULT, result)
                }
                startActivity(intent)
            }
            rvHistory.layoutManager = LinearLayoutManager(this)
            rvHistory.adapter = adapter
        }
    }

    inner class HistoryAdapter(
        private val list: List<DiagnosticResult>,
        private val onItemClick: (DiagnosticResult) -> Unit
    ) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvDate: TextView = view.findViewById(R.id.tvHistoryDate)
            val tvRisk: TextView = view.findViewById(R.id.tvHistoryRisk)
            val tvTitle: TextView = view.findViewById(R.id.tvHistoryTitle)
            val tvVehicle: TextView = view.findViewById(R.id.tvHistoryVehicle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = list[position]
            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
            holder.tvDate.text = sdf.format(Date(item.timestamp))

            val risk = item.riskLevel.uppercase()
            holder.tvRisk.text = risk
            when (risk) {
                "TINGGI" -> holder.tvRisk.setBackgroundColor(Color.parseColor("#EF4444"))
                "SEDANG" -> holder.tvRisk.setBackgroundColor(Color.parseColor("#F59E0B"))
                else -> holder.tvRisk.setBackgroundColor(Color.parseColor("#10B981"))
            }

            holder.tvTitle.text = item.matchedRuleTitle

            val v = item.vehicleData
            val brandModel = listOf(v.brand, v.model).filter { it.isNotBlank() }.joinToString(" ")
            holder.tvVehicle.text = "${v.type}${if (brandModel.isNotBlank()) " • $brandModel" else ""}"

            holder.itemView.setOnClickListener {
                onItemClick(item)
            }
        }

        override fun getItemCount(): Int = list.size
    }
}
