package com.example.usermanagement.service

import com.example.usermanagement.model.User
import com.example.usermanagement.repository.UserRepository
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserServiceTest {

    private lateinit var userRepository: UserRepository
    private lateinit var inMemoryQueueService: InMemoryQueueService
    private lateinit var meterRegistry: SimpleMeterRegistry
    private lateinit var userService: UserServiceImpl

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        inMemoryQueueService = mockk(relaxed = true)
        meterRegistry = SimpleMeterRegistry()
        userService = UserServiceImpl(userRepository, inMemoryQueueService, meterRegistry)
    }

    @Test
    fun `createUser should save user, send notification, and increment metric`() {
        // Existing test implementation
        // ...
    }

    @Test
    fun `createUser should throw UserAlreadyExistsException when email already exists`() {
        // Given
        val existingUser = User(id = 1, name = "Existing User", email = "existing@example.com")
        every { userRepository.findByEmail(existingUser.email) } returns existingUser

        // When / Then
        assertThrows<UserAlreadyExistsException> {
            userService.createUser(existingUser)
        }

        verify(exactly = 1) { userRepository.findByEmail(existingUser.email) }
        verify(exactly = 0) { userRepository.save(any()) }
        verify(exactly = 0) { inMemoryQueueService.sendMessage(any()) }

        assertEquals(0.0, meterRegistry.counter("user.created").count(), 0.01)
    }

    @Test
    fun `getUserById should return user when found`() {
        // Given
        val userId = 1L
        val user = User(id = userId, name = "John Doe", email = "john@example.com")
        every { userRepository.findById(userId) } returns java.util.Optional.of(user)

        // When
        val result = userService.getUserById(userId)

        // Then
        assertNotNull(result)
        assertEquals(user, result)
        verify(exactly = 1) { userRepository.findById(userId) }
    }

    @Test
    fun `getUserById should return null when user not found`() {
        // Given
        val userId = 1L
        every { userRepository.findById(userId) } returns java.util.Optional.empty()

        // When
        val result = userService.getUserById(userId)

        // Then
        assertNull(result)
        verify(exactly = 1) { userRepository.findById(userId) }
    }

    @Test
    fun `getAllUsers should return list of all users`() {
        // Given
        val users = listOf(
            User(id = 1, name = "John Doe", email = "john@example.com"),
            User(id = 2, name = "Jane Doe", email = "jane@example.com")
        )
        every { userRepository.findAll() } returns users

        // When
        val result = userService.getAllUsers()

        // Then
        assertEquals(users, result)
        verify(exactly = 1) { userRepository.findAll() }
    }

    @Test
    fun `getUserByEmail should return user when found`() {
        // Given
        val email = "john@example.com"
        val user = User(id = 1, name = "John Doe", email = email)
        every { userRepository.findByEmail(email) } returns user

        // When
        val result = userService.getUserByEmail(email)

        // Then
        assertNotNull(result)
        assertEquals(user, result)
        verify(exactly = 1) { userRepository.findByEmail(email) }
    }

    @Test
    fun `getUserByEmail should return null when user not found`() {
        // Given
        val email = "nonexistent@example.com"
        every { userRepository.findByEmail(email) } returns null

        // When
        val result = userService.getUserByEmail(email)

        // Then
        assertNull(result)
        verify(exactly = 1) { userRepository.findByEmail(email) }
    }

    @Test
    fun `deleteUser should return true and increment metric when user exists`() {
        // Given
        val userId = 1L
        every { userRepository.existsById(userId) } returns true
        every { userRepository.deleteById(userId) } just Runs

        // When
        val result = userService.deleteUser(userId)

        // Then
        assertTrue(result)
        verify(exactly = 1) { userRepository.existsById(userId) }
        verify(exactly = 1) { userRepository.deleteById(userId) }
        verify(exactly = 1) { inMemoryQueueService.sendMessage("User deleted: ID $userId") }
        assertEquals(1.0, meterRegistry.counter("user.deleted").count(), 0.01)
    }

    @Test
    fun `deleteUser should return false and not increment metric when user doesn't exist`() {
        // Given
        val userId = 1L
        every { userRepository.existsById(userId) } returns false

        // When
        val result = userService.deleteUser(userId)

        // Then
        assertFalse(result)
        verify(exactly = 1) { userRepository.existsById(userId) }
        verify(exactly = 0) { userRepository.deleteById(any()) }
        verify(exactly = 0) { inMemoryQueueService.sendMessage(any()) }
        assertEquals(0.0, meterRegistry.counter("user.deleted").count(), 0.01)
    }
}