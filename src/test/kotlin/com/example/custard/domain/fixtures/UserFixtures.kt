package com.example.custard.domain.fixtures

import com.example.custard.config.auth.oauth2.AuthProvider
import com.example.custard.domain.user.model.User

class UserFixtures {
    companion object {
        val kakaoProvider = AuthProvider.KAKAO
        val googleProvider = AuthProvider.GOOGLE
        val naverProvider = AuthProvider.NAVER

        const val testUserEmail: String = "test@test.com"
        const val testUserName: String = "테스트 유저 이름"
        const val testUserProfileImageUrl: String = "testuser_profile_image_url"

        const val modifiedTestUserName: String = "수정된 테스트 유저 이름"
        const val modifiedTestUserProfileImageUrl: String = "modified_testuser_profile_image_url"

        const val sellerEmail: String = "seller@test.com"
        const val sellerName: String = "판매자 이름"
        const val sellerProfileImageUrl: String = "seller_profile_image_url"

        const val buyerEmail: String = "buyer@test.com"
        const val buyerName: String = "구매자 이름"
        const val buyerProfileImageUrl: String = "buyer_profile_image_url"

        val testUser: User = User(kakaoProvider, testUserEmail, testUserName, testUserProfileImageUrl)
        val modifiedTestUser: User = User(kakaoProvider, testUserEmail, modifiedTestUserName, modifiedTestUserProfileImageUrl)

        val seller: User = User(kakaoProvider, sellerEmail, sellerName, sellerProfileImageUrl)
        val buyer: User = User(kakaoProvider, buyerEmail, buyerName, buyerProfileImageUrl)
    }
}