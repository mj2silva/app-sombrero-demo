package com.manuelsilva.sombreroapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.manuelsilva.sombreroapp.data.Producto

class MainActivity : AppCompatActivity() {

    private val SPLASH_SCREEN = 4000
    // Variables
    lateinit var topAnim : Animation
    lateinit var bottomAnim : Animation

    lateinit var imageView : ImageView
    lateinit var welcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        // Animaciones
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        // Hooks
        imageView = findViewById(R.id.imageView)
        welcome = findViewById(R.id.textView)

        imageView.animation = topAnim
        welcome.animation = bottomAnim

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, Menu::class.java);
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN.toLong())
    }
}