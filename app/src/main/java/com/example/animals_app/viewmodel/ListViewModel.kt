package com.example.animals_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animals_app.model.Animal

class ListViewModel(application: Application) : AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    fun refresh() {
        getAnimals()
    }

    private fun getAnimals() {
        val a = Animal("Bear")
        val b = Animal("Bird")
        val c = Animal("Elephant")
        val d = Animal("Dog")
        val e = Animal("Cat")


        val animalList: ArrayList<Animal> = arrayListOf(a, b, c, d, e)

        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}