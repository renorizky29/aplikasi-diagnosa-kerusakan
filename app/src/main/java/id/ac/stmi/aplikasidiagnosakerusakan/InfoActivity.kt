package id.ac.stmi.aplikasidiagnosakerusakan

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info)

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }
    }
}
