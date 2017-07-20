package com.samuelepontremoli.ktry.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import com.eftimoff.androipathview.PathView
import com.samuelepontremoli.ktry.R
import com.samuelepontremoli.ktry.commons.BaseActivity

/**
 * Created by s.pontremoli on 20/07/2017.
 * Splash Activity
 */
class SplashActivity : BaseActivity() {

    private val animDelay = 100
    private val animDuration = 1000
    private val animTotal = 1400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val pathView = findViewById(R.id.pathView) as PathView

        pathView.pathAnimator
                .delay(animDelay)
                .duration(animDuration)
                .interpolator(AccelerateDecelerateInterpolator())
                .start()

        pathView.setFillAfter(true)

        Handler().postDelayed({ startGifsActivity() }, animTotal.toLong())
    }

    fun startGifsActivity() {
        val intent = Intent(applicationContext, GifsActivity::class.java)
        if(!this.isFinishing) {
            startActivity(intent)
        }
    }

}