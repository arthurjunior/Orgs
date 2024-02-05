package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import br.com.alura.orgs.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#94a84e")
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,ListaProdutosActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}