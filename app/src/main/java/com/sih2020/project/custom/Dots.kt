package com.sih2020.project.custom

import android.widget.ImageView
import android.widget.LinearLayout
import com.sih2020.project.R

class Dots(dotView:LinearLayout) {

    /**
     * @author Lakshay Dutta
     * @since 10-3-20
     *
     * Dots are used as an indicator to which screen/fragment the user is on currently
     * @see R.layout.bottom_dots
     */

    private var dot1:ImageView = dotView.findViewById(R.id.dot1)
    private var dot2:ImageView = dotView.findViewById(R.id.dot2)

    private fun clear(){
        dot1.setBackgroundResource(R.drawable.dot_black)
        dot2.setBackgroundResource(R.drawable.dot_black)
    }

    fun setActiveDot(position:Int){
        clear()
        when(position){
            1 -> dot1.setBackgroundResource(R.drawable.dot_white)
            2 -> dot2.setBackgroundResource(R.drawable.dot_white)
        }
    }

}