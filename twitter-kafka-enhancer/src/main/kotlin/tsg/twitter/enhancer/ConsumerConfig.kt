package tsg.twitter.enhancer

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean

@EnableKafka
@Configuration
class ConsumerConfig {

    fun consumerFactory(groupId: String): ConsumerFactory<String, String> {
        val props = mapOf(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to StringSerializer::class.java,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
        )
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {

        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory("spring")
        return factory
    }
}