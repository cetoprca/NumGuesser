package com.cesar.numguesser

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cesar.numguesser.databinding.ConfigMenuBinding
import com.cesar.numguesser.databinding.GameBinding
import com.cesar.numguesser.databinding.StartMenuBinding

class ConfigMenu : AppCompatActivity() {

    var numTries = 0
    var maxNum = 0
    var debugMode = false

    private lateinit var configMenuBinding: ConfigMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadConfigMenu()
    }

    fun loadConfigMenu(){
        configMenuBinding = ConfigMenuBinding.inflate(layoutInflater)

        setContentView(configMenuBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(configMenuBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs: SharedPreferences = getSharedPreferences("AppConfig", MODE_PRIVATE)
        numTries = prefs.getInt("numTries", 5)
        maxNum = prefs.getInt("maxNum", 10)
        debugMode = prefs.getBoolean("debugMode", false)

        val numTriesInput = configMenuBinding.numTriesInput
        val maxNumInput = configMenuBinding.maxNumInput
        val debugModeInput = configMenuBinding.debugMode

        numTriesInput.setText(numTries.toString())
        maxNumInput.setText(maxNum.toString())
        debugModeInput.isChecked = debugMode

        configMenuBinding.back.setOnClickListener {
            finish()
        }

        configMenuBinding.apply.setOnClickListener {
            prefs.edit()
                .putInt("numTries", numTriesInput.text.toString().toInt())
                .putInt("maxNum", maxNumInput.text.toString().toInt())
                .putBoolean("debugMode", debugModeInput.isChecked)
                .apply()

            finish()
        }
    }
}