package com.spuldz.praksesprojekts.core.di

import android.content.Context
import androidx.room.Room
import com.spuldz.praksesprojekts.core.database.AppDatabase
import com.spuldz.praksesprojekts.core.database.dao.GameStateDAO
import com.spuldz.praksesprojekts.core.database.dao.PreferencesDAO
import com.spuldz.praksesprojekts.core.database.dao.ScoreDAO
import com.spuldz.praksesprojekts.core.database.dao.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
private const val DATABASE_NAME = "database"

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providePreferencesDao(database: AppDatabase): PreferencesDAO = database.preferencesDao()

    @Provides
    @Singleton
    fun provideScoreDao(database: AppDatabase): ScoreDAO = database.scoreDao()

    @Provides
    @Singleton
    fun provideGameStateDao(database: AppDatabase): GameStateDAO = database.gameStateDao()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDAO = database.userDao()
}
