package org.example.config


import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.JacksonUtils

@Configuration
open class ApplicationConfig(@param:Value("\${application.kafka.topic}") val topicName: String) {

    @Bean
    open fun objectMapper(): ObjectMapper {
        return JacksonUtils.enhancedObjectMapper()
    }

//    @Bean
//    fun producerFactory(
//        kafkaProperties: KafkaProperties, mapper: ObjectMapper?,
//    ): ProducerFactory<String, StringValue> {
//        val props: Unit = kafkaProperties.buildProducerProperties()
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer::class.java)
//
//        val kafkaProducerFactory: DefaultKafkaProducerFactory<String, StringValue> =
//            DefaultKafkaProducerFactory<String, StringValue>(props)
//        kafkaProducerFactory.setValueSerializer(JsonSerializer(mapper))
//        return kafkaProducerFactory
//    }
//
//    @Bean
//    fun kafkaTemplate(
//        producerFactory: ProducerFactory<String?, StringValue?>?,
//    ): KafkaTemplate<String, StringValue> {
//        return KafkaTemplate(producerFactory)
//    }
//
//    @Bean
//    fun topic(): NewTopic {
//        return TopicBuilder.name(topicName).partitions(1).replicas(1).build()
//    }
//
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
//
//    companion object {
//        private val log: Logger = LoggerFactory.getLogger(ApplicationConfig::class.java)
//    }
}
