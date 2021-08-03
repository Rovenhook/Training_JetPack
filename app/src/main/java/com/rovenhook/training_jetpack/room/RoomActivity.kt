package com.rovenhook.training_jetpack.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.room.Room
import com.rovenhook.training_jetpack.databinding.ActivityRoomBinding
import kotlinx.coroutines.*

class RoomActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoomBinding
    var arr: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room
            .databaseBuilder(this, AnimalEntitiesDataBase::class.java, "animalentity")
            .build()
        val dao = db.animalEntityDao()
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arr)
        binding.listViewRoomItems.adapter = adapter

        val job1 = MainScope().launch(Dispatchers.Default) {
            arr = dao.getAll().map { it.name }.toMutableList()
            adapter.clear()
            adapter.addAll(arr)
        }

        binding.buttonAddToRoom.setOnClickListener {
            if (binding.editTextAddToRoom.text.isNotEmpty()) {
                val str: String = binding.editTextAddToRoom.text.toString()
                binding.editTextAddToRoom.text.clear()
                val animent: AnimalEntity = AnimalEntity(str)

                // Not a UI scope
                val job2 = MainScope().launch(Dispatchers.Default) {
                    dao.insert(animent)
                    arr = dao.getAll().map { it.name }.toMutableList()
                }
                // UI scope
                val job3 = MainScope().launch {
                    job2.join() // wait for job2 to finish first
                    adapter.clear()
                    adapter.addAll(arr)
                }
            }
        }

        binding.buttonClearDBandList.setOnClickListener {
            // Not a UI scope
            MainScope().launch(Dispatchers.Default) {
                dao.deleteAll()
            }
            // UI scope
            MainScope().launch {
                adapter.clear()
            }
        }
    }
}