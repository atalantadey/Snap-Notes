package com.snapnotes.DependencyInject

import com.snapnotes.data.repository.SessionRepositoryImpl
import com.snapnotes.data.repository.SubjectRepositoryImpl
import com.snapnotes.data.repository.TaskRepositoryImpl
import com.snapnotes.domain.repository.SessionRepository
import com.snapnotes.domain.repository.SubjectRepository
import com.snapnotes.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindSubjectRepo(impl:SubjectRepositoryImpl):SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepo(impl: TaskRepositoryImpl):TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepo(impl: SessionRepositoryImpl): SessionRepository


}