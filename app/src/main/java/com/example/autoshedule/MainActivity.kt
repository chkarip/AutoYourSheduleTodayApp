package com.example.autoshedule

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.view.View
import android.view.View.OnFocusChangeListener
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    var nameOfTimer: String? = ""
    var hourOfTimer:String?=""
    var minutesOfTimer:String?=""
    var secondsOfTimer:String?=""
    var hour: Int = 0
    var minutes: Int = 0
    var seconds: Int = 0
    var handler: Handler = Handler()
    var runnable: Runnable = Runnable { }
    var count: CountDownTimer? = null
    var formatter: NumberFormat = DecimalFormat("00")
    var formatHour: String = formatter.format(hour) // ----> 01
    var formatMinutes: String = formatter.format(minutes) // ----> 01
    var formatHSeconds: String = formatter.format(seconds) // ----> 01



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view3.setBackgroundColor(Color.GRAY)
        val sharePreferences = this.getSharedPreferences("com.example.autoshedule", MODE_PRIVATE)
        var storedNameOfTimer = sharePreferences.getString("nameOfTimer", "")
        var storedHourOfTimer = sharePreferences.getString("hourOfTimer", "00")
        var storedMinutesOfTimer = sharePreferences.getString("minutesOfTimer", "00")
        var storeSecondsOfTimer = sharePreferences.getString("secondsOfTimer", "00")
        if (storedNameOfTimer != null) {
            nameOfTimer = storedNameOfTimer
            nameID.text = SpannableStringBuilder("$nameOfTimer")
        } else {
            nameOfTimer =""
            nameID.text = SpannableStringBuilder("$nameOfTimer")
            nameID.setBackgroundColor(Color.WHITE)
            nameID.setTextColor(Color.BLACK)
        }
        if (storedHourOfTimer != null) {
            hour = storedHourOfTimer.toInt()
            var formatHour: String = formatter.format(hour)
            hourID.text = SpannableStringBuilder("$formatHour")

        } else {
            hour=0
            var formatHour: String = formatter.format(hour)
            hourID.text = SpannableStringBuilder("$formatHour")
        }
        if (storedMinutesOfTimer != null) {
            minutes = storedMinutesOfTimer.toInt()
            var formatMinutes: String = formatter.format(minutes)
            minutesID.text = SpannableStringBuilder("$formatMinutes")
        } else {
            minutes=0
            var formatMinutes: String = formatter.format(minutes)
            minutesID.text = SpannableStringBuilder("$formatMinutes")
        }
        if (storeSecondsOfTimer != null) {
            seconds = storeSecondsOfTimer.toInt()
            var formatSeconds: String = formatter.format(seconds)
            secondsID.text = SpannableStringBuilder("$formatSeconds")
        } else {
            seconds=0
            var formatSeconds: String = formatter.format(seconds)
            secondsID.text = SpannableStringBuilder("$formatSeconds")
        }

        secondsID.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                var secondsCast =secondsID.text.get(0).toString() + secondsID.text.get(1).toString()
                seconds = secondsCast.toInt()
                var minutesCast =minutesID.text.get(0).toString() + minutesID.text.get(1).toString()
                minutes = minutesCast.toInt()
                var hourCast = hourID.text.get(0).toString() + hourID.text.get(1).toString()
                hour = hourCast.toInt()
                var formatHour: String = formatter.format(hour) // ----> 01
                var formatMinutes: String = formatter.format(minutes) // ----> 01
                var formatSeconds: String = formatter.format(seconds) // ----> 01
                hourID.text = SpannableStringBuilder("$formatHour")
                minutesID.text = SpannableStringBuilder("$formatMinutes")
                secondsID.text = SpannableStringBuilder("$formatSeconds")
            }
        })
        minutesID.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                var minutesCast =
                    minutesID.text.get(0).toString() + minutesID.text.get(1).toString()
                minutes = minutesCast.toInt()
                var formatHour: String = formatter.format(hour) // ----> 01
                var formatMinutes: String = formatter.format(minutes) // ----> 01
                var formatHSeconds: String = formatter.format(seconds) // ----> 01
                hourID.text = SpannableStringBuilder("$formatHour")
                minutesID.text = SpannableStringBuilder("$formatMinutes")
                secondsID.text = SpannableStringBuilder("$formatHSeconds")
            }
        })
        hourID.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                var hourCast = hourID.text.get(0).toString() + hourID.text.get(1).toString()
                hour = hourCast.toInt()
                var formatHour: String = formatter.format(hour) // ----> 01
                var formatMinutes: String = formatter.format(minutes) // ----> 01
                var formatHSeconds: String = formatter.format(seconds) // ----> 01
                hourID.text = SpannableStringBuilder("$formatHour")
                minutesID.text = SpannableStringBuilder("$formatMinutes")
                secondsID.text = SpannableStringBuilder("$formatHSeconds")

            }
        })


    }
    fun save(view: View) {
        val sharePreferences = this.getSharedPreferences("com.example.autoshedule", MODE_PRIVATE)
        nameOfTimer = nameID.text.toString()
        sharePreferences.edit().putString("nameOfTimer", nameOfTimer).apply()
        hourOfTimer = hourID.text.toString()
        sharePreferences.edit().putString("hourOfTimer", hourOfTimer).apply()
        minutesOfTimer = minutesID.text.toString()
        sharePreferences.edit().putString("minutesOfTimer", minutesOfTimer).apply()
        secondsOfTimer = secondsID.text.toString()
        sharePreferences.edit().putString("secondsOfTimer", secondsOfTimer).apply()
        saveButton.visibility=View.INVISIBLE
    }
    fun start(view: View) {
        startButton.visibility = View.INVISIBLE
        saveButton.visibility = View.INVISIBLE
        count = object : CountDownTimer(seconds.toLong() * 1000, 1000) {
            var secondsP0 = seconds.toLong() * 1000
            override fun onTick(secondsP0: Long) {
                seconds = (secondsP0 / 1000).toInt()
                var formatHour: String = formatter.format(hour) // ----> 01
                var formatMinutes: String = formatter.format(minutes) // ----> 01
                var formatHSeconds: String = formatter.format(seconds) // ----> 01
                hourID.text = SpannableStringBuilder("$formatHour")
                minutesID.text = SpannableStringBuilder("$formatMinutes")
                secondsID.text = SpannableStringBuilder("$formatHSeconds")

            }

            override fun onFinish() {
                minutes--
                if (minutes >= 0) {
                    seconds = 59
                    startButton.callOnClick()
                } else if (hour > 0) {
                    seconds = 59
                    minutes = 59
                    hour--
                    startButton.callOnClick()
                } else if (seconds > 0) {
                    startButton.callOnClick()
                } else {
                }
            }

        }.start()

    }

    fun stop(view: View) {
        saveButton.visibility = View.VISIBLE
        startButton.visibility = View.VISIBLE
        count?.cancel()
    }

    fun delete(view: View) {
        count?.cancel()
        hour = 0
        minutes = 0
        seconds = 0
        val sharePreferences = this.getSharedPreferences("com.example.autoshedule", MODE_PRIVATE)
        nameOfTimer = ""
        sharePreferences.edit().putString("nameOfTimer", nameOfTimer).apply()
        nameID.text = SpannableStringBuilder("")
        hourID.text = SpannableStringBuilder("$formatHour")
        minutesID.text = SpannableStringBuilder("$formatMinutes")
        secondsID.text = SpannableStringBuilder("$formatHSeconds")

    }

    fun plus(view: View) {
        seconds++
        if (seconds > 59) {
            seconds = 0
            minutes++
        }
        if (minutes > 59) {
            minutes = 0
            hour++
        }

        var formatHour: String = formatter.format(hour) // ----> 01
        var formatMinutes: String = formatter.format(minutes) // ----> 01
        var formatHSeconds: String = formatter.format(seconds) // ----> 01
        hourID.text = SpannableStringBuilder("$formatHour")
        minutesID.text = SpannableStringBuilder("$formatMinutes")
        secondsID.text = SpannableStringBuilder("$formatHSeconds")

    }
    fun minus(view: View) {
        seconds--
        if (seconds <0) {
            if(minutes>0){
                minutes--
                     seconds = 59}
            else if(hour>0){
                minutes=59
                seconds=59
                hour--
            }
        }
     if(seconds<0)seconds=0
        var formatHour: String = formatter.format(hour) // ----> 01
        var formatMinutes: String = formatter.format(minutes) // ----> 01
        var formatHSeconds: String = formatter.format(seconds) // ----> 01
        hourID.text = SpannableStringBuilder("$formatHour")
        minutesID.text = SpannableStringBuilder("$formatMinutes")
        secondsID.text = SpannableStringBuilder("$formatHSeconds")

    }


}





