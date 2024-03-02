package com.example.demo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @description: 用户实体类
 * @author yanglei
 * @date :2024/3/2
 * @version 1.0.0
 */
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "nickname") val nickname: String = "",
    @ColumnInfo(name = "age") val age: Int = 99,
)