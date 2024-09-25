package com.example.custard.domain.post.repository.schedule;

import com.example.custard.domain.post.dto.info.ScheduleInfo
import com.example.custard.domain.post.model.Schedule
import com.example.custard.domain.post.service.date.ScheduleStore
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ScheduleStoreImpl(
    private val scheduleRepository: ScheduleRepository
) : ScheduleStore {
    override fun saveScheduleIfNotExists(schedule: Schedule): Schedule {
        return scheduleRepository.existsScheduleByDateAndTime(schedule.date, schedule.time)
            .takeIf { !it }
            ?.let { scheduleRepository.save(schedule) }
            ?: scheduleRepository.findByDateAndTime(schedule.date, schedule.time)!!
    }

    override fun findOrCreateSchedule(schedules: List<ScheduleInfo>): List<Schedule> {
        return schedules.map { scheduleInfo ->
            scheduleRepository.findByDateAndTime(scheduleInfo.date, scheduleInfo.time)
                ?: scheduleRepository.save(Schedule(scheduleInfo.date, scheduleInfo.time))
        }
    }

    override fun deleteSchedule(schedule: Schedule) {
        scheduleRepository.delete(schedule)
    }

    override fun deleteSchedules(schedules: List<Schedule>) {
        schedules.forEach { scheduleRepository.delete(it) }
    }
}
