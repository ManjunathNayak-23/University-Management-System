package com.developer.aurora.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.developer.aurora.R

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor=ContextCompat.getColor(this,R.color.authBack)
        setContentView(R.layout.activity_authentication)
    }
}