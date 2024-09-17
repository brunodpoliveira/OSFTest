package com.example.usermanagement.service

import com.example.usermanagement.model.User
import com.example.usermanagement.repository.UserRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Counter

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val meterRegistry: MeterRegistry
) : UserService {

    private val userCreationCounter: Counter = Counter.builder("user.created")
        .description("The number of users created")
        .register(meterRegistry)

    override fun createUser(user: User): User {
        val savedUser = userRepository.save(user)
        sendNotification(savedUser)
        userCreationCounter.increment()
        return savedUser
    }

    override fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)

    override fun getAllUsers(): List<User> = userRepository.findAll()

    override fun getUserByEmail(email: String): User? = userRepository.findByEmail(email)

    private fun sendNotification(user: User) {
        val message = "New user created: ${user.name} (${user.email})"
        kafkaTemplate.send("user-creation-topic", message)
    }
}