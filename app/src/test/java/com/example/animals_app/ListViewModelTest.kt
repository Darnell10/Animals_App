package com.example.animals_app

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animals_app.dependency_injection.ApplicationModule
import com.example.animals_app.dependency_injection.DaggerViewModelComponent
import com.example.animals_app.model.Animal
import com.example.animals_app.model.AnimalService
import com.example.animals_app.model.ApiKey
import com.example.animals_app.util.SharedPreferencesHelper
import com.example.animals_app.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.security.Key
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

    var listViewMock = ListViewModel(application, true)

    private val key = "Test Key"

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

    @Test
    fun getAnimalsSuccess() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val animal = Animal(
            "cow", null, null, null, null, null,
            null
        )
        val animalList: List<Animal> = listOf(animal)

        val testSingle: Single<List<Animal>> = Single.just(animalList)

        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)

        listViewMock.refresh()

        Assert.assertEquals(1, listViewMock.animals.value?.size)
        Assert.assertEquals(false, listViewMock.loadError.value)
        Assert.assertEquals(false, listViewMock.loading.value)
    }

    @Test
    fun getAnimalFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val testSingle: Single<List<Animal>> = Single.error<List<Animal>>(Throwable())
        val keySingle: Single<ApiKey> = Single.just(ApiKey("OK", key))

        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        listViewMock.refresh()

        Assert.assertEquals(null, listViewMock.animals.value)
        Assert.assertEquals(false, listViewMock.loading.value)
        Assert.assertEquals(true, listViewMock.loadError.value)





    }

    @Test
    fun getKeySuccess() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val apiKey = ApiKey("OK", key)
        val keySingle: Single<ApiKey> = Single.just(apiKey)

        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        val animal = Animal(
            "cow", null, null, null, null, null,
            null
        )
        val animalList: List<Animal> = listOf(animal)

        val testSingle: Single<List<Animal>> = Single.just(animalList)
        listViewMock.refresh()

        Assert.assertEquals(1, listViewMock.animals.value?.size)
        Assert.assertEquals(false, listViewMock.loadError.value)
        Assert.assertEquals(false, listViewMock.loading.value)
    }

    @Test
    fun getKeyFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keySingle: Single<ApiKey> = Single.error<ApiKey>(Throwable())

        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        listViewMock.refresh()

        Assert.assertEquals(null,listViewMock.animals.value)
        Assert.assertEquals(false,listViewMock.loading.value)
        Assert.assertEquals(true,listViewMock.loadError.value)

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