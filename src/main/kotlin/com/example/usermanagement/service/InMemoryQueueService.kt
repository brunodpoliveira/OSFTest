package com.example.usermanagement.service

import org.springframework.stereotype.Service

@Service
class InMemoryQueueService {
    private val messages = mutableListOf<String>()

    fun sendMessage(message: String) {
        messages.add(message)
        println("Message sent: $message")
    }

    fun getMessages(): List<String> = messages.toList()
}