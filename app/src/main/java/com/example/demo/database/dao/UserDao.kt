package com.example.demo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.demo.database.entity.User

/**
 * @description: 数据访问对象，用于对数据库的数据进行增删改查操作
 * @author yanglei
 * @date :2024/3/2
 * @version 1.0.0
 */
@Dao
interface UserDao {
    // 数据访问对象
    @Insert
    fun insert(vararg user: User)

    @Update
    fun update(vararg user: User)

    @Delete
    fun delete(vararg user: User)

    @Query("delete from user where id=:id")
    fun deleteById(id: Int)

    @Query("select * from user")
    fun findAll(): List<User>

    /**
     * 根据用户名查找用户
     * @return 查找到的用户（可为空）
     */
    @Query("select * from user where username = :username limit 1")
    fun findByUsername(username: String): User?
}