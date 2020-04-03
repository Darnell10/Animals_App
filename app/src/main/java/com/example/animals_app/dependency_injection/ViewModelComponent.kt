package com.example.animals_app.dependency_injection

import com.example.animals_app.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, PrefsModule::class, ApplicationModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewModel)
}