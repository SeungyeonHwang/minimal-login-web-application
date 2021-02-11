package com.login.kotlin

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(
    var userId: String,
    var password: String,
    @Id @GeneratedValue var id: Long? = null) //prime 키 생성, 자동생성

//원래
//CREATE TABLE Uuser (
//    userId TEXT NOT NULL,
//    password VARCHAR(20) NOT NULL,
//    id LONG NOT NULL PRIME KEY
//);
