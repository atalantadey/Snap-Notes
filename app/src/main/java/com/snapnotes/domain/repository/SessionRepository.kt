package com.snapnotes.domain.repository

import com.snapnotes.domain.model.Sessions
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun insertSession(session: Sessions)
    suspend fun deleteSession(session: Sessions)
    fun getAllSessions(): Flow<List<Sessions>>
    fun getRecentFiveSessions(): Flow<List<Sessions>>
    fun getRecentTenSessionsForSubject(subjectId: Int): Flow<List<Sessions>>
    fun getTotalSessionsDuration(): Flow<Long>
    fun getTotalSessionsDurationBySubjectId (subjectId: Int): Flow<Long>
}