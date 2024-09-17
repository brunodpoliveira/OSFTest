package com.example.usermanagement.model

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Name is required")
    @Column(nullable = false)
    val name: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)