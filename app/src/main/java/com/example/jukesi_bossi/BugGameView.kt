package com.example.jukesi_bossi

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

class BugGameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val bugs = mutableListOf<Bug>()
    private val random = Random.Default
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmapCache = mutableMapOf<Int, Bitmap>()

    private var nextId = 1L
    private var running = false
    private var lastFrameTime = 0L

    var onHit: ((Int) -> Unit)? = null
    var onMiss: ((Int) -> Unit)? = null

    fun startGame() {
        bugs.clear()
        nextId = 1L

        repeat(GameSettings.maxBugs) { index ->
            if (index < 3) {
                addBug(BugType.values()[index])
            } else {
                addBug(randomType())
            }
        }

        running = true
        lastFrameTime = System.nanoTime()
        invalidate()
    }

    fun stopGame() {
        running = false
    }

    private fun randomType(): BugType {
        val number = random.nextInt(100)

        return when {
            number < 60 -> BugType.NORMAL
            number < 90 -> BugType.FAST
            else -> BugType.RARE
        }
    }

    private fun addBug(type: BugType) {
        val speed: Float
        val size: Float
        val points: Int
        val resource: Int

        when (type) {
            BugType.NORMAL -> {
                speed = 0.15f
                size = 0.16f
                points = 1
                resource = R.drawable.bug_normal
            }

            BugType.FAST -> {
                speed = 0.28f
                size = 0.12f
                points = 2
                resource = R.drawable.bug_fast
            }

            BugType.RARE -> {
                speed = 0.20f
                size = 0.10f
                points = 5
                resource = R.drawable.bug_rare
            }
        }

        val angle = random.nextFloat() * (Math.PI * 2.0)

        val bug = Bug(
            id = nextId++,
            x = 0.15f + random.nextFloat() * 0.7f,
            y = 0.15f + random.nextFloat() * 0.7f,
            speed = speed,
            size = size,
            type = type,
            points = points,
            visualResource = resource,
            directionX = cos(angle).toFloat(),
            directionY = sin(angle).toFloat()
        )

        bugs.add(bug)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (width == 0 || height == 0) {
            return
        }

        if (running) {
            updateBugs()
        }

        drawBugs(canvas)

        if (running) {
            postInvalidateOnAnimation()
        }
    }

    private fun updateBugs() {
        val currentTime = System.nanoTime()

        val deltaTime = if (lastFrameTime == 0L) {
            0f
        } else {
            ((currentTime - lastFrameTime) / 1_000_000_000f)
                .coerceAtMost(0.05f)
        }

        lastFrameTime = currentTime

        val speedMultiplier = GameSettings.speed / 5f
        val minDimension = min(width, height).toFloat()

        for (bug in bugs) {
            bug.x += bug.directionX * bug.speed * speedMultiplier * deltaTime
            bug.y += bug.directionY * bug.speed * speedMultiplier * deltaTime

            val halfSizePixels = bug.size * minDimension / 2f
            val halfWidthLogical = halfSizePixels / width
            val halfHeightLogical = halfSizePixels / height

            if (bug.x - halfWidthLogical < 0f) {
                bug.x = halfWidthLogical
                bug.directionX *= -1
            }

            if (bug.x + halfWidthLogical > 1f) {
                bug.x = 1f - halfWidthLogical
                bug.directionX *= -1
            }

            if (bug.y - halfHeightLogical < 0f) {
                bug.y = halfHeightLogical
                bug.directionY *= -1
            }

            if (bug.y + halfHeightLogical > 1f) {
                bug.y = 1f - halfHeightLogical
                bug.directionY *= -1
            }
        }
    }

    private fun drawBugs(canvas: Canvas) {
        val minDimension = min(width, height).toFloat()

        for (bug in bugs) {
            val centerX = bug.x * width
            val centerY = bug.y * height
            val sizePixels = bug.size * minDimension

            val rect = RectF(
                centerX - sizePixels / 2f,
                centerY - sizePixels / 2f,
                centerX + sizePixels / 2f,
                centerY + sizePixels / 2f
            )

            val bitmap = bitmapCache.getOrPut(bug.visualResource) {
                BitmapFactory.decodeResource(
                    resources,
                    bug.visualResource
                )
            }

            canvas.drawBitmap(
                bitmap,
                null,
                rect,
                paint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_DOWN || !running) {
            return true
        }

        val minDimension = min(width, height).toFloat()

        for (index in bugs.indices.reversed()) {
            val bug = bugs[index]

            val centerX = bug.x * width
            val centerY = bug.y * height
            val sizePixels = bug.size * minDimension

            val left = centerX - sizePixels / 2f
            val right = centerX + sizePixels / 2f
            val top = centerY - sizePixels / 2f
            val bottom = centerY + sizePixels / 2f

            if (
                event.x in left..right &&
                event.y in top..bottom
            ) {
                val points = bug.points

                bugs.removeAt(index)
                addBug(randomType())

                onHit?.invoke(points)

                invalidate()
                return true
            }
        }

        onMiss?.invoke(1)

        return true
    }
}