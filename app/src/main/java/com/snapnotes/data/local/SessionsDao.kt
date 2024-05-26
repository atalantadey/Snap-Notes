package com.snapnotes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.snapnotes.domain.model.Sessions
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionsDao {

    @Insert
    suspend fun insertSession(session:Sessions)

    @Delete
    suspend fun deleteSession(session: Sessions)

    @Query("SELECT * FROM Sessions")
    fun getAllSessions(): Flow<List<Sessions>>

    @Query("SELECT * FROM Sessions WHERE sessionSubjectId = :subjectId")
    fun getRecentSessionsForSubject(subjectId: Int): Flow<List<Sessions>>

    @Query("SELECT SUM(duration) FROM Sessions")
    fun getTotalSessionsByDuration(): Flow<Long>

    @Query("SELECT SUM(duration) FROM Sessions WHERE sessionSubjectId = :subjectId")
    fun getTotalSessionsByDurationBySubjectId (subjectId: Int): Flow<Long>

    @Query("DELETE FROM Sessions WHERE sessionSubjectId = :subjectId")
    fun deleteSessionsBySubjectId (subjectId: Int)

}