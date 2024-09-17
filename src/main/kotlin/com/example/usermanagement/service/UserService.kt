package com.example.usermanagement.service

import com.example.usermanagement.model.User

interface UserService {
    fun createUser(user: User): User
    fun getUserById(id: Long): User?
    fun getAllUsers(): List<User>
    fun getUserByEmail(email: String): User?
    fun deleteUser(id: Long): Boolean
}