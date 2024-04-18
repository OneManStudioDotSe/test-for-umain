package se.onemanstudio.test

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import se.onemanstudio.test.umain.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class OneManApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeModules() //initialize the tools that we use in the app
    }

    private fun initializeModules() {
        instance = this

        // "There are no Tree implementations installed by default because
        // every time you log in production, a puppy dies."
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        private var instance: OneManApplication? = null
    }
}
