package com.example.custard.domain.order.enums

enum class OrderStatus (
    val index: Int,
    val description: String
) {
    REJECTED(-1, "거절됨"),
    WAITING(0, "요청 대기중"),
    ADJUSTING(1, "조정중"),
    IN_PROGRESS(2, "제작중"),
    COMPLETED(3, "완료됨");
    // TODO : 취소 상태 추가
//    CANCLE(4, "취소됨")

    fun stepForward(): OrderStatus? {
        if (index < 0) return null
        val next = index + 1
        return values().find { it.index == next }
    }

    fun stepBackward(): OrderStatus? {
        if (index < 0) return null
        val prev = index - 1
        return values().find { it.index == prev }
    }
}