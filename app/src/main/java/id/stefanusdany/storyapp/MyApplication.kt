package id.stefanusdany.storyapp

import android.app.Application
import id.stefanusdany.data.di.CoreComponent
import id.stefanusdany.data.di.DaggerCoreComponent
import id.stefanusdany.storyapp.di.AppComponent
import id.stefanusdany.storyapp.di.DaggerAppComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}