package com.rovenhook.training_jetpack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rovenhook.training_jetpack.databinding.ActivityMainBinding
import com.rovenhook.training_jetpack.livedata.LiveDataActivity
import com.rovenhook.training_jetpack.room.RoomActivity
import com.rovenhook.training_jetpack.viewmodel.ViewModelActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTestLiveData.setOnClickListener {
            val intent = Intent(this, LiveDataActivity::class.java)
            startActivity(intent)
        }

        binding.buttonTestViewModel.setOnClickListener {
            val intent = Intent(this, ViewModelActivity::class.java)
            startActivity(intent)
        }

        binding.buttonTestRoom.setOnClickListener {
            val intent = Intent(this, RoomActivity::class.java)
            startActivity(intent)
        }
    }
}