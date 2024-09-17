package example.usermanagement.service

import com.example.usermanagement.model.User
import com.example.usermanagement.repository.UserRepository
import com.example.usermanagement.service.KafkaProducerService
import com.example.usermanagement.service.UserServiceImpl
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var kafkaProducerService: KafkaProducerService
    private lateinit var meterRegistry: SimpleMeterRegistry
    private lateinit var userService: UserServiceImpl

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        kafkaProducerService = mockk(relaxed = true)
        meterRegistry = SimpleMeterRegistry()
        userService = UserServiceImpl(userRepository, kafkaProducerService, meterRegistry)
    }

    @Test
    fun `createUser should save user, send notification, and increment metric`() {
        // Given
        val user = User(name = "John Doe", email = "john@example.com")
        every { userRepository.save(any()) } returns user.copy(id = 1)

        // When
        val result = userService.createUser(user)

        // Then
        assertEquals(1, result.id)
        assertEquals("John Doe", result.name)
        assertEquals("john@example.com", result.email)

        verify(exactly = 1) { userRepository.save(any()) }
        verify(exactly = 1) {
            kafkaProducerService.sendMessage(
                "user-creation-topic",
                "New user created: John Doe (john@example.com)"
            )
        }

        assertEquals(1.0, meterRegistry.counter("user.created").count(), 0.01)
    }
}