package com.example.animals_app

import com.example.animals_app.dependency_injection.ApiModule
import com.example.animals_app.model.AnimalService

class ApiModuleTest(val mockService: AnimalService) : ApiModule() {
    override fun provideAnimalService(): AnimalService {
        return mockService
    }
}