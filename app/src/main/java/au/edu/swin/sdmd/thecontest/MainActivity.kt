package au.edu.swin.sdmd.thecontest

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mediaPlayerWin = MediaPlayer.create(this, R.raw.digital_watch_alarm_long)
        val mediaPlayerPress = MediaPlayer.create(this, R.raw.beep_short)
        val scoreButton = findViewById<Button>(R.id.scoreButton)
        val stealButton = findViewById<Button>(R.id.stealButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val scoreView = findViewById<TextView>(R.id.scoreView)

        savedInstanceState?. let {
            score = savedInstanceState.getInt("SCORE")
            scoreView.text = score.toString()
        }



        fun isWithinLimits() {
            scoreButton.setEnabled(score < 15)
            stealButton.setEnabled(score > 0)
        }

        isWithinLimits()


        scoreButton.setOnClickListener {
            score++
            scoreView.text = score.toString()
            isWithinLimits()
            mediaPlayerPress.start()
            if(score == 15) mediaPlayerWin.start()
            Log.i("SCORE", "Score:$score")
        }
        stealButton.setOnClickListener {
            score--
            scoreView.text = score.toString()
            isWithinLimits()
            mediaPlayerPress.start()
            if(score != 15) mediaPlayerWin.stop()
            Log.i("STEAL", "Steal:$score")
        }
        resetButton.setOnClickListener {
            score = 0
            scoreView.text = score.toString()
            isWithinLimits()
            mediaPlayerPress.start()
            Log.i("RESET", "Reset:$score")
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SCORE", score)
        Log.i("LIFECYCLE", "saveInstanceState $score")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        score = savedInstanceState.getInt("SCORE")
        Log.i("LIFECYCLE", "restoreInstanceState $score")
    }
}