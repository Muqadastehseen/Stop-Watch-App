package com.example.stop_watch

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.stop_watch.databinding.ActivityAlarmClockBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

import java.util.Calendar

class Alarm_clock : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmClockBinding
    private lateinit var picker: MaterialTimePicker
    private lateinit var calender: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var binding = ActivityAlarmClockBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ////////////////////////////Notification

        createNotificationChannel()


        // Btn Lintener
        binding.selectTimeBtn.setOnClickListener {

            showTimePicker()

        }

        ///////// set Alarm btn/////

        binding.setAlarmBtn.setOnClickListener {
            setAlarm()
        }

        /////////cancel Alarm btn/////
        binding.cancelAlarmBtn.setOnClickListener {

            cancelAlarm()

        }
        binding.StopwatchBtn.setOnClickListener {
            val intent=Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }


    }

    //Notification
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.O) {
            val name: CharSequence = "foxandroidReminderChannel"
            val description = "Channel for Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("foxandroid", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)

        }
    }

    //Notification


    private fun cancelAlarm() {

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show()

    }

    private fun setAlarm() {

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setRepeating(

            //RTC mean real time clock that matches with the world clock timing

            AlarmManager.RTC_WAKEUP, calender.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show()


    }

    private fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Time")
            .build()

        //picker.show(supportFragmentManager, "TAG")
        picker.show(supportFragmentManager, "foxandrid")

        picker.addOnPositiveButtonClickListener {
            if (picker.hour > 12) {
                binding.selectTimeBtn.text =
                    String.format("%02d", picker.hour - 12) + ":" + String.format(
                        "%02d",
                        picker.minute

                    ) + "PM"
            } else {

                String.format("%02d", picker.hour) + ":" + String.format(
                    "%02d",
                    picker.minute

                ) + "AM"

            }

            calender = Calendar.getInstance()
            calender[Calendar.HOUR_OF_DAY] = picker.hour
            calender[Calendar.MINUTE] = picker.minute
            calender[Calendar.SECOND] = 0
            calender[Calendar.MILLISECOND] = 0

        }


    }


}






