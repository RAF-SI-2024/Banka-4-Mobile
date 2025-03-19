package rs.raf.rafeisen.security.di

import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rs.raf.rafeisen.security.Encryption
import rs.raf.rafeisen.security.NoEncryption

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {
    @Provides
    fun provideEncryption(): Encryption = NoEncryption() // TODO: this can and should be improved

    @Provides
    fun provideDatabaseOpenHelper(): SupportSQLiteOpenHelper.Factory {
        return FrameworkSQLiteOpenHelperFactory()
    }
}
