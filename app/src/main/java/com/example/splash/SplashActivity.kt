package com.example.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Path
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd

class SplashActivity: AppCompatActivity() {
    private lateinit var handler: Handler
    private val jumpRunnable = { Log.d("Splash", "goToMainScreen delayed")
        goToMainScreen() }

    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Splash", "SplashActivity#onCreate")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        MyLifecycleObserver(lifecycle, this).also { lifecycle.addObserver(it) }

        goToMainScreenDelayed()

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            Log.d("Splash", "SplashActivity#onSplashScreenExit() view:$splashScreenView")

            // Create your custom animation.
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            )
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.width.toFloat()
            )

//            slideUp.interpolator = AnticipateInterpolator()
//            slideUp.duration = 1000L
//            // Call SplashScreenView.remove at the end of your custom animation.
//            slideUp.doOnEnd {
//                Log.d("Splash", "SplashActivity#onSplashScreenExit() onEnd remove")
//                splashScreenView.remove()
//            }
//            // Run your animation.
//            slideUp.start()

//            val scaleXOut = ObjectAnimator.ofFloat(
//                splashScreenView,
//                View.SCALE_X,
//                1.0f,
//                0f
//            )

            val path = Path()
            path.moveTo(1.0f, 1.0f)
            path.lineTo(0f, 0f)
            val scaleOut = ObjectAnimator.ofFloat(
                splashScreenView,
                View.SCALE_X,
                View.SCALE_Y,
                path
            )

            val animatorSet = AnimatorSet()
            animatorSet.duration = 400L
            animatorSet.interpolator = AnticipateInterpolator()
//            animatorSet.playTogether(slideUp, scaleXOut)
//            animatorSet.playTogether(slideUp, scaleOut)
//            animatorSet.playTogether(slideUp)
            animatorSet.playTogether(slideUp, slideLeft)
            animatorSet.doOnEnd {
                Log.d("Splash", "SplashActivity#onSplashScreenExit() onEnd remove")
                splashScreenView.remove()
            }
            animatorSet.start()
        }
    }

    fun onSkipTap(view: View) {
        // handler.removeCallbacks(jumpRunnable)
        handler.removeCallbacksAndMessages(null)
        goToMainScreen()
    }

    private fun goToMainScreenDelayed() {
        handler = Handler(mainLooper)
        handler.postDelayed(jumpRunnable, 3000)
    }

    private fun goToMainScreen() {
        Intent(
            this,
            MainActivity::class.java
        ).also { startActivity(it) }
        finish()
    }
}