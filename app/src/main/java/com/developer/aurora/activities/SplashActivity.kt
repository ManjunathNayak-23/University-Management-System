package com.developer.aurora.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.developer.aurora.R
import com.developer.aurora.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        if (isDarkThemeOn()) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        } else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }

        setContentView(binding.root)
        auth = Firebase.auth
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val float = AnimationUtils.loadAnimation(this, R.anim.floatanim)

        binding.container.startAnimation(fadeIn)
        binding.circle1.startAnimation(float)
        binding.circle2.startAnimation(float)
        binding.circle3.startAnimation(float)


    }

    fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if (currentUser != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 1200)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, AuthenticationActivity::class.java))
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 1200)
        }
    }
}