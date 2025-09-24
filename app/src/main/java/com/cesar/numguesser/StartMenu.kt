package com.cesar.numguesser

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cesar.numguesser.databinding.StartMenuBinding

class StartMenu : AppCompatActivity() {
    private lateinit var startMenuBinding: StartMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadStartMenu()
    }

    fun loadStartMenu(){
        startMenuBinding = StartMenuBinding.inflate(layoutInflater)
        setContentView(startMenuBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(startMenuBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadBehavior()
    }

    fun loadBehavior(){
        startMenuBinding.config.setOnClickListener {
            val intent = Intent(this, ConfigMenu::class.java)
            startActivity(intent)
        }
        startMenuBinding.play.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
    }
}