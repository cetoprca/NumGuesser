package com.cesar.numguesser

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.cesar.numguesser.databinding.GameBinding
import kotlin.random.Random

class Game : AppCompatActivity() {

    lateinit var prefs: SharedPreferences
    var numTries = 0
    var maxNum = 0

    var debugMode = false
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

        loadPreferences()

        loadBehavior()

        debugTextInit()
    }

    fun debugTextInit(){
        gameBinding.debugNumHidden.isVisible = debugMode;
        gameBinding.degubViewNum.isVisible = debugMode

        numRandom = Random.nextInt(0,maxNum)
        gameBinding.debugNumHidden.text = numRandom.toString()
    }

    fun loadPreferences(){
        prefs = getSharedPreferences("AppConfig", MODE_PRIVATE)
        numTries = prefs.getInt("numTries", 5)
        maxNum = prefs.getInt("maxNum", 10)
        debugMode = prefs.getBoolean("debugMode", false)
    }

    fun loadBehavior(){
        gameBinding.guess.setOnClickListener {
            var num = 0
            try {
                num = gameBinding.numInput.text.toString().toInt()
            }catch (e: Exception){
                num = 0
                gameBinding.numInput.setText("0")
            }

            val response = gameLogic(num)
            when(response){
                0 -> gameBinding.answer.text = "algo ha ido mal..."
                1 -> gameBinding.answer.text = "mas pequeño que el numero oculto. Intento $currentTries"
                2 -> gameBinding.answer.text = "mas grande que el numero oculto. Intento $currentTries"
                3 -> gameBinding.answer.text = "invalido... te has quedado sin intentos. El numero era $numRandom"
                4 -> gameBinding.answer.text = "correcto! Has ganado en $currentTries intentos"
            }
        }
    }

    fun gameLogic(num: Int) : Int{
        if (currentTries <= numTries){
            if (!correct){
                currentTries++

                if (num < numRandom){
                    return 1

                }else if (num > numRandom){
                    return 2

                }else{
                    correct = true
                }
            }
        }

        if (currentTries > numTries){
            return 3
        }

        if (correct){
            return 4
        }

        return 0
    }

}