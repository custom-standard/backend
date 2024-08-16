package com.example.custard.domain.fixtures

import com.example.custard.domain.post.model.PostDate

class PostDateFixtures {
    companion object {
        val postDates: List<PostDate> = listOf(
            PostDate(PostFixtures.salePost, DateFixtures.dateEntity_2024_01_01_null),
            PostDate(PostFixtures.salePost, DateFixtures.dateEntity_2024_01_02_14_00)
        )

        val modifiedPostDates: List<PostDate> = listOf(
            PostDate(PostFixtures.salePost, DateFixtures.dateEntity_2024_01_01_14_00),
            PostDate(PostFixtures.salePost, DateFixtures.dateEntity_2024_01_02_null)
        )
    }
}