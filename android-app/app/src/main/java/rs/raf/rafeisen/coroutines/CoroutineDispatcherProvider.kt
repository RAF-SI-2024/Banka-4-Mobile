package rs.raf.rafeisen.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoroutineDispatcherProvider
    @Inject
    constructor() {
        fun io(): CoroutineDispatcher = Dispatchers.IO

        fun main(): CoroutineDispatcher = Dispatchers.Main

        fun default(): CoroutineDispatcher = Dispatchers.Default
    }
