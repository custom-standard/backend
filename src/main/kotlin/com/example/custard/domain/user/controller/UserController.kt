package com.example.custard.domain.user.controller

import com.example.custard.domain.user.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController (
    private val userService: UserService,
) {
    /* 회원정보 조회 */

    /* 회원정보 수정 */

    /* 회원 탈퇴 */
}