package com.example.animals_app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.Callable
import java.util.concurrent.Executor

class ListViewModelTest {
    @get : Rule
    var rule = InstantTaskExecutorRule()

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