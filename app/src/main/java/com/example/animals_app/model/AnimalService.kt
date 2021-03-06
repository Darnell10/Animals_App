package com.example.animals_app.model

import com.example.animals_app.dependency_injection.DaggerApiComponent
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AnimalService {
    @Inject
    lateinit var retrofit: AnimalApi
   // private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"

//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .build()
//        .create(AnimalApi::class.java)

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey> {
        return retrofit.getApiKey()
    }

    fun getAnimals(key:String):Single<List<Animal>>{
        return retrofit.getAnimals(key)
    }
}