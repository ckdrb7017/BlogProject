package com.example.dynamicconstraintset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.dynamicconstraintset.databinding.ActivityMainBinding
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var dataList :ArrayList<String> = arrayListOf("aaaaa","bbbbbb","cccccccccccccccccccccccccccc","dddddddddddddddddddddddddddd","eeeee","ffffffffff","ggggggg","hhh","iii","jjjjj","kkkkk")
    private var buttonList :ArrayList<Button> =arrayListOf()
    private lateinit var selectedButtonList : LinkedBlockingQueue<Button>
    private var accumulatedMargin :Float?=0f
    private var constraintLayout :ConstraintLayout?=null
    private var constraintSet = ConstraintSet()
    private var heightRatio = 2
    private var constraintWidth=0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedButtonList = LinkedBlockingQueue()
        constraintLayout = binding.constraintlayout
        constraintSet.clone(constraintLayout)


        binding.refresh.setOnClickListener {
            binding.constraintlayout.removeAllViews()
            refresh()
        }

        refresh()
    }

    private fun refresh(){
        accumulatedMargin =0f
        heightRatio = 2
        dataList.shuffle()
        buttonList.clear()
        for(i :Int in 0 until dataList.size){
            var button = Button(this)
            button.background = getDrawable(R.drawable.toggle_button_selector)

            button.id = View.generateViewId()
            button.text = dataList[i]
            button.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            constraintLayout!!.addView(button)
            constraintSet.constrainHeight(button.id, ViewGroup.LayoutParams.WRAP_CONTENT)
            constraintSet.constrainWidth(button.id,ViewGroup.LayoutParams.WRAP_CONTENT)
            setButtonConstraint(constraintSet, button, i)
            constraintSet.applyTo(constraintLayout)
            buttonList.add(button)
            setButtonListener(button,i)

        }
    }

    private fun setButtonListener(button : Button, currentIndex :Int){
        button.post(Runnable {
            if(button.x != 0f && currentIndex !=0){
                setConstraintMargin(constraintSet,buttonList,button,currentIndex)
            }
            button.setOnClickListener {
                selectedButtonList.run {
                    val iter = selectedButtonList.iterator()
                    var index = -1
                    while (iter.hasNext()) {
                        val iterButton = iter.next()
                        if (iterButton.id == button.id) {
                            selectedButtonList.remove(iterButton)
                            index++
                            break;
                        }
                    }

                    if(index==-1){
                        selectedButtonList.add(button)
                    }
                }

                if(selectedButtonList.size==6){
                    val deletedButton : Button = selectedButtonList.first()
                    deletedButton.isSelected = false
                    selectedButtonList.remove(deletedButton)
                }

                it.isSelected = !it.isSelected
            }
        })
    }

    private fun setConstraintMargin(constraintSet: ConstraintSet, buttonList : ArrayList<Button>,button : Button, i : Int) {
        val prevItemX = buttonList[i - 1].x + buttonList[i - 1].measuredWidth
        val currentItemX = buttonList[i].x + buttonList[i].measuredWidth
        var marginSet =0f
        constraintWidth = binding.constraintlayout.x+binding.constraintlayout.measuredWidth
        if(i%3==1) {
            val randomNum = (Math.random()*3+2)
            var transX = -button.measuredWidth/randomNum.toFloat()

            constraintSet.setTranslationX(buttonList[i].id, transX)
            if(buttonList[i].x+transX < 0){
                val measuredMargin = buttonList[i].x+transX
                transX -=measuredMargin
                constraintSet.setTranslationX(buttonList[i].id, transX)

            }
            marginSet = transX

        }
        else if(i%3==2) {
            val randomNum = (Math.random()*3+2).toInt()
            var transX = button.measuredWidth/randomNum.toFloat()
            if(currentItemX+transX>=constraintWidth){
                transX -= ((currentItemX+transX)-constraintWidth)
            }
            constraintSet.setTranslationX(buttonList[i].id, transX)
            marginSet = transX
        }

        if (prevItemX < buttonList[i].x+marginSet ) {
            accumulatedMargin =
                accumulatedMargin!! + (buttonList[i].measuredHeight / heightRatio).toFloat()
            constraintSet.setTranslationY(buttonList[i].id, accumulatedMargin!!)
            constraintSet.applyTo(constraintLayout)

        }
        else if(currentItemX+marginSet < buttonList[i-1].x){
            accumulatedMargin =
                accumulatedMargin!! + (buttonList[i].measuredHeight / heightRatio).toFloat()
            constraintSet.setTranslationY(buttonList[i].id, accumulatedMargin!!)
            constraintSet.applyTo(constraintLayout)
        }
        else if(button.x  < buttonList[i-1].x && currentItemX <prevItemX){
            constraintSet.setTranslationY(buttonList[i].id, accumulatedMargin!!.toFloat())
            constraintSet.applyTo(constraintLayout)
        }
        else {
            constraintSet.setTranslationY(buttonList[i].id, accumulatedMargin!!.toFloat())
            constraintSet.applyTo(constraintLayout)
        }
    }

    private fun setButtonConstraint(constraintSet: ConstraintSet, button: Button, type:Int){
        if(type==0) {
            constraintSet.connect(button.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
            constraintSet.connect(button.id,ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0)
            constraintSet.connect(button.id,ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0)

        }
        else{
            when(type%3) {
                0-> {
                    setConstraintConnect(constraintSet, button.id, ConstraintSet.BOTTOM, buttonList[buttonList.size - 1].id, ConstraintSet.TOP,0)
                    setConstraintConnect(constraintSet, button.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,0)
                    setConstraintConnect(constraintSet, button.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT,0)
                }
                1->{
                    setConstraintConnect(constraintSet,button.id, ConstraintSet.BOTTOM, buttonList[buttonList.size - 1].id, ConstraintSet.TOP,0)
                    setConstraintConnect(constraintSet,button.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT,0)
                    setConstraintConnect(constraintSet,button.id, ConstraintSet.LEFT, buttonList[buttonList.size - 1].id, ConstraintSet.RIGHT,0)
                }
                2->{
                    setConstraintConnect(constraintSet, button.id, ConstraintSet.BOTTOM, buttonList[buttonList.size - 1].id, ConstraintSet.TOP,0)
                    setConstraintConnect(constraintSet, button.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,0)
                    setConstraintConnect(constraintSet, button.id, ConstraintSet.LEFT, buttonList[buttonList.size - 1].id, ConstraintSet.RIGHT,0)

                }
            }
        }
    }

    private fun setConstraintConnect(constraintSet: ConstraintSet, startID :Int, startSide :Int, endID : Int, endSide : Int,margin:Int){
        constraintSet.connect(startID, startSide,endID, endSide, margin)
    }
}