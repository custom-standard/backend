package com.example.custard.domain.common.date

import com.example.custard.domain.common.date.dto.DateInfo

interface DateStore {
    fun saveDateIfNotExists(date: Date): Date
    fun findOrCreateDate(dates: List<DateInfo>): List<Date>
    fun deleteDate(date: Date)
    fun deleteDates(dates: List<Date>)
}