package com.snapnotes.data.repository

import com.snapnotes.data.local.SessionsDao
import com.snapnotes.data.local.SubjectDao
import com.snapnotes.domain.model.Sessions
import com.snapnotes.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDao: SessionsDao
):SessionRepository {
    override suspend fun insertSession(session: Sessions) {
        sessionDao.insertSession(session)
    }

    override suspend fun deleteSession(session: Sessions) {
        TODO("Not yet implemented")
    }

    override fun getAllSessions(): Flow<List<Sessions>> {
        TODO("Not yet implemented")
    }

    override fun getRecentFiveSessions(): Flow<List<Sessions>> {
        TODO("Not yet implemented")
    }

    override fun getRecentTenSessionsForSubject(subjectId: Int): Flow<List<Sessions>> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionsDuration(): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionsDurationBySubjectId(subjectId: Int): Flow<Long> {
        TODO("Not yet implemented")
    }
}