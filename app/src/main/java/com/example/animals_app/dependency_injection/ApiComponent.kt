package com.example.animals_app.dependency_injection

import com.example.animals_app.model.AnimalService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service:AnimalService)
}