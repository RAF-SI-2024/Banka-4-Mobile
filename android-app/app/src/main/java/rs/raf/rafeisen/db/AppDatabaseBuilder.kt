package rs.raf.rafeisen.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteOpenHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppDatabaseBuilder
@Inject
constructor(
    @ApplicationContext private val context: Context,
    private val openHelperFactory: SupportSQLiteOpenHelper.Factory,
) {
    init {
        System.loadLibrary("sqlcipher")
    }

    fun build(): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "${context.dataDir.path}/databases/rafeisen_v1.db",
        )
            .openHelperFactory(openHelperFactory)
            .enableMultiInstanceInvalidation()
            .fallbackToDestructiveMigration()
            .build()
}
