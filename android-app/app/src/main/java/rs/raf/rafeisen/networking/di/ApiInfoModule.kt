package rs.raf.rafeisen.networking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.net.URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiInfoModule {
    @Singleton
    @Provides
    fun apiInfo() = ApiInfo(
        baseUrl = URL("http://192.168.0.195:8080/"
        )
    )
}
