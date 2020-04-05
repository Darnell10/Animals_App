package com.example.animals_app.dependency_injection

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.animals_app.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class PrefsModule {
    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    fun providesSharedPreferences(application: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(application)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideActivitySharedPreferences(activity: AppCompatActivity): SharedPreferencesHelper {
        return SharedPreferencesHelper(activity)
    }
}

//Don't really need this for this app. just becoming familiar with Dagger.
const val CONTEXT_APP = "Application Context"
const val CONTEXT_ACTIVITY = "Activity Context"

@Qualifier
annotation class TypeOfContext(val type: String)