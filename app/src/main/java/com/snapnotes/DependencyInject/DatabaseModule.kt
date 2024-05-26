package com.snapnotes.DependencyInject

import android.app.Application
import androidx.room.Room
import com.snapnotes.data.local.AppDatabase
import com.snapnotes.data.local.SessionsDao
import com.snapnotes.data.local.SubjectDao
import com.snapnotes.data.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ):AppDatabase{
        return Room.databaseBuilder(
            context = application,
            AppDatabase::class.java,
            "snapnotes.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSubjectDao(database: AppDatabase):SubjectDao{
        return database.subjectDao()
    }
    @Provides
    @Singleton
    fun provideSessionsDao(database: AppDatabase):SessionsDao{
        return database.sessionDao()
    }
    @Provides
    @Singleton
    fun provideTaskDao(database: AppDatabase):TaskDao{
        return database.taskDao()
    }

}