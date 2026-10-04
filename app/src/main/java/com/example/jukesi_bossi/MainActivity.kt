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
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private var selectedDay = 1
    private var selectedMonth = 1
    private var selectedYear = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_registration)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val spinnerCourse = findViewById<Spinner>(R.id.spinnerCourse)
        val seekBarDifficulty = findViewById<SeekBar>(R.id.seekBarDifficulty)
        val textDifficulty = findViewById<TextView>(R.id.textDifficulty)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val textResult = findViewById<TextView>(R.id.textResult)
        val imageZodiac = findViewById<ImageView>(R.id.imageZodiac)

        val courses = arrayOf(
            "1 курс",
            "2 курс",
            "3 курс",
            "4 курс"
        )

        val adapter = ArrayAdapter(
            this,
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

        val currentCalendar = java.util.Calendar.getInstance()

        selectedDay = currentCalendar.get(java.util.Calendar.DAY_OF_MONTH)
        selectedMonth = currentCalendar.get(java.util.Calendar.MONTH) + 1
        selectedYear = currentCalendar.get(java.util.Calendar.YEAR)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedYear = year
            selectedMonth = month + 1
            selectedDay = dayOfMonth
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
                        this,
                        "Выберите пол",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }
            }

            val course = spinnerCourse.selectedItem.toString()
            val difficulty = seekBarDifficulty.progress + 1

            val birthDate =
                String.format(
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
                fullName = fullName,
                gender = gender,
                course = course,
                difficulty = difficulty,
                birthDate = birthDate,
                zodiac = zodiac.first
            )

            val result =
                """
                Игрок зарегистрирован
                
                ФИО: ${player.fullName}
                Пол: ${player.gender}
                Курс: ${player.course}
                Сложность: ${player.difficulty}
                Дата рождения: ${player.birthDate}
                Знак зодиака: ${player.zodiac}
                """.trimIndent()

            textResult.text = result

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