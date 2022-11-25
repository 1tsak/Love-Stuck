package com.oa.thedatingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.oa.thedatingapp.R
import com.oa.thedatingapp.models.WelcomeModel
import com.oa.thedatingapp.adapters.ViewPagerAdapter

class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var welcomeList: ArrayList<Int>
    var sliderDotspanel: LinearLayout? = null
    private var dotscount = 3
    private lateinit var dots: ArrayList<ImageView?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        intit()


        findViewById<CardView>(R.id.signUp).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        })

    }


    private fun intit() {
        welcomeList = ArrayList()
        dots = ArrayList()
        dots.add(findViewById<ImageView>(R.id.dot1))
        dots.add(findViewById<ImageView>(R.id.dot2))
        dots.add(findViewById<ImageView>(R.id.dot3))
        viewPager = findViewById(R.id.viewPager)
        welcomeList.add(R.drawable.welcome1)
        welcomeList.add(R.drawable.welcome2)
        welcomeList.add(R.drawable.welcome3)
        adapter = ViewPagerAdapter(this,welcomeList,viewPager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                dots[position]?.setImageResource(R.color.item_active)
                if (position != 0){

                dots[position-1]?.setImageResource(R.color.item_unactive)
                }
            }
        })
    }
}