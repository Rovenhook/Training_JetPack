package com.rovenhook.training_jetpack.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.rovenhook.training_jetpack.R
import com.rovenhook.training_jetpack.databinding.ActivityViewModelBinding

class ViewModelActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewModelBinding
    lateinit var viewModelLink: ViewModelClass
    lateinit var notViewModelLink: NotAViewModelClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // this way this activity is always given the same ViewModel object after rotation
        viewModelLink = ViewModelProvider(this).get(ViewModelClass::class.java)
        notViewModelLink = NotAViewModelClass()

        binding.textViewNotAviewmodel.text = notViewModelLink.str
        binding.textviewViewModel.text = viewModelLink.str

        binding.buttonAddText.setOnClickListener {
            val str = binding.editTextTextPersonName.text.toString()
            viewModelLink.str = str
            notViewModelLink.str = str
            binding.textViewNotAviewmodel.text = notViewModelLink.str
            binding.textviewViewModel.text = viewModelLink.str
        }

        // type text, see that both textviews have the same text and then rotate the screen to see
        // that only text connected to ViewModel survived the rotation
        // thats a special ViewModel class feature
    }
}