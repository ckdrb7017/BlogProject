package com.jakchang.viewdrawing

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout

class MyLinearLayoutR: LinearLayout {
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet){

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("TAG"," MyLinearLayoutR : onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        Log.d("TAG"," MyLinearLayoutR : onLayout")
        super.onLayout(changed, l, t, r, b)

    }

    override fun onDraw(canvas: Canvas?) {
        Log.d("TAG"," MyLinearLayoutR : onDraw")
        super.onDraw(canvas)

    }
}