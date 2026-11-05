package id.ac.stmi.aplikasidiagnosakerusakan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val PREF_NAME = "motocare_login_pref"
        private const val KEY_REMEMBER = "remember_me"
        private const val KEY_EMAIL = "saved_email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val isRemembered = prefs.getBoolean(KEY_REMEMBER, false)
        val savedEmail = prefs.getString(KEY_EMAIL, "") ?: ""

        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val cbRememberMe = findViewById<CheckBox>(R.id.cbRememberMe)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGuest = findViewById<Button>(R.id.btnGuest)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        if (isRemembered && savedEmail.isNotEmpty()) {
            etEmail.setText(savedEmail)
            cbRememberMe.isChecked = true
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cbRememberMe.isChecked) {
                prefs.edit().putBoolean(KEY_REMEMBER, true).putString(KEY_EMAIL, email).apply()
            } else {
                prefs.edit().clear().apply()
            }

            Toast.makeText(this, "Login berhasil! Selamat datang, $email", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnGuest.setOnClickListener {
            Toast.makeText(this, "Masuk sebagai Tamu", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvForgotPassword.setOnClickListener {
            val resetInput = TextInputEditText(this).apply {
                hint = "Masukkan email Anda"
                inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
            AlertDialog.Builder(this)
                .setTitle("Lupa Password")
                .setMessage("Masukkan email akun Anda untuk menerima instruksi pemulihan password.")
                .setView(resetInput)
                .setPositiveButton("Kirim") { _, _ ->
                    val emailVal = resetInput.text.toString().trim()
                    if (emailVal.isNotEmpty()) {
                        Toast.makeText(this, "Instruksi pemulihan telah dikirim ke $emailVal", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
