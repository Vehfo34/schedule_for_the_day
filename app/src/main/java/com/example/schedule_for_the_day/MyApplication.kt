package com.example.schedule_for_the_day


import android.app.Application
import com.example.schedule_for_the_day.data.AppDatabase

class MyApplication : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
}