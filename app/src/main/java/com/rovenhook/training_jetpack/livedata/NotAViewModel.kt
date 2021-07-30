package com.rovenhook.training_jetpack.livedata


import androidx.lifecycle.MutableLiveData

class NotAViewModel {

    val distantLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    //can be val distantLiveData = MutableLiveData<String>() too
}