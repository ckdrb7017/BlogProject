package com.jakchang.viewdrawing

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout

class MyLinearLayout2: LinearLayout {
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet){

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("TAG"," MyLinearLayout2 : onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        Log.d("TAG"," MyLinearLayout2 : onLayout")
        super.onLayout(changed, l, t, r, b)

    }

    override fun onDraw(canvas: Canvas?) {
        Log.d("TAG"," MyLinearLayout2 : onDraw")
        super.onDraw(canvas)

    }
}