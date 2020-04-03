package com.example.animals_app.dependency_injection

import android.app.Application
import com.example.animals_app.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(application)
    }
}