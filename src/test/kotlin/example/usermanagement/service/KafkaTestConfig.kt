package example.usermanagement.service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.test.EmbeddedKafkaBroker

@Configuration
class KafkaTestConfig {

    @Bean
    fun embeddedKafka(): EmbeddedKafkaBroker {
        return EmbeddedKafkaBroker(1, true, "user-creation-topic")
    }

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, String>): KafkaTemplate<String, String> {
        val kafkaTemplate = KafkaTemplate(producerFactory)
        kafkaTemplate.messageConverter = StringJsonMessageConverter()
        return kafkaTemplate
    }
}