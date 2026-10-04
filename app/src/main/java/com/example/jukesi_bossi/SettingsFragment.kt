package com.example.jukesi_bossi

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSeekBar(
            view,
            R.id.seekSpeed,
            R.id.textSpeed,
            1,
            "Скорость игры: "
        )

        setupSeekBar(
            view,
            R.id.seekMaxBugs,
            R.id.textMaxBugs,
            1,
            "Максимум тараканов: "
        )

        setupSeekBar(
            view,
            R.id.seekBonusInterval,
            R.id.textBonusInterval,
            5,
            "Интервал бонусов: ",
            " сек."
        )

        setupSeekBar(
            view,
            R.id.seekRoundDuration,
            R.id.textRoundDuration,
            30,
            "Длительность раунда: ",
            " сек."
        )
    }

    private fun setupSeekBar(
        view: View,
        seekBarId: Int,
        textViewId: Int,
        offset: Int,
        prefix: String,
        suffix: String = ""
    ) {
        val seekBar = view.findViewById<SeekBar>(seekBarId)
        val textView = view.findViewById<TextView>(textViewId)

        textView.text = "$prefix${seekBar.progress + offset}$suffix"

        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    textView.text =
                        "$prefix${progress + offset}$suffix"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )
    }
}