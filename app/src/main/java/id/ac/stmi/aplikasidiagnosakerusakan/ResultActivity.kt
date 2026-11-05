package id.ac.stmi.aplikasidiagnosakerusakan

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import id.ac.stmi.aplikasidiagnosakerusakan.model.DiagnosticResult
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private var result: DiagnosticResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        result = intent.SerializableExtraCompat<DiagnosticResult>(SymptomActivity.EXTRA_RESULT)

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        val tvRiskBadge = findViewById<TextView>(R.id.tvRiskBadge)
        val tvMatchScore = findViewById<TextView>(R.id.tvMatchScore)
        val tvTitle = findViewById<TextView>(R.id.tvDiagnosisTitle)
        val tvDesc = findViewById<TextView>(R.id.tvDiagnosisDesc)
        val tvStopAdvice = findViewById<TextView>(R.id.tvStopAdvice)
        val tvRecommendations = findViewById<TextView>(R.id.tvRecommendations)
        val tvVehicleSummary = findViewById<TextView>(R.id.tvVehicleSummary)
        val tvSymptomsSummary = findViewById<TextView>(R.id.tvSymptomsSummary)

        val btnShare = findViewById<Button>(R.id.btnShare)
        val btnExportPdf = findViewById<Button>(R.id.btnExportPdf)
        val btnReDiagnose = findViewById<Button>(R.id.btnReDiagnose)
        val btnHome = findViewById<Button>(R.id.btnHome)

        if (result != null) {
            tvTitle.text = result!!.matchedRuleTitle
            tvDesc.text = result!!.matchedRuleDescription
            tvStopAdvice.text = result!!.stopVehicleAdvice

            val risk = result!!.riskLevel.uppercase()
            tvRiskBadge.text = "RISIKO: $risk"
            when (risk) {
                "TINGGI" -> tvRiskBadge.setBackgroundColor(Color.parseColor("#EF4444"))
                "SEDANG" -> tvRiskBadge.setBackgroundColor(Color.parseColor("#F59E0B"))
                else -> tvRiskBadge.setBackgroundColor(Color.parseColor("#10B981"))
            }

            if (result!!.totalRuleSymptoms > 0) {
                tvMatchScore.text = "Skor Kecocokan: ${result!!.matchScore}/${result!!.totalRuleSymptoms}"
            } else {
                tvMatchScore.text = "Skor Kecocokan: N/A"
            }

            val recBuilder = StringBuilder()
            for ((index, rec) in result!!.recommendations.withIndex()) {
                recBuilder.append("${index + 1}. $rec\n")
            }
            tvRecommendations.text = recBuilder.toString().trim()

            val v = result!!.vehicleData
            val brandModel = listOf(v.brand, v.model).filter { it.isNotBlank() }.joinToString(" ")
            val vehicleStr = buildString {
                append(v.type)
                if (brandModel.isNotBlank()) append(" • $brandModel")
                if (v.year.isNotBlank()) append(" (${v.year})")
            }
            tvVehicleSummary.text = vehicleStr

            val symptomsStr = result!!.selectedSymptoms.joinToString(", ") { "[${it.id}] ${it.name}" }
            tvSymptomsSummary.text = "Gejala dipilih (${result!!.selectedSymptoms.size}): $symptomsStr"
        }

        btnShare.setOnClickListener {
            shareResult()
        }

        btnExportPdf.setOnClickListener {
            exportPdf()
        }

        btnReDiagnose.setOnClickListener {
            val intent = Intent(this, VehicleActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
    }

    private fun shareResult() {
        val res = result ?: return
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
        val dateStr = sdf.format(Date(res.timestamp))

        val shareText = buildString {
            append("=== LAPORAN DIAGNOSA MOTOCARE ===\n\n")
            append("Tanggal: $dateStr\n")
            append("Kendaraan: ${res.vehicleData.type} ${res.vehicleData.brand} ${res.vehicleData.model}\n")
            append("Gejala: ${res.selectedSymptoms.joinToString(", ") { it.name }}\n\n")
            append("Kemungkinan Kerusakan:\n*${res.matchedRuleTitle}*\n\n")
            append("Tingkat Risiko: ${res.riskLevel}\n")
            append("Penjelasan:\n${res.matchedRuleDescription}\n\n")
            append("Saran Kendaraan:\n${res.stopVehicleAdvice}\n\n")
            append("Rekomendasi:\n")
            res.recommendations.forEachIndexed { i, r -> append("${i + 1}. $r\n") }
            append("\nCatatan: Hasil diagnosa adalah perkiraan awal (sistem pakar sederhana).")
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Hasil Diagnosa MotoCare - ${res.matchedRuleTitle}")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(intent, "Bagikan Hasil Diagnosa via"))
    }

    private fun exportPdf() {
        val res = result ?: return
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
        }
        val titlePaint = Paint().apply {
            color = Color.BLACK
            textSize = 16f
            isFakeBoldText = true
        }
        val subtitlePaint = Paint().apply {
            color = Color.GRAY
            textSize = 13f
            isFakeBoldText = true
        }

        var y = 40f
        canvas.drawText("MOTOCARE - LAPORAN DIAGNOSA KERUSAKAN", 40f, y, titlePaint)
        y += 25f

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("id", "ID"))
        canvas.drawText("Tanggal: ${sdf.format(Date(res.timestamp))}", 40f, y, paint)
        y += 20f

        val v = res.vehicleData
        canvas.drawText("Kendaraan: ${v.type} ${v.brand} ${v.model} (${v.year})", 40f, y, paint)
        y += 30f

        canvas.drawText("KEMUNGKINAN KERUSAKAN:", 40f, y, subtitlePaint)
        y += 20f
        canvas.drawText(res.matchedRuleTitle, 40f, y, titlePaint)
        y += 25f

        canvas.drawText("Tingkat Risiko: ${res.riskLevel}", 40f, y, paint)
        y += 20f
        canvas.drawText("Skor Kecocokan: ${res.matchScore} / ${res.totalRuleSymptoms}", 40f, y, paint)
        y += 30f

        canvas.drawText("PENJELASAN:", 40f, y, subtitlePaint)
        y += 20f
        canvas.drawText(res.matchedRuleDescription, 40f, y, paint)
        y += 30f

        canvas.drawText("SARAN PENGGUNAAN KENDARAAN:", 40f, y, subtitlePaint)
        y += 20f
        canvas.drawText(res.stopVehicleAdvice, 40f, y, paint)
        y += 30f

        canvas.drawText("REKOMENDASI TINDAKAN AWAL:", 40f, y, subtitlePaint)
        y += 20f
        for ((idx, rec) in res.recommendations.withIndex()) {
            canvas.drawText("${idx + 1}. $rec", 50f, y, paint)
            y += 20f
        }

        pdfDocument.finishPage(page)

        val file = File(getExternalFilesDir(null), "MotoCare_Report_${System.currentTimeMillis()}.pdf")
        try {
            val fos = FileOutputStream(file)
            pdfDocument.writeTo(fos)
            fos.close()
            Toast.makeText(this, "PDF berhasil disimpan di:\n${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal membuat PDF: ${e.message}", Toast.LENGTH_LONG).show()
        } finally {
            pdfDocument.close()
        }
    }

    private inline fun <reified T : java.io.Serializable> Intent.SerializableExtraCompat(name: String): T? {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            getSerializableExtra(name, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            getSerializableExtra(name) as? T
        }
    }
}
