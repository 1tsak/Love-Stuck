package com.oa.thedatingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import com.oa.thedatingapp.R
import com.oa.thedatingapp.models.WelcomeModel
import com.oa.thedatingapp.adapters.ViewPagerAdapter

class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var models: ArrayList<WelcomeModel>
    var sliderDotspanel: LinearLayout? = null
    private var dotscount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        findViewById<CardView>(R.id.signUp).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        })
        viewPager = findViewById(R.id.viewPager)
         adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

    }
}