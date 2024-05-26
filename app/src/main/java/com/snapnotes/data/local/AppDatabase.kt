package com.snapnotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.snapnotes.domain.model.Sessions
import com.snapnotes.domain.model.Subject
import com.snapnotes.domain.model.Task

@Database(
    entities = [
        Subject::class,
        Sessions::class,
        Task::class], version = 1)
@TypeConverters(colorListConverted::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun subjectDao():SubjectDao
    abstract fun taskDao():TaskDao
    abstract fun sessionDao():SessionsDao
}