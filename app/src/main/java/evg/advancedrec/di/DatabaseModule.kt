package evg.advancedrec.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import evg.advancedrec.data.AppDatabase
import evg.advancedrec.data.dao.DaoCaseRecords
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        app: Application
    ): AppDatabase = Room
        .databaseBuilder(app, AppDatabase::class.java, "applicationDatabase")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDaoCaseRecords(db:AppDatabase): DaoCaseRecords = db.daoCaseRecords()
}