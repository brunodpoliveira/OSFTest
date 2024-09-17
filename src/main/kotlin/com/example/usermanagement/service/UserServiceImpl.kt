package com.example.usermanagement.service

import com.example.usermanagement.model.User
import com.example.usermanagement.repository.UserRepository
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val kafkaProducerService: KafkaProducerService,
    private val meterRegistry: MeterRegistry
) : UserService {

    @Transactional
    override fun createUser(user: User): User {
        val createdUser = userRepository.save(user)
        sendUserCreationNotification(createdUser)
        meterRegistry.counter("user.created").increment()
        return createdUser
    }

    override fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    private fun sendUserCreationNotification(user: User) {
        val message = "New user created: ${user.name} (${user.email})"
        kafkaProducerService.sendMessage("user-creation-topic", message)
    }
}