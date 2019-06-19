package zs.qimai.com.viewtest

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kotlin.math.sin

class FlowLayout : ViewGroup {
    var totalHeight = 0
    var singleLineMaxLine = 0
    var mVerticalSpacing = 20
    var mHorizontalSpacing = 20

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val count = childCount
        val singleWidth = paddingLeft
        //测量子view
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        //遍历获取每行最大的高度

        var singleMxHeight = 0
        var left = paddingLeft

        for (i in 0 until count) {
            val childView = getChildAt(i)
            totalHeight = Math.max(childView.measuredHeight, totalHeight)
            singleMxHeight = Math.max(childView.measuredHeight, singleMxHeight)
            //换行
            if (left + childView.measuredWidth + paddingRight > widthSize) {
                left = paddingLeft
                totalHeight += singleMxHeight + mVerticalSpacing
                singleMxHeight = childView.measuredHeight
            } else {
                left += childView.measuredWidth + mHorizontalSpacing

            }
        }
        //totalHeight += singleMxHeight

        /*if (getChildViewAvalibleHeight(childView) > singleLineMaxLine) {
            singleLineMaxLine = getChildViewAvalibleHeight(childView)
            left += singleLineMaxLine
            if (left > widthSize - paddingLeft - paddingRight) {
                left = paddingLeft
                totalHeight += singleLineMaxLine
                singleLineMaxLine = 0
            }
//最后没换行
        }
        totalHeight += singleLineMaxLine
    }*/
        setMeasuredDimension(widthSize, totalHeight + paddingBottom + paddingTop)
    }

    /**
     * 获取view的可用最大空间
     */
    private fun getChildViewAvalibleHeight(childView: View) =
            childView.measuredHeight + childView.paddingTop + childView.paddingBottom

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = paddingLeft
        var top = paddingTop
        var lineHeight = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            lineHeight = Math.max(lineHeight, childView.measuredHeight)
            if (left + childView.measuredWidth + paddingRight > measuredWidth) {
                left = paddingLeft
                top += lineHeight + mVerticalSpacing
                lineHeight = childView.measuredHeight
                //childView.layout(left, top, left + childView.measuredWidth, +top + childView.measuredHeight)
                //left += childView.measuredWidth
            }
            childView.layout(left, top, left + childView.measuredWidth, top + childView.measuredHeight)
            left += childView.measuredWidth + mHorizontalSpacing
        }
    }
}
