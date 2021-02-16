package com.login.kotlin

import org.springframework.data.repository.CrudRepository

//<가져오는 데이터, id 타입>
interface UserRepository : CrudRepository<User, Long> {
    fun findByUserId(userId: String): User
}