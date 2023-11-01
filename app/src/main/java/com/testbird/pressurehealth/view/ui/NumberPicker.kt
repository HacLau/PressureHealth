package com.testbird.pressurehealth.view.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import com.testbird.pressurehealth.R
import kotlin.math.abs
import kotlin.properties.Delegates

class NumberPicker :View{
    private var strings: List<String> = mutableListOf()
    private var anInt:Int = 0
    private var width:Int = 0
    private var height:Int = 0
    private var index:Int = 0
    private var downX:Float = 0f
    private var anOffset:Float = 0f
    private var selectedTextSize = 0f
    private var selectedColor:Int = 0
    private var textSize:Float = 0f
    private var textColor:Int = 0
    private var seeSize by Delegates.notNull<Int>()
    private var firstVisible by Delegates.notNull<Boolean>()
    private var textWidth by Delegates.notNull<Int>()
    private var textHeight by Delegates.notNull<Int>()
    private var centerTextHeight by Delegates.notNull<Int>()
    private lateinit var rect: Rect
    private var textPaint: TextPaint? = null
    private var selectedPaint: TextPaint? = null
    private var onSelect: (String, Int) -> Unit = { _, _->}
    private var onMove:(String, Int)-> Unit = { _, _->}

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
        initData()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
        initData()
    }

    private fun initData() {
        strings = arrayListOf()
        seeSize = 5
        firstVisible = true
        rect = Rect()
        textWidth = 0
        textHeight = 0
        centerTextHeight = 0
        setWillNotDraw(false)
        this.isClickable = true
        initPaint()
    }


    private fun initPaint() {
        textPaint = TextPaint(1)
        textPaint!!.textSize = textSize
        textPaint!!.color = textColor
        selectedPaint = TextPaint(1)
        selectedPaint!!.color = selectedColor
        selectedPaint!!.textSize = selectedTextSize
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val tta = context!!.obtainStyledAttributes(attrs, R.styleable.NumberPicker)
        seeSize = tta.getInteger(R.styleable.NumberPicker_seesize, 5)
        selectedTextSize = tta.getFloat(R.styleable.NumberPicker_selectedTextSize, 60.0f)
        selectedColor = tta.getColor(R.styleable.NumberPicker_selectedTextColor, context!!.resources.getColor(R.color.result_0))
        textSize = tta.getFloat(R.styleable.NumberPicker_normalTextSize, 50.0f)
        textColor = tta.getColor(R.styleable.NumberPicker_normalTextColor, context!!.resources.getColor(R.color.result_1))
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
            }
            MotionEvent.ACTION_UP -> {
                anOffset = 0.0f
                this.invalidate()
                onSelect.invoke(getSelectedString(), index)
            }

            MotionEvent.ACTION_MOVE -> {
                val scrollX = event.x

                if (index != 0 && index != strings.size - 1) {
                    anOffset = scrollX - downX
                }
                if (index == 0 || index == strings.size - 1 || abs(anOffset) > abs(50 - strings.size.div(6.0f))) {
                    if (scrollX > downX) {
                        if (index > 0) {
                            anOffset = 0.0f
                            --index
                            downX = scrollX
                        }
                    } else if (index < strings.size - 1) {
                        anOffset = 0.0f
                        ++index
                        downX = scrollX
                    }
                }
                onMove.invoke(getSelectedString(), index)
                this.invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (firstVisible) {
            width = getWidth()
            height = getHeight()
            anInt = width / seeSize
            firstVisible = false
        }
        if (index >= 0 && index <= strings.size - 1) {
            selectedPaint!!.getTextBounds(strings[index], 0, strings[index].length, rect)
            val centerTextWidth = rect.width()
            centerTextHeight = rect.height()
            canvas.drawText(
                strings[index], (getWidth() / 2 - centerTextWidth / 2).toFloat() + anOffset, (getHeight() / 2 + centerTextHeight / 2).toFloat(),
                selectedPaint!!
            )
            for (i in strings.indices) {
                if (index > 0 && index < strings.size - 1) {
                    textPaint!!.getTextBounds(strings[index - 1], 0, strings[index - 1].length, rect)
                    val width1 = rect.width()
                    textPaint!!.getTextBounds(strings[index + 1], 0, strings[index + 1].length, rect)
                    val width2 = rect.width()
                    textWidth = (width1 + width2) / 2
                }
                if (i == 0) {
                    textPaint!!.getTextBounds(strings[0], 0, strings[0].length, rect)
                    textHeight = rect.height()
                }
                if (i != index) {
                    canvas.drawText(
                        strings[i],
                        ((i - index) * anInt + getWidth() / 2 - textWidth / 2).toFloat() + anOffset,
                        (getHeight() / 2 + textHeight / 2).toFloat(),
                        textPaint!!
                    )
                }
            }
        }
    }

    fun setData(strings: List<String>, index: Int = strings.size / 2) {
        this.strings = strings
        this.index = index
        this.invalidate()
    }

    fun setSelectedColor(@ColorInt color:Int){
        selectedColor = color
        selectedPaint!!.color
    }

    private fun getSelectedString(): String {
        return if (strings.isNotEmpty()) strings[index] else ""
    }

}