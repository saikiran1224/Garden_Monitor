package com.kirandroid.gardenmonitor.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Toast
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.utils.AppPreferences
import kotlin.math.abs
import kotlin.math.roundToInt


class RectangleOverlay : LinearLayout {
    private var bitmap: Bitmap? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (bitmap == null) {
            createWindowFrame()
        }
        canvas.drawBitmap(bitmap!!, 0F, 0F, null)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    protected fun createWindowFrame() {
        setWillNotDraw(false)
        setLayerType(LAYER_TYPE_HARDWARE, null)

        // initialising App Preferences
        AppPreferences.init(context)

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val osCanvas = Canvas()


        osCanvas.setBitmap(bitmap)

        val outerRectangle = RectF(0F, 0F, width.toFloat(), height.toFloat())
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setColor(resources.getColor(com.kirandroid.gardenmonitor.R.color.greenAlpha))
        osCanvas.drawRect(outerRectangle, paint)

        paint.setColor(Color.TRANSPARENT)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OUT))

        val x1 = (width / 14).toFloat()
        val x2 = width - x1
        val heightDiff = x2 - x1

        val y1 = height / 2 - heightDiff / 2
        val y2 = height / 2 + heightDiff / 2
        val rect = RectF(x1, y1, x2, y2)

       // Toast.makeText(context,"x1: " + x1.toString() + " y1: "+y1.toString() + " Width: "+ (x2-x1) + " Height: " + (y2-y1),Toast.LENGTH_LONG).show()

        osCanvas.drawRoundRect(rect, 5F, 5F, paint)

        val drawable: Drawable = resources.getDrawable(R.drawable.qr_code_square, null)
        drawable.setBounds(x1.roundToInt() - 8 , y1.roundToInt() - 8, x2.roundToInt() + 8, y2.roundToInt() + 8)
        drawable.draw(osCanvas)


        /* val rectBorder = RectF(x1 + 1, y1 + 1, x2 - 1, y2 - 1)
         paint.setStyle(Paint.Style.STROKE)
         paint.setStrokeWidth(1F)
         paint.setColor(Color.WHITE)

         osCanvas.drawRoundRect(rectBorder, 4F, 4F, paint)*/
    }

    fun getDp(float: Float): Int {
        return (float * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }

    override fun isInEditMode(): Boolean {
        return true
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        bitmap = null
    }
}