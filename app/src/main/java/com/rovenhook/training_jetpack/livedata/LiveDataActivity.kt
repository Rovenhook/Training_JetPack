package com.rovenhook.training_jetpack.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.rovenhook.training_jetpack.databinding.MainLivedataBinding

class LiveDataActivity : AppCompatActivity() {
    lateinit var binding: MainLivedataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLivedataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create a LiveData with a String
        // by means we entrust initialising to somebody else (to lazy function here)
        // lazy is lambda function that launches only once and only when first time called
        // can be changed to currentName = MutableLiveData<String>()
        val currentName: MutableLiveData<String> by lazy {
            MutableLiveData<String>()
        }

        // Create the observer which updates the UI.
        val nameObserver = Observer<String> { newName ->
            // Update the UI, in this case, a TextView.
            binding.textViewTextDisplay.text = newName
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        currentName.observe(this, nameObserver)

        // Here we change value of LiveData then Observer sees it and updates TextView
        binding.buttonSetText.setOnClickListener {
            currentName.value = binding.editTextTextToSet.text.toString()
        }

        // Advanced example without clicking any button (has nothing to do with LiveData itself)
        binding.editTextTextToSet.doOnTextChanged { text, start, before, count ->
            currentName.value = binding.editTextTextToSet.text.toString()
        }



        // Second example

        // Now example of text change with LIveData in NotAViewModel class
        // instance of the class where we have our livedata object
        val notAViewModel = NotAViewModel()
        // new observer to observe new livedata and make this action on any change in livedata
        // observer gets newValue from livedata it connected to when there is any change in livedata
        val secondObserver = Observer<String> { newValue ->
            binding.textViewTextDisplay.text = "From other class! \n$newValue"
        }
        // connect the observer and the livedata together
        notAViewModel.distantLiveData.observe(this, secondObserver)

        // the action that changes livedata. That change is seen by the observer and used in
        // the above set text action
        binding.editTextTextForDistantControl.doOnTextChanged { text, start, before, count ->
            notAViewModel.distantLiveData.value = binding.editTextTextForDistantControl.text.toString()
        }

        // Architecture notes for more sophisticated structure:
        // LiveData is usually placed in ViewModel or somewhere that is not UI
        // Observer is usually created in UI file (activity or fragment)
        // Connection between two is usually made in the file with the observer (UI, activity, fragment)
        // Changes to LiveData are made in UI usually through UI: buttons, edittext, etc.
        // So in short everything is in UI except LiveData object

    }

}