package com.example.jukesi_bossi

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var gameView: BugGameView
    private lateinit var gamePanel: LinearLayout
    private lateinit var resultPanel: LinearLayout
    private lateinit var textScore: TextView
    private lateinit var textTimer: TextView
    private lateinit var textResult: TextView

    private var timer: CountDownTimer? = null

    private var score = 0
    private var hits = 0
    private var misses = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameView = view.findViewById(R.id.gameView)
        gamePanel = view.findViewById(R.id.gamePanel)
        resultPanel = view.findViewById(R.id.resultPanel)
        textScore = view.findViewById(R.id.textScore)
        textTimer = view.findViewById(R.id.textTimer)
        textResult = view.findViewById(R.id.textResult)

        val buttonRestart =
            view.findViewById<Button>(R.id.buttonRestart)

        val buttonGameMenu =
            view.findViewById<Button>(R.id.buttonGameMenu)

        gameView.onHit = { points ->
            score += points
            hits++

            textScore.text = "Очки: $score"
        }

        gameView.onMiss = { penalty ->
            score -= penalty
            misses++

            textScore.text = "Очки: $score"
        }

        buttonRestart.setOnClickListener {
            startRound()
        }

        buttonGameMenu.setOnClickListener {
            showGameMenu(buttonGameMenu)
        }

        startRound()
    }

    private fun startRound() {
        timer?.cancel()

        score = 0
        hits = 0
        misses = 0

        textScore.text = "Очки: 0"

        resultPanel.visibility = View.GONE
        gamePanel.visibility = View.VISIBLE

        gameView.startGame()

        val duration =
            GameSettings.roundDuration * 1000L

        timer = object : CountDownTimer(
            duration,
            1000
        ) {
            override fun onTick(
                millisUntilFinished: Long
            ) {
                val seconds =
                    (millisUntilFinished + 999) / 1000

                textTimer.text =
                    "Время: $seconds"
            }

            override fun onFinish() {
                finishRound()
            }
        }.start()
    }

    private fun finishRound() {
        gameView.stopGame()

        gamePanel.visibility = View.GONE
        resultPanel.visibility = View.VISIBLE

        val totalShots = hits + misses

        val accuracy = if (totalShots == 0) {
            0.0
        } else {
            hits.toDouble() /
                    totalShots.toDouble() * 100.0
        }

        textResult.text = """
            Очки: $score
            Попадания: $hits
            Промахи: $misses
            Точность: ${String.format("%.1f", accuracy)}%
        """.trimIndent()
    }

    private fun showGameMenu(button: View) {
        val popup = PopupMenu(
            requireContext(),
            button
        )

        popup.menu.add(
            0,
            1,
            0,
            "Правила игры"
        )

        popup.menu.add(
            0,
            2,
            1,
            "Авторы"
        )

        popup.menu.add(
            0,
            3,
            2,
            "Настройки"
        )

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                1 -> {
                    (activity as? MainActivity)
                        ?.openTab(2)
                    true
                }

                2 -> {
                    (activity as? MainActivity)
                        ?.openTab(3)
                    true
                }

                3 -> {
                    (activity as? MainActivity)
                        ?.openTab(4)
                    true
                }

                else -> false
            }
        }

        popup.show()
    }

    override fun onResume() {
        super.onResume()

        if (GameSettings.restartGame) {
            GameSettings.restartGame = false
            startRound()
        }
    }

    override fun onDestroyView() {
        timer?.cancel()
        gameView.stopGame()

        super.onDestroyView()
    }
}