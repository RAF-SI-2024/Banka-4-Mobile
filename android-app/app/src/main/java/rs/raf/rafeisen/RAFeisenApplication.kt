package rs.raf.rafeisen

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import timber.log.Timber

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
