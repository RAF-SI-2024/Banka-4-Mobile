package rs.raf.rafeisen

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RAFeisenApplication : Application() {
    @Inject
    lateinit var loggers: Set<@JvmSuppressWildcards Timber.Tree>

    override fun onCreate() {
        super.onCreate()
        loggers.forEach {
            Timber.plant(it)
        }
    }
}
