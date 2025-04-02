package rs.raf.rafeisen.db.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import rs.raf.rafeisen.db.AppDatabaseBuilder

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(builder: AppDatabaseBuilder) = builder.build()
}
