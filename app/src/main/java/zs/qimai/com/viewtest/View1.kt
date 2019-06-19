package zs.qimai.com.viewtest

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class View1 @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val lineaPaint: Paint = Paint()
    lateinit var paint: TextPaint

    init {
        //attrs.getAttributeName()
        paint = TextPaint()
        paint.textSize = 42f
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var resultWidth: Int = widthSize
        var resultHeight: Int = heightSize

        if (widthMode == MeasureSpec.AT_MOST) {
            val contentWidth: Int = paint.measureText("VIEW").toInt()
            resultWidth = Math.min(resultWidth, contentWidth)

        }
        if (heightMode == MeasureSpec.AT_MOST) {

            var rectF = Rect()
            paint.getTextBounds("VIEW"!!, 0, "VIEW".length, rectF)
            val contentHeight = rectF.height()
            resultHeight = Math.min(resultHeight, contentHeight)
        }

        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawColor(Color.RED)
        canvas.drawText("VIEW", (width / 2).toFloat(), (height / 2).toFloat(), paint)
        lineaPaint.color = Color.BLACK
        canvas.drawLine(0f, height.toFloat() / 2, width.toFloat(), height.toFloat() / 2, lineaPaint)
        canvas.drawLine(width.toFloat() / 2, 0f, width.toFloat() / 2, height.toFloat(), lineaPaint)
    }
}