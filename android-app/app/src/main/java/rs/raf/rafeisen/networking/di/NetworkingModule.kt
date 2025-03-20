package rs.raf.rafeisen.networking.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import rs.raf.rafeisen.api.auth.AuthApi
import rs.raf.rafeisen.api.client.ClientApi
import rs.raf.rafeisen.networking.api.AuthClientServiceInternalRequests
import rs.raf.rafeisen.networking.api.ClientRoute
import rs.raf.rafeisen.networking.utils.buildAuthenticatedOkHttpClient
import rs.raf.rafeisen.store.ActiveAccountStore
import rs.raf.rafeisen.store.CredentialsStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
        credentialsStore: CredentialsStore,
        activeAccountStore: ActiveAccountStore,
        authClientServiceInternalRequests: AuthClientServiceInternalRequests
    ): OkHttpClient {
        return buildAuthenticatedOkHttpClient(
            credentialsStore = credentialsStore,
            activeAccountStore = activeAccountStore,
            authClientServiceInternalRequests = authClientServiceInternalRequests,
        ) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                },
            )
        }
    }

    @Singleton
    @Provides
    fun provideAuthClientServiceInternalRequests(
        apiInfo: ApiInfo,
        appJson: Json,
    ): AuthClientServiceInternalRequests =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        },
                    )
                    .build()
            )
            .baseUrl(apiInfo.baseUrl)
            .addConverterFactory(appJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AuthClientServiceInternalRequests::class.java)

    @Singleton
    @Provides
    fun provideAppJson() =
        Json {
            ignoreUnknownKeys = true
        }

    @Singleton
    @Provides
    fun provideRetrofit(
        authenticatedOkHttpClient: OkHttpClient,
        apiInfo: ApiInfo,
        appJson: Json,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(apiInfo.baseUrl)
            .client(authenticatedOkHttpClient)
            .addConverterFactory(
                appJson.asConverterFactory("application/json".toMediaType())
            )
            .build()

    @Singleton
    @Provides
    fun provideClientRouteRetrofit(
        retrofit: Retrofit
    ): ClientRoute =
        retrofit.create(ClientRoute::class.java)

    @Singleton
    @Provides
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideClientApi(
        retrofit: Retrofit
    ): ClientApi =
        retrofit.create(ClientApi::class.java)
}
