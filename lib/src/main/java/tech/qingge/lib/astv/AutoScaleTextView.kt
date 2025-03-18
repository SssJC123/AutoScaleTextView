package tech.qingge.lib.astv

import android.annotation.SuppressLint
import android.content.Context
import android.icu.number.IntegerWidth
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import kotlin.math.max

class AutoScaleTextView : TextView {

    private var maxTextSize = 0f
    private var minTextSize = 0f


    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(attrs)
    }

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(attrs)
    }

    @SuppressLint("UseKtx")
    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoScaleTextView)
        maxTextSize = typedArray.getDimension(R.styleable.AutoScaleTextView_maxTextSize, 0f)
        minTextSize = typedArray.getDimension(R.styleable.AutoScaleTextView_minTextSize, 0f)
        typedArray.recycle()

        maxLines = Int.MAX_VALUE
        ellipsize = null
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        adjustTextSize(widthMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun adjustTextSize(widthMeasureSpec: Int) {
        if (minTextSize == 0f || maxTextSize == 0f) {
            throw RuntimeException("minTextSize and maxTextSize must be set")
        }

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val mode = MeasureSpec.getMode(widthMeasureSpec)

        val adjustedMeasuredWidth: Int = when (mode) {
            MeasureSpec.EXACTLY -> {
                width.toInt()
            }

            MeasureSpec.AT_MOST -> {
                paint.measureText(text.toString()).toInt()
            }

            MeasureSpec.UNSPECIFIED -> {
                paint.measureText(text.toString()).toInt()
            }

            else -> 0
        }

        val availableWidth = adjustedMeasuredWidth - paddingLeft - paddingRight

        paint.textSize = maxTextSize
        while (paint.measureText(text.toString()) > availableWidth) {
            paint.textSize -= 1
        }


        setTextSize(TypedValue.COMPLEX_UNIT_PX, paint.textSize)

    }

}