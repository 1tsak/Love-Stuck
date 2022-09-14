package com.oa.thedatingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oa.thedatingapp.R
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Timer().schedule(1500) {
            startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
            finish()
        }
    }
}