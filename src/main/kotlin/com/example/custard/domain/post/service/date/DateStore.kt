package com.example.custard.domain.post.service.date

import com.example.custard.domain.post.dto.info.PostDateInfo
import com.example.custard.domain.post.model.date.Date

interface DateStore {
    fun saveDateIfNotExists(date: Date): Date
    fun findOrCreateDate(dates: List<PostDateInfo>): List<Date>
    fun deleteDates(dates: List<Date>)
}