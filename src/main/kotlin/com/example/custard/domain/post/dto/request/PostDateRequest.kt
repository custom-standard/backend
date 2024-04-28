package com.example.custard.domain.post.dto.request

import com.example.custard.domain.post.dto.info.PostDateInfo
import java.time.LocalDate
import java.time.LocalTime

class PostDateRequest (
    val date: LocalDate,
    val time: LocalTime?,
) {
    fun createInfo(): PostDateInfo {
        return PostDateInfo(date, time)
    }
}