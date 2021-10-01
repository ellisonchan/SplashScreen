package com.example.splash.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Path
import android.os.*
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.window.SplashScreenView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import com.example.splash.R
import com.example.splash.databinding.ActivityMainSimpleBinding
import com.example.splash.databinding.ActivitySplashBinding
import com.example.splash.observer.MyLifecycleObserver
import com.example.splash.viewmodel.MyViewModel
import java.time.Instant

class JetpackSplashActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    private lateinit var splashScreen: SplashScreen
    private val defaultExitDuration: Long by lazy {
        viewModel.getApplication<Application>()
            .resources.getInteger(R.integer.splash_exit_total_duration).toLong()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Need to be called before setContentView or other view operation on the root view.
        splashScreen = installSplashScreen()

        val binding = ActivityMainSimpleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customizeSplashScreen(splashScreen)
    }

    private fun customizeSplashScreen(splashScreen: SplashScreen) {
        // keepSplashScreenLonger(splashScreen)
        customizeSplashScreenExit(splashScreen)
    }

    // Keep splash screen showing till data initialized.
    private fun keepSplashScreenLonger(splashScreen: SplashScreen) {
        Log.d("Splash", "SplashActivity#keepSplashScreenLonger()")
        splashScreen.setKeepVisibleCondition { !viewModel.isDataReady() }
    }

    // Customize splash screen exit animator.
    private fun customizeSplashScreenExit(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            Log.d(
                "Splash", "SplashScreen#onSplashScreenExit view:$splashScreenViewProvider"
                        + " view:${splashScreenViewProvider.view}"
                        + " icon:${splashScreenViewProvider.iconView}"
            )

            // val onExit = {
            //     splashScreenViewProvider.remove()
            // }

            // defaultExitDuration = getRemainingDuration(splashScreenViewProvider)

            // hookViewLayout(splashScreenViewProvider)

             showSplashExitAnimator(splashScreenViewProvider.view) {
                 splashScreenViewProvider.remove()
             }

            showSplashIconExitAnimator(splashScreenViewProvider.iconView) {
                splashScreenViewProvider.remove()
            }
        }
    }

    // Show exit animator for splash screen view.
    private fun showSplashExitAnimator(splashScreenView: View, onExit: () -> Unit = {}) {
        Log.d("Splash", "showSplashExitAnimator() splashScreenView:$splashScreenView" +
                " context:${splashScreenView.context}" +
                " parent:${splashScreenView.parent}")

        // Create your custom animation set.
        val alphaOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.ALPHA,
            1f,
            0f
        )

        // Slide up to center.
        val slideUp = ObjectAnimator.ofFloat(
            splashScreenView,
            View.TRANSLATION_Y,
            0f,
            // iconView.translationY,
            -(splashScreenView.height).toFloat()
        ).apply {
            addUpdateListener {
                Log.d("Splash", "showSplashIconExitAnimator() translationY:${splashScreenView.translationY}")
            }
        }

        // Slide down to center.
        val slideDown = ObjectAnimator.ofFloat(
            splashScreenView,
            View.TRANSLATION_Y,
            0f,
            // iconView.translationY,
            (splashScreenView.height).toFloat()
        ).apply {
            addUpdateListener {
                Log.d("Splash", "showSplashIconExitAnimator() translationY:${splashScreenView.translationY}")
            }
        }

        val scaleOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0f, 0f)
            }
        )

        AnimatorSet().run {
            duration = defaultExitDuration
            interpolator = AnticipateInterpolator()
            Log.d("Splash", "showSplashExitAnimator() duration:$duration")

            // playTogether(alphaOut, slideUp)
            // playTogether(scaleOut)
            // playTogether(alphaOut)
            // playTogether(scaleOut, slideUp, alphaOut)
            // playTogether(slideUp, alphaOut)
            playTogether(slideDown, alphaOut)

            doOnEnd {
                Log.d("Splash", "showSplashExitAnimator() onEnd")
                Log.d("Splash", "showSplashExitAnimator() onEnd remove")
                onExit()
            }

            start()
        }
    }

    // Show exit animator for splash icon.
    private fun showSplashIconExitAnimator(iconView: View, onExit: () -> Unit = {}) {
        Log.d("Splash", "showSplashIconExitAnimator()" +
                " iconView[:${iconView.width}, ${iconView.height}]" +
                " translation[:${iconView.translationX}, ${iconView.translationY}]")

        val alphaOut = ObjectAnimator.ofFloat(
            iconView,
            View.ALPHA,
            1f,
            0f
        )

        // Bird scale out animator.
        val scaleOut = ObjectAnimator.ofFloat(
            iconView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0.3f, 0.3f)
            }
        )

        // Bird slide up to center.
        val slideUp = ObjectAnimator.ofFloat(
            iconView,
            View.TRANSLATION_Y,
            0f,
            // iconView.translationY,
            -(iconView.height).toFloat() * 2.25f
        ).apply {
            addUpdateListener {
                Log.d("Splash", "showSplashIconExitAnimator() translationY:${iconView.translationY}")
            }
        }

        AnimatorSet().run {
            interpolator = AnticipateInterpolator()
            duration = defaultExitDuration
            Log.d("Splash", "showSplashIconExitAnimator() duration:$duration")

            // playTogether(alphaOut, slideUp)
            playTogether(alphaOut, scaleOut, slideUp)
            // playTogether(scaleOut, slideUp)
            // playTogether(slideUp)

            doOnEnd {
                Log.d("Splash", "showSplashIconExitAnimator() onEnd remove")
                onExit()
            }

            start()
        }
    }

    private fun hookViewLayout(splashScreenViewProvider: SplashScreenViewProvider) {
        Log.d("Splash", "hookViewLayout()")
        val rootWindowInsets = splashScreenViewProvider.view.rootWindowInsets

        Log.d("Splash", "hookViewLayout() rootWindowInsets:$rootWindowInsets" +
                // "\n systemWindowInsets:${rootWindowInsets.systemWindowInsets}" +
                " top:${rootWindowInsets.systemWindowInsetTop}" +
                " bottom${rootWindowInsets.systemWindowInsetBottom}" +
                " icon translationY:${splashScreenViewProvider.iconView.translationY}")
    }

    private fun getRemainingDuration(splashScreenView: SplashScreenViewProvider): Long {
        // Get the duration of the animated vector drawable.
        val animationDuration = splashScreenView.iconAnimationDurationMillis

        // Get the start time of the animation.
        val animationStart = splashScreenView.iconAnimationStartMillis

        // Calculate the remaining duration of the animation.
        return if (animationDuration == 0L || animationStart == 0L)
            defaultExitDuration
        else (animationDuration - SystemClock.uptimeMillis() + animationStart)
            .coerceAtLeast(0L)
    }
}