package rs.raf.rafeisen.db.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rs.raf.rafeisen.db.AppDatabaseBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(builder: AppDatabaseBuilder) = builder.build()
}
