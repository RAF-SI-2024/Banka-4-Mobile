package rs.raf.rafeisen.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    // Logging
    @Provides
    @IntoSet
    fun timberLogger(): Timber.Tree = Timber.DebugTree()
}
