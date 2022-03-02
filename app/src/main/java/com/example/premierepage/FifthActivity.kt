package com.example.premierepage

import android.graphics.Color.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_fifth.*

class FifthActivity : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)




      progress_circular.apply {
          progress = 100f
          setProgressWithAnimation(30f,3000)
          progressBarWidth = 12f
          backgroundProgressBarWidth = 16f
          progressBarColorStart = CYAN
          progressBarColorEnd = WHITE
          roundBorder = true
          progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
          // Set background ProgressBar Color
          backgroundProgressBarColor = GRAY
          // or with gradient
          backgroundProgressBarColorStart = WHITE
          backgroundProgressBarColorEnd = rgb(255,69,0)
          backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM


      }
    }
}