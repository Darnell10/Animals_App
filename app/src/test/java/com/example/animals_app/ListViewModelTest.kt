package com.example.animals_app

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animals_app.dependency_injection.ApplicationModule
import com.example.animals_app.dependency_injection.DaggerViewModelComponent
import com.example.animals_app.model.AnimalService
import com.example.animals_app.util.SharedPreferencesHelper
import com.example.animals_app.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
import java.util.concurrent.Executor

class ListViewModelTest {
    @get : Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalService: AnimalService

    @Mock
    lateinit var prefs: SharedPreferencesHelper

    val application = Mockito.mock(Application::class.java)

    var listViewMock = ListViewModel(application)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(application))
            .apiModule(ApiModuleTest(animalService))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewMock)
    }


    // create threads to test
    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }

        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler> -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler> -> immediate }
    }
}