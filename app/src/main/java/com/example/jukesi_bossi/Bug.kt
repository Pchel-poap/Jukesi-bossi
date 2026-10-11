package com.example.jukesi_bossi

data class Bug(
    val id: Long,
    var x: Float,
    var y: Float,
    val speed: Float,
    val size: Float,
    val type: BugType,
    val points: Int,
    val visualResource: Int,
    var directionX: Float,
    var directionY: Float
)