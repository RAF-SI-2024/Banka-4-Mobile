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
import rs.raf.rafeisen.model.TotpSecret
import rs.raf.rafeisen.security.Encryption
import rs.raf.rafeisen.serialization.CredentialsSerialization
import rs.raf.rafeisen.serialization.StringSerializer
import rs.raf.rafeisen.serialization.TotpSecretsSerialization
import rs.raf.rafeisen.serialization.UserAccountsSerialization
import rs.raf.rafeisen.store.Credential
import rs.raf.rafeisen.store.UserAccount
import javax.inject.Qualifier
import javax.inject.Singleton

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
    fun provideActiveAccountStore(
        @ApplicationContext context: Context,
    ): DataStore<String> =
        DataStoreFactory.create(
            produceFile = { context.dataStoreFile("active_account.txt") },
            serializer = StringSerializer(),
        )

    @Provides
    @Singleton
    fun provideTotpSecretsStore(
        @ApplicationContext context: Context,
        encryption: Encryption
    ): DataStore<List<TotpSecret>> = DataStoreFactory.create(
        produceFile = { context.dataStoreFile("totp_secrets.json") },
        serializer = TotpSecretsSerialization(encryption = encryption)
    )
}

@Qualifier
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class ActiveAccountDataStore
