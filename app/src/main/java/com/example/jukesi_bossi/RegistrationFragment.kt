package com.example.jukesi_bossi

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private var selectedDay = 1
    private var selectedMonth = 1
    private var selectedYear = 2000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextName = view.findViewById<EditText>(R.id.editTextName)
        val radioGroupGender = view.findViewById<RadioGroup>(R.id.radioGroupGender)
        val spinnerCourse = view.findViewById<Spinner>(R.id.spinnerCourse)
        val seekBarDifficulty = view.findViewById<SeekBar>(R.id.seekBarDifficulty)
        val textDifficulty = view.findViewById<TextView>(R.id.textDifficulty)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val buttonRegister = view.findViewById<Button>(R.id.buttonRegister)
        val textResult = view.findViewById<TextView>(R.id.textResult)
        val imageZodiac = view.findViewById<ImageView>(R.id.imageZodiac)

        val courses = arrayOf(
            "1 курс",
            "2 курс",
            "3 курс",
            "4 курс",
            "5 курс",
            "6 курс"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            courses
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerCourse.adapter = adapter

        seekBarDifficulty.max = 4
        seekBarDifficulty.progress = 2

        textDifficulty.text = "Сложность: 3"

        seekBarDifficulty.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    textDifficulty.text = "Сложность: ${progress + 1}"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )

        val calendar = java.util.Calendar.getInstance()

        selectedDay = calendar.get(java.util.Calendar.DAY_OF_MONTH)
        selectedMonth = calendar.get(java.util.Calendar.MONTH) + 1
        selectedYear = calendar.get(java.util.Calendar.YEAR)

        calendarView.setOnDateChangeListener { _, year, month, day ->
            selectedYear = year
            selectedMonth = month + 1
            selectedDay = day
        }

        buttonRegister.setOnClickListener {

            val fullName = editTextName.text.toString().trim()

            if (fullName.isEmpty()) {
                editTextName.error = "Введите ФИО"
                return@setOnClickListener
            }

            val gender = when (radioGroupGender.checkedRadioButtonId) {
                R.id.radioMale -> "Мужской"
                R.id.radioFemale -> "Женский"

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Выберите пол",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }
            }

            val course = spinnerCourse.selectedItem.toString()
            val difficulty = seekBarDifficulty.progress + 1

            val birthDate = String.format(
                "%02d.%02d.%04d",
                selectedDay,
                selectedMonth,
                selectedYear
            )

            val zodiac = getZodiac(
                selectedDay,
                selectedMonth
            )

            val player = Player(
                fullName,
                gender,
                course,
                difficulty,
                birthDate,
                zodiac.first
            )

            textResult.text = """
                Игрок зарегистрирован

                ФИО: ${player.fullName}
                Пол: ${player.gender}
                Курс: ${player.course}
                Сложность: ${player.difficulty}
                Дата рождения: ${player.birthDate}
                Знак зодиака: ${player.zodiac}
            """.trimIndent()

            imageZodiac.setImageResource(zodiac.second)
            imageZodiac.visibility = View.VISIBLE
        }
    }

    private fun getZodiac(day: Int, month: Int): Pair<String, Int> {
        return when (month) {
            1 ->
                if (day <= 19)
                    Pair("Козерог", R.drawable.koziyrog)
                else
                    Pair("Водолей", R.drawable.vodolei)

            2 ->
                if (day <= 18)
                    Pair("Водолей", R.drawable.vodolei)
                else
                    Pair("Рыбы", R.drawable.ribi)

            3 ->
                if (day <= 20)
                    Pair("Рыбы", R.drawable.ribi)
                else
                    Pair("Овен", R.drawable.owen)

            4 ->
                if (day <= 19)
                    Pair("Овен", R.drawable.owen)
                else
                    Pair("Телец", R.drawable.telec)

            5 ->
                if (day <= 20)
                    Pair("Телец", R.drawable.telec)
                else
                    Pair("Близнецы", R.drawable.blizneci)

            6 ->
                if (day <= 20)
                    Pair("Близнецы", R.drawable.blizneci)
                else
                    Pair("Рак", R.drawable.rac)

            7 ->
                if (day <= 22)
                    Pair("Рак", R.drawable.rac)
                else
                    Pair("Лев", R.drawable.lev)

            8 ->
                if (day <= 22)
                    Pair("Лев", R.drawable.lev)
                else
                    Pair("Дева", R.drawable.deva)

            9 ->
                if (day <= 22)
                    Pair("Дева", R.drawable.deva)
                else
                    Pair("Весы", R.drawable.vesi)

            10 ->
                if (day <= 22)
                    Pair("Весы", R.drawable.vesi)
                else
                    Pair("Скорпион", R.drawable.skorpion)

            11 ->
                if (day <= 21)
                    Pair("Скорпион", R.drawable.skorpion)
                else
                    Pair("Стрелец", R.drawable.strelec)

            12 ->
                if (day <= 21)
                    Pair("Стрелец", R.drawable.strelec)
                else
                    Pair("Козерог", R.drawable.koziyrog)

            else ->
                Pair("Неизвестно", R.drawable.zodiakesi)
        }
    }
}