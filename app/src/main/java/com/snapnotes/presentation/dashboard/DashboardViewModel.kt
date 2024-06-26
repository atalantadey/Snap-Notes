package com.snapnotes.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.snapnotes.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel@Inject constructor(
    private val subjectRepository: SubjectRepository
) :ViewModel() {
    //Manage State and Events

}