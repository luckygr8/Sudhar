package com.sih2020.project.intro

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sih2020.project.BaseActivity
import com.sih2020.project.R
import com.sih2020.project.viewReports.ViewReportsFragment

class IntroActivity : BaseActivity() {

    //
    private lateinit var introViewpager: ViewPager
    private lateinit var dots: LinearLayout

    private lateinit var dot1:ImageView
    private lateinit var dot2:ImageView
    //

    override fun bindViews() {
        introViewpager = findViewById(R.id.introViewpager)
        val adapter = ScreenSlidePagerAdapter(supportFragmentManager)
        introViewpager.adapter = adapter

        dots = findViewById(R.id.dots)

        dot1 = dots.findViewById(R.id.dot1)
        dot2 = dots.findViewById(R.id.dot2)

        introViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        dot2.setBackgroundResource(R.drawable.dot_black)
                        dot1.setBackgroundResource(R.drawable.dot_white)
                    }

                    1 -> {
                        dot1.setBackgroundResource(R.drawable.dot_black)
                        dot2.setBackgroundResource(R.drawable.dot_white)
                    }
                }
            }
        })


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        bindViews()

        //Functions.firstBootDone()
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 2

        override fun getItem(position: Int): Fragment = when (position) {
            0 ->
               IntroRules()


            1 ->
                IntroRules()


            else -> ViewReportsFragment()
        }
    }

}
