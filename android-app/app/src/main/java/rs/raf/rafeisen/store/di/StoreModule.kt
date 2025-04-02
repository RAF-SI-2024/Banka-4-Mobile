package rs.raf.rafeisen.store.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import rs.raf.rafeisen.security.Encryption
import rs.raf.rafeisen.serialization.store.CredentialsSerialization
import rs.raf.rafeisen.serialization.store.StringSerializer
import rs.raf.rafeisen.serialization.store.UserAccountsSerialization
import rs.raf.rafeisen.store.Credential
import rs.raf.rafeisen.store.UserAccount

@Module
@InstallIn(SingletonComponent::class)
object StoreModule {
    @Provides
    @Singleton
    fun provideCredentialsStore(
        @ApplicationContext context: Context,
        encryption: Encryption,
    ): DataStore<Set<Credential>> =
        DataStoreFactory.create(
            produceFile = { context.dataStoreFile("credentials.json") },
            serializer = CredentialsSerialization(encryption = encryption),
        )

    @Provides
    @Singleton
    fun provideUserAccountsStore(
        @ApplicationContext context: Context,
        encryption: Encryption,
    ): DataStore<List<UserAccount>> =
        DataStoreFactory.create(
            produceFile = { context.dataStoreFile("accounts.json") },
            serializer = UserAccountsSerialization(encryption = encryption),
        )

    @Provides
    @Singleton
    @ActiveAccountDataStore
    fun provideActiveAccountStore(@ApplicationContext context: Context): DataStore<String> =
        DataStoreFactory.create(
            produceFile = { context.dataStoreFile("active_account.txt") },
            serializer = StringSerializer(),
        )
}

@Qualifier
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class ActiveAccountDataStore
