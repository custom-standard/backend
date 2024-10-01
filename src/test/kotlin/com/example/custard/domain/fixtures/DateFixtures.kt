package com.example.custard.domain.fixtures

import com.example.custard.domain.post.dto.info.DateInfo
import com.example.custard.domain.post.model.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateFixtures {
    companion object {
        val datetime_2024_01_01: LocalDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0)
        val datetime_2024_01_02: LocalDateTime = LocalDateTime.of(2024, 1, 2, 0, 0, 0)
        val datetime_2024_01_03: LocalDateTime = LocalDateTime.of(2024, 1, 3, 0, 0, 0)

        val date_2024_01_01: LocalDate = datetime_2024_01_01.toLocalDate()
        val date_2024_01_02: LocalDate = datetime_2024_01_02.toLocalDate()
        val date_2024_01_03: LocalDate = datetime_2024_01_03.toLocalDate()

        val time_14_00: LocalTime = LocalTime.of(14, 0, 0)

        val dateEntity_2024_01_01_14_00: Date = Date(date_2024_01_01, time_14_00)
        val dateEntity_2024_01_01_null: Date = Date(date_2024_01_01, null)
        val dateEntity_2024_01_02_14_00: Date = Date(date_2024_01_02, time_14_00)
        val dateEntity_2024_01_02_null: Date = Date(date_2024_01_02, null)
        val dateEntity_2024_01_03_null: Date = Date(date_2024_01_03, null)

        val dateEntities: List<Date> = listOf(dateEntity_2024_01_01_null, dateEntity_2024_01_02_14_00)
        val modifiedDateEntities: List<Date> = listOf(dateEntity_2024_01_01_14_00, dateEntity_2024_01_02_null)

        val dateInfo_2024_01_01_14_00: DateInfo = DateInfo(date_2024_01_01, time_14_00)
        val dateInfo_2024_01_01_null: DateInfo = DateInfo(date_2024_01_01, null)
        val dateInfo_2024_01_02_14_00: DateInfo = DateInfo(date_2024_01_02, time_14_00)
        val dateInfo_2024_01_02_null: DateInfo = DateInfo(date_2024_01_02, null)

        val dateInfos: List<DateInfo> = listOf(dateInfo_2024_01_01_null, dateInfo_2024_01_02_14_00)
        val modifiedDateInfos: List<DateInfo> = listOf(dateInfo_2024_01_01_14_00, dateInfo_2024_01_02_null)
    }
}