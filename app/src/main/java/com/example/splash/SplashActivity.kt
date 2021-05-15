package com.example.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Path
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.window.SplashScreenView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import com.example.splash.databinding.ActivitySplashBinding
import java.lang.Thread.sleep
import java.time.Instant

class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private val viewModel: MyViewModel by viewModels()
    private val jumpRunnable = { goToMainScreen() }
//    {
//        Log.d("Splash", "goToMainScreen delayed")
//        goToMainScreen()
//    }

    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Log.d("Splash", "SplashActivity#onCreate")
        super.onCreate(savedInstanceState)

        // setContentView(R.layout.activity_splash)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyLifecycleObserver(lifecycle, this).also { lifecycle.addObserver(it) }
        // goToMainScreenDelayed()

        // keepSplashScreenLonger()

         customizeSplashScreenExit()
    }

    // Ensure main screen not shown when tap home key during message queueing.
    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    // Ensure main screen jump logic can do again.
    override fun onResume() {
        super.onResume()
        goToMainScreenDelayed()
    }

    fun onSkipTap(view: View) {
        // handler.removeCallbacks(jumpRunnable)
        handler.removeCallbacksAndMessages(null)
        goToMainScreen()
    }

    private fun goToMainScreenDelayed() {
        handler.postDelayed(jumpRunnable, 3000)
    }

    private fun goToMainScreen() {
        Intent(
            this,
            MainActivity::class.java
        ).also { startActivity(it) }
        finish()
    }

    private fun keepSplashScreenLonger() {
        Log.d("Splash", "SplashActivity#keepSplashScreenLonger()")
        // 监听Content View的描画时机
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    Log.d("Splash", "SplashActivity#onPreDraw() currentTime:${SystemClock.uptimeMillis()}")
                    // 准备好了描画放行，反之挂起
                    return if (viewModel.isDataReady()) {
                        Log.d("Splash", "SplashActivity#onPreDraw() proceed")
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        Log.d("Splash", "SplashActivity#onPreDraw() suspend")
                        false
                    }
                }
            }
        )
    }

    @RequiresApi(31)
    private fun customizeSplashScreenExit() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            Log.d(
                "Splash", "SplashScreen#onSplashScreenExit view:$splashScreenView"
                        + " icon:${splashScreenView.iconView}"
                        + " bg:${splashScreenView.background}"
            )

            // Exit immediately
//            Log.d("Splash", "SplashScreen#remove directly")
//            splashScreenView.remove()

            // Standard exit animator
//            sleep(1000)
//            Log.d("Splash", "SplashScreen#remove after sleeping")
//            splashScreenView.remove()

            // Customize exit animator
//            showSplashExitAnimator(splashScreenView)
             showSplashIconExitAnimator(splashScreenView)
        }
    }

    @RequiresApi(31)
    private fun showSplashExitAnimator(splashScreenView: SplashScreenView) {
        // Single slide up animator.
//        val slideUp = ObjectAnimator.ofFloat(
//            splashScreenView,
//            View.TRANSLATION_Y,
//            0f,
//            -splashScreenView.height.toFloat()
//        )
//            slideUp.interpolator = AnticipateInterpolator()
//            slideUp.duration = 1000L
//            // Call SplashScreenView.remove at the end of your custom animation.
//            slideUp.doOnEnd {
//                Log.d("Splash", "SplashScreen#onSplashScreenExit onEnd remove")
//                splashScreenView.remove()
//            }
//            // Run your animation.
//            slideUp.start()

        // Create your custom animation set.
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

        val scaleXOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.SCALE_X,
            1.0f,
            0f
        )

        val path = Path()
        path.moveTo(1.0f, 1.0f)
        path.lineTo(0f, 0f)
        val scaleOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.SCALE_X,
            View.SCALE_Y,
            path
        )

        val alphaOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.ALPHA,
            1f,
            0f
        )

        val animatorSet = AnimatorSet()
        animatorSet.duration = resources.getInteger(R.integer.splash_exit_total_duration).toLong()
        animatorSet.interpolator = AnticipateInterpolator()

        animatorSet.playTogether(scaleOut)
//        animatorSet.playTogether(slideUp)
//        animatorSet.playTogether(slideUp, scaleXOut)
//        animatorSet.playTogether(slideUp, scaleOut)
//        animatorSet.playTogether(slideUp, slideLeft)
//        animatorSet.playTogether(slideUp, slideLeft, scaleOut)
//        animatorSet.playTogether(slideUp, slideLeft, scaleOut, alphaOut)

        animatorSet.doOnEnd {
            Log.d("Splash", "SplashScreen#remove when animator done")
            // splashScreenView.setBackgroundColor(android.graphics.Color.BLUE)
            splashScreenView.remove()
        }
        animatorSet.start()
    }

    @RequiresApi(31)
    private fun showSplashIconExitAnimator(splashScreenView: SplashScreenView) {
        val iconView = splashScreenView.iconView ?: return

        val slideUp = ObjectAnimator.ofFloat(
            splashScreenView.iconView,
            View.TRANSLATION_Y,
            0f,
            -iconView.height * 2.toFloat()
//            -iconView.height.toFloat()
        )
        slideUp.interpolator = AnticipateInterpolator()
        slideUp.duration = resources.getInteger(R.integer.splash_exit_icon_duration).toLong()
        // slideUp.duration = getRemainingDuration(splashScreenView)
//        Log.d("Splash", "SplashScreen#showSplashIconExitAnimator() duration:${slideUp.duration}")

        slideUp.doOnEnd {
            Log.d("Splash", "SplashScreen#showSplashIconExitAnimator() onEnd remove")
            splashScreenView.remove()
        }
        slideUp.start()
    }

    @RequiresApi(31)
    private fun getRemainingDuration(splashScreenView: SplashScreenView): Long {
        // Get the duration of the animated vector drawable.
        val animationDuration = splashScreenView.iconAnimationDurationMillis

        // Get the start time of the animation.
        val animationStart = splashScreenView.iconAnimationStartMillis

        Log.d(
            "Splash", "SplashScreen#getRemainingDuration() animationDuration:$animationDuration" +
                    " animationStart:$animationStart" +
                    " animationStart:${Instant.ofEpochMilli(animationStart).toEpochMilli()}" +
                    " current:${Instant.ofEpochMilli(System.currentTimeMillis()).toEpochMilli()}" +
                    " current:${Instant.now().toEpochMilli()}" +
                    " current:${SystemClock.uptimeMillis()}"
        )

        // Calculate the remaining duration of the animation.
        return if (animationDuration != null && animationStart != null) {
            (animationDuration - SystemClock.uptimeMillis() + animationStart)
                .coerceAtLeast(0L)
        } else {
            0L
        }

//        // Calculate the remaining duration of the animation.
//        var remainingDuration = if (animationDuration != null && animationStart != null) {
//            // (animationDuration - Duration.between(animationStart, Instant.now()))
//            // (animationDuration - ChronoUnit.MICROS.between(Instant.ofEpochMilli(animationStart * 1000), Instant.now()))
//            (animationDuration - SystemClock.uptimeMillis() + animationStart)
//            // .coerceAtLeast(0L)
//        } else {
//            0L
//        }
//
//        Log.d("Splash", "SplashScreen#getRemainingDuration() remainingDuration:$remainingDuration")
//        remainingDuration = remainingDuration.coerceAtLeast(0L)
//
//        Log.d("Splash", "SplashScreen#getRemainingDuration() 2 remainingDuration:$remainingDuration")
//        return remainingDuration
    }
}