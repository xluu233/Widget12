package com.example.widget12

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class TodoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "TodoReceiver onReceive")
    }
}