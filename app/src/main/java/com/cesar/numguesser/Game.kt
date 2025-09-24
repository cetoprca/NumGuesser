package com.cesar.numguesser

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.cesar.numguesser.databinding.ConfigMenuBinding
import com.cesar.numguesser.databinding.GameBinding
import com.cesar.numguesser.databinding.StartMenuBinding
import kotlin.random.Random

class Game : AppCompatActivity() {

    var numTries = 0
    var maxNum = 0

    var currentTries = 0
    var numRandom = 0
    var debugMode = false
    var correct = false
    private lateinit var gameBinding: GameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadGame()
    }

    fun loadGame(){
        gameBinding = GameBinding.inflate(layoutInflater)

        setContentView(gameBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(gameBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val prefs: SharedPreferences = getSharedPreferences("AppConfig", MODE_PRIVATE)
        numTries = prefs.getInt("numTries", 5)
        maxNum = prefs.getInt("maxNum", 10)
        debugMode = prefs.getBoolean("debugMode", false)

        gameBinding.debugNumHidden.isVisible = debugMode;
        gameBinding.degubViewNum.isVisible = debugMode

        numRandom = Random.nextInt(0,maxNum)
        gameBinding.debugNumHidden.text = numRandom.toString()

        gameBinding.guess.setOnClickListener {
            if (currentTries < numTries){
                if (!correct){
                    var num = gameBinding.numInput.text.toString().toInt()

                    currentTries++

                    if (num < numRandom){
                        gameBinding.answer.text = "mas pequeño que el numero oculto. Intento $currentTries"
                    }else if (num > numRandom){
                        gameBinding.answer.text = "mas grande que el numero oculto. Intento $currentTries"
                    }else{
                        correct = true
                    }
                }
            }else{
                gameBinding.answer.text = "invalido... te has quedado sin intentos. El numero era $numRandom"
            }

            if (currentTries == numTries){
                gameBinding.answer.text = "invalido... te has quedado sin intentos. El numero era $numRandom"
            }


            if (correct){
                gameBinding.answer.text = "correcto! Has ganado en $currentTries intentos"
            }
        }
    }

}