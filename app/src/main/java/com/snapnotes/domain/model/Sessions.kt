package com.snapnotes.domain.model

data class Sessions(
    val sessionSubjectId:Int,
    val relatedToSubject:String,
    val date:Long,
    val duration:Long,
    val sessionId:Int

)
