package com.example.custard.domain.post.repository.date;

import com.example.custard.domain.post.dto.info.DateInfo
import com.example.custard.domain.post.model.date.Date
import com.example.custard.domain.post.service.date.DateStore
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class DateStoreImpl(
    private val dateRepository: DateRepository
) : DateStore {
    override fun saveDateIfNotExists(date: Date): Date {
        return dateRepository.existsDateByDateAndTime(date.date, date.time)
            .takeIf { !it }
            ?.let { dateRepository.save(date) }
            ?: dateRepository.findByDateAndTime(date.date, date.time)!!
    }

    override fun findOrCreateDate(dates: List<DateInfo>): List<Date> {
        return dates.map { dateInfo ->
            dateRepository.findByDateAndTime(dateInfo.date, dateInfo.time)
                ?: dateRepository.save(Date(dateInfo.date, dateInfo.time))
        }
    }

    override fun deleteDate(date: Date) {
        dateRepository.delete(date)
    }

    override fun deleteDates(dates: List<Date>) {
        dates.forEach { dateRepository.delete(it) }
    }
}
