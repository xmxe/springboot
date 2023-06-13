package com.xmxe.spring;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.producer.retries}")
	private Integer retries;

	@Value("${spring.kafka.producer.batch-size}")
	private Integer batchSize;

	@Value("${spring.kafka.producer.buffer-memory}")
	private Integer bufferMemory;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;

	@Value("${spring.kafka.consumer.max-poll-records}")
	private Integer maxPollRecords;

	@Value("${spring.kafka.consumer.batch.concurrency}")
	private Integer batchConcurrency;

	@Value("${spring.kafka.consumer.enable-auto-commit}")
	private Boolean autoCommit;

	@Value("${spring.kafka.consumer.auto-commit-interval}")
	private Integer autoCommitInterval;


	/**
	 *  生产者配置信息
	 *  在application.properties配置的话可以不用配置，所以把@Bean注释掉了
	 */
	// @Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();

		// #procedure要求leader在考虑完成请求之前收到的确认数，用于控制发送记录在服务端的持久化，其值可以为如下：
		// #acks = 0 如果设置为零，则生产者将不会等待来自服务器的任何确认，该记录将立即添加到套接字缓冲区并视为已发送。在这种情况下，无法保证服务器已收到记录，并且重试配置将不会生效（因为客户端通常不会知道任何故障），为每条记录返回的偏移量始终设置为-1。
		// #acks = 1 这意味着leader会将记录写入其本地日志，但无需等待所有副本服务器的完全确认即可做出回应，在这种情况下，如果leader在确认记录后立即失败，但在将数据复制到所有的副本服务器之前，则记录将会丢失。
		// #acks = all 这意味着leader将等待完整的同步副本集以确认记录，这保证了只要至少一个同步副本服务器仍然存活，记录就不会丢失，这是最强有力的保证，这相当于acks = -1的设置。
		// #可以设置的值为：all, -1, 0, 1
		// props.put(ProducerConfig.ACKS_CONFIG, "0");
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.RETRIES_CONFIG, retries);

		// 为了减少网络请求次数，采取批量发送消息的策略。同时批量发送时消息里面可能有发送到不同分区的消息，而分区也可能落在不同的broker，
		// 所以发送时时按不同分区来分发的
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		// 表示延时时间，在延时时间内进行一次消息批量发送，可以配合batch_size使用
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}

	/**
	 *  生产者工厂
	 */
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	/**
	 *  生产者模板
	 */
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}


	/**
	 *  消费者配置信息
	 *  在application.properties配置的话可以不用配置，所以把@Bean注释掉了
	 */
	// @Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
		props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

	/**
	 *  消费者批量工厂
	 */
	@Bean
	public KafkaListenerContainerFactory<?> batchFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerConfigs()));
		//设置并发量，小于或等于Topic的分区数
		factory.setConcurrency(batchConcurrency);
		factory.getContainerProperties().setPollTimeout(1500);

		// MANUAL 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后, 手动调用Acknowledgment.acknowledge()后提交
		// MANUAL_IMMEDIATE 手动调用Acknowledgment.acknowledge()后立即提交
		// 区别：MANUAL: 在处理完最后一次轮询的所有结果后，将队列排队，并在一次操作中提交偏移量。可以认为是在批处理结束时提交偏移量
		// MANUAL_IMMEDIATE：只要在侦听器线程上执行确认，就立即提交偏移。会在批量执行的时候逐一提交它们。

		// RECORD 当每一条记录被消费者监听器（ListenerConsumer）处理之后提交
		// BATCH 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后提交
		// TIME	当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，距离上次提交时间大于TIME时提交
		// COUNT 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，被处理record数量大于等于COUNT时提交
		// COUNT_TIME TIME或COUNT有一个条件满足时提交
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
		//设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
		factory.setBatchListener(true);
		return factory;
	}
}