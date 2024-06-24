package org.example.config

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.shaded.com.google.protobuf.StringValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.JacksonUtils
import org.springframework.kafka.support.serializer.JsonSerializer


class KafkaProducerConfig {

    @Value("\${application.kafka.topic}")
    lateinit var topicName: String

    @Bean
    fun objectMapper(): ObjectMapper {
        return JacksonUtils.enhancedObjectMapper()
    }

    @Bean
    fun producerFactory(
        kafkaProperties: KafkaProperties, mapper: ObjectMapper?,
    ): ProducerFactory<String, StringValue> {

        val props = kafkaProperties.buildAdminProperties(null)
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

        val kafkaProducerFactory = DefaultKafkaProducerFactory<String, StringValue>(props)
        kafkaProducerFactory.valueSerializer = JsonSerializer(mapper)
        return kafkaProducerFactory
    }

    @Bean
    fun kafkaTemplate(
        producerFactory: ProducerFactory<String, StringValue>,
    ): KafkaTemplate<String, StringValue> {
        return KafkaTemplate(producerFactory)
    }

    @Bean
    fun topic(): NewTopic {
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build()
    }

//    @Bean
//    fun dataSender(topic: NewTopic, kafkaTemplate: KafkaTemplate<String?, StringValue?>?): DataSender {
//        return DataSenderKafka(
//            topic.name(),
//            kafkaTemplate
//        ) { stringValue -> log.info("asked, value:{}", stringValue) }
//    }
//
//    @Bean
//    fun stringValueSource(dataSender: DataSender?): StringValueSource {
//        return StringValueSource(dataSender)
//    }

    private companion object : KLogging() {

    }

}
