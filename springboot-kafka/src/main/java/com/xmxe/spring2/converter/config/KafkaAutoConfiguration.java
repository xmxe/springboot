package com.xmxe.spring2.converter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.ProducerListener;

/**
 * 初始化KafkaTemplate
 * 主要是初始化消息转换器
 */
/*@Configuration
@ConditionalOnClass(KafkaTemplate.class)
@EnableConfigurationProperties(KafkaProperties.class)*/
public class KafkaAutoConfiguration {

    private final KafkaProperties properties;

    public KafkaAutoConfiguration(KafkaProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(KafkaTemplate.class)
    public KafkaTemplate<?, ?> kafkaTemplate(
        ProducerFactory<Object, Object> kafkaProducerFactory,
        ProducerListener<Object, Object> kafkaProducerListener) {

        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(
            kafkaProducerFactory);
        // 设置消息转换
        kafkaTemplate.setMessageConverter(new CustomRecordMessageConverter());
        kafkaTemplate.setProducerListener(kafkaProducerListener);
        // 设置默认的topic
        kafkaTemplate.setDefaultTopic(this.properties.getTemplate().getDefaultTopic());
        return kafkaTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(ConsumerFactory.class)
    public ConsumerFactory<?, ?> kafkaConsumerFactory() {
        DefaultKafkaConsumerFactory<String, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(
                this.properties.buildConsumerProperties());
        // 对数据进行序列化
        // 需要注意在对值进行解密的时候，kafka需要指定安全包名，或者使用*表示所有
        /*
         * JsonDeserializer jsonDeserializer = new JsonDeserializer();
         * jsonDeserializer.addTrustedPackages("*");
         * consumerFactory.setValueDeserializer(jsonDeserializer);
        */

        return consumerFactory;
    }

    @Bean
    @ConditionalOnMissingBean(ProducerFactory.class)
    public ProducerFactory<?, ?> kafkaProducerFactory() {
        DefaultKafkaProducerFactory<String, Object> factory = new DefaultKafkaProducerFactory<>(
            this.properties.buildProducerProperties());
        String transactionIdPrefix = this.properties.getProducer()
            .getTransactionIdPrefix();
        /*
         * 对数据进行序列化
         * factory.setKeySerializer(new StringSerializer());
         * factory.setValueSerializer(new JsonSerializer());
         */

        if (transactionIdPrefix != null) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
        }
        return factory;
    }
}