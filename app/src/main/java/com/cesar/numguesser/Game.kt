package com.cesar.numguesser

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cesar.numguesser.databinding.ConfigMenuBinding
import com.cesar.numguesser.databinding.GameBinding
import com.cesar.numguesser.databinding.StartMenuBinding
import kotlin.random.Random

class Game : AppCompatActivity() {

    var numTries = 0
    var maxNum = 0

    var currentTries = 0
    var numRandom = 0
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

        numRandom = Random.nextInt(0,maxNum)

        gameBinding.guess.setOnClickListener {
            if (currentTries < numTries){
                if (!correct){
                    check()
                }
                if (correct){
                    gameBinding.answer.text = "Correcto!\nHas ganado!\nHas necesitado $currentTries intentos"
                }
            }else{
                gameBinding.answer.text = "Te has quedado sin intentos\nEl numero era $numRandom"
            }
        }
    }

    fun check(){
        val num = gameBinding.numInput.text.toString().toInt()
        if (num < numRandom){
            gameBinding.answer.text = "mas pequeño que el numero oculto. Intento $currentTries"
            currentTries++
        }else if (num > numRandom){
            gameBinding.answer.text = "mas grande que el numero oculto. Intento $currentTries"
            currentTries++
        }else {
            currentTries++
            correct = true
        }
    }
}