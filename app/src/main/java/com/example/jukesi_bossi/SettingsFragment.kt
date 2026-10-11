package com.example.jukesi_bossi

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment(
    R.layout.fragment_settings
) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        val seekSpeed =
            view.findViewById<SeekBar>(
                R.id.seekSpeed
            )

        val seekMaxBugs =
            view.findViewById<SeekBar>(
                R.id.seekMaxBugs
            )

        val seekBonusInterval =
            view.findViewById<SeekBar>(
                R.id.seekBonusInterval
            )

        val seekRoundDuration =
            view.findViewById<SeekBar>(
                R.id.seekRoundDuration
            )

        val textSpeed =
            view.findViewById<TextView>(
                R.id.textSpeed
            )

        val textMaxBugs =
            view.findViewById<TextView>(
                R.id.textMaxBugs
            )

        val textBonusInterval =
            view.findViewById<TextView>(
                R.id.textBonusInterval
            )

        val textRoundDuration =
            view.findViewById<TextView>(
                R.id.textRoundDuration
            )

        seekSpeed.progress =
            GameSettings.speed - 1

        seekMaxBugs.progress =
            GameSettings.maxBugs - 1

        seekBonusInterval.progress =
            GameSettings.bonusInterval - 5

        seekRoundDuration.progress =
            GameSettings.roundDuration - 30

        textSpeed.text =
            "Скорость игры: ${GameSettings.speed}"

        textMaxBugs.text =
            "Максимум тараканов: ${GameSettings.maxBugs}"

        textBonusInterval.text =
            "Интервал бонусов: ${GameSettings.bonusInterval} сек."

        textRoundDuration.text =
            "Длительность раунда: ${GameSettings.roundDuration} сек."

        seekSpeed.setOnSeekBarChangeListener(
            listener { progress ->
                GameSettings.speed =
                    progress + 1

                textSpeed.text =
                    "Скорость игры: ${GameSettings.speed}"
            }
        )

        seekMaxBugs.setOnSeekBarChangeListener(
            listener { progress ->
                GameSettings.maxBugs =
                    progress + 1

                textMaxBugs.text =
                    "Максимум тараканов: ${GameSettings.maxBugs}"
            }
        )

        seekBonusInterval.setOnSeekBarChangeListener(
            listener { progress ->
                GameSettings.bonusInterval =
                    progress + 5

                textBonusInterval.text =
                    "Интервал бонусов: ${GameSettings.bonusInterval} сек."
            }
        )

        seekRoundDuration.setOnSeekBarChangeListener(
            listener { progress ->
                GameSettings.roundDuration =
                    progress + 30

                textRoundDuration.text =
                    "Длительность раунда: ${GameSettings.roundDuration} сек."
            }
        )
    }

    private fun listener(
        action: (Int) -> Unit
    ): SeekBar.OnSeekBarChangeListener {

        return object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                action(progress)
            }

            override fun onStartTrackingTouch(
                seekBar: SeekBar?
            ) {
            }

            override fun onStopTrackingTouch(
                seekBar: SeekBar?
            ) {
            }
        }
    }
}