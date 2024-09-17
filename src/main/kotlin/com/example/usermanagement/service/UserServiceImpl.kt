package com.example.usermanagement.service

import com.example.usermanagement.model.User
import com.example.usermanagement.repository.UserRepository
import org.springframework.stereotype.Service
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Counter
import org.springframework.dao.DataIntegrityViolationException

// import org.springframework.kafka.core.KafkaTemplate

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val queueService: InMemoryQueueService,
    private val meterRegistry: MeterRegistry
    // private val kafkaTemplate: KafkaTemplate<String, String>
) : UserService {

    private val userCreationCounter: Counter = Counter.builder("user.created")
        .description("The number of users created")
        .register(meterRegistry)

    private val userDeletionCounter: Counter = Counter.builder("user.deleted")
        .description("The number of users deleted")
        .register(meterRegistry)

    override fun createUser(user: User): User {
        if (userRepository.findByEmail(user.email) != null) {
            throw UserAlreadyExistsException("A user with email ${user.email} already exists")
        }

        try {
            val savedUser = userRepository.save(user)
            sendNotification(savedUser)
            userCreationCounter.increment()
            return savedUser
        } catch (e: DataIntegrityViolationException) {
            throw UserAlreadyExistsException("A user with email ${user.email} already exists")
        }
    }

    override fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)

    override fun getAllUsers(): List<User> = userRepository.findAll()

    override fun getUserByEmail(email: String): User? = userRepository.findByEmail(email)

    override fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            sendDeletionNotification(id)
            userDeletionCounter.increment()
            true
        } else {
            false
        }
    }

    private fun sendNotification(user: User) {
        val message = "New user created: ${user.name} (${user.email})"
        queueService.sendMessage(message)
        // kafkaTemplate.send("user-creation-topic", message)
    }

    private fun sendDeletionNotification(userId: Long) {
        val message = "User deleted: ID $userId"
        queueService.sendMessage(message)
     // kafkaTemplate.send("user-deletion-topic", message)
    }
}

class UserAlreadyExistsException(message: String) : RuntimeException(message)
