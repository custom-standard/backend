package com.example.custard.domain.post.repository.date

import com.example.custard.domain.post.model.Post
import com.example.custard.domain.post.model.date.Date
import com.example.custard.domain.post.model.date.PostDate
import com.example.custard.domain.post.service.date.DateStore
import com.example.custard.domain.post.service.date.PostDateStore
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PostDateStoreImpl(
    private val postDateRepository: PostDateRepository,
    private val dateStore: DateStore,
) : PostDateStore {
    override fun existsPostDateByDate(date: Date): Boolean {
        return postDateRepository.existsPostDateByDate(date)
    }

    @Transactional
    override fun savePostDate(post: Post, dates: List<Date>): List<PostDate> {
        val savedDates = dates.map { date -> dateStore.saveDateIfNotExists(date) }
        val postDates = savedDates.map { PostDate(post, it) }
        return postDateRepository.saveAll(postDates)
    }

    @Transactional
    override fun updatePostDate(post: Post, dates: List<Date>): List<PostDate> {
        val dateEntities = dates.map { date -> dateStore.saveDateIfNotExists(date) }

        val postDates = postDateRepository.findAllByPost(post)
        val newPostDates = dateEntities.map { PostDate(post, it) }

        val postDatesMap = postDates.associateBy { it.date }
        val newPostDatesMap = newPostDates.associateBy { it.date }

        val toDelete = postDates.filter { !newPostDatesMap.containsKey(it.date) }
        val toSave = newPostDates.filter { !postDatesMap.containsKey(it.date) }

        deletePostDate(toDelete)

        toDelete.forEach { postDate ->
            if (!existsPostDateByDate(postDate.date)) dateStore.deleteDate(postDate.date)
        }

        postDateRepository.saveAll(toSave)

        return postDateRepository.findAllByPost(post)
    }

    @Transactional
    override fun deletePostDate(postDates: List<PostDate>) {
        postDateRepository.deleteAll(postDates)

        val dates = postDates.map { it.date }
        val toDelete = dates.filter { date ->
            !existsPostDateByDate(date)
        }

        dateStore.deleteDates(toDelete)
    }

    @Transactional
    override fun deleteAllByPost(post: Post) {
        val postDates = postDateRepository.findAllByPost(post)
        deletePostDate(postDates)
    }
}