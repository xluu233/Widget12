package com.example.widget12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val date = intent?.data
        date?.let {
            val idRes = Integer.parseInt(it.schemeSpecificPart)
            when(idRes){
                R.id.add -> {
                    Toast.makeText(this,"Add TODO",Toast.LENGTH_LONG).show()
                }
                R.id.duck_debug -> {
                    Toast.makeText(this,"小黄鸭调试",Toast.LENGTH_LONG).show()
                }
                else -> {
                    Log.d(TAG, "onCreate: unknow")
                }
            }
        }
    }
}