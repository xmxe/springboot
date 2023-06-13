package com.xmxe.general;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerCall {

    public static void main(String[] args) {
        /*
         * 1.group.id 消费者所属消费组的唯一标识
         * 2.max.poll.records 一次拉取请求的最大消息数，默认500条
         * 3.max.poll.interval.ms 指定拉取消息线程最长空闲时间，默认300000ms
         * 4.session.timeout.ms 检测消费者是否失效的超时时间，默认10000ms
         * 5.heartbeat.interval.ms 消费者心跳时间，默认3000ms
         * 6.bootstrap.servers 连接集群broker地址
         * 7.enable.auto.commit 是否开启自动提交消费位移的功能，默认true
         * 8.auto.commit.interval.ms 自动提交消费位移的时间间隔，默认5000ms
         * 9.partition.assignment.strategy 消费者的分区配置策略, 默认RangeAssignor
         * 10.auto.offset.reset
         * 如果分区没有初始偏移量，或者当前偏移量服务器上不存在时，将使用的偏移量设置，earliest从头开始消费，latest从最近的开始消费，none抛出异常
         * 如果存在已经提交的offest时,不管设置为earliest或者latest 都会从已经提交的offest处开始消费
         * 如果不存在已经提交的offest时,earliest表示从头开始消费,latest表示从最新的数据消费,也就是新产生的数据.
         * none topic各分区都存在已提交的offset时，从提交的offest处开始消费；只要有一个分区不存在已提交的offset，则抛出异常
         * kafka-0.10.1.X版本之前: auto.offset.reset的值为smallest,和,largest.(offest保存在zk中)
         * kafka-0.10.1.X版本之后: auto.offset.reset的值更改为:earliest,latest,和none(offest保存在kafka的一个特殊的topic名为:__consumer_offsets里面)
         * 11.fetch.min.bytes 消费者客户端一次请求从Kafka拉取消息的最小数据量，如果Kafka返回的数据量小于该值，会一直等待，直到满足这个配置大小，默认1b
         * 12.fetch.max.bytes 消费者客户端一次请求从Kafka拉取消息的最大数据量，默认50MB
         * 13.fetch.max.wait.ms 从Kafka拉取消息时，在不满足fetch.min.bytes条件时，等待的最大时间，默认500ms
         * 14.metadata.max.age.ms 强制刷新元数据时间，毫秒，默认300000，5分钟
         * 15.max.partition.fetch.bytes 设置从每个分区里返回给消费者的最大数据量，区别于fetch.max.bytes，默认1MB
         * 16.send.buffer.bytes Socket发送缓冲区大小，默认128kb,-1将使用操作系统的设置
         * 17.receive.buffer.bytes Socket发送缓冲区大小，默认64kb,-1将使用操作系统的设置
         * 18.client.id 消费者客户端的id
         * 19.reconnect.backoff.ms 连接失败后，尝试连接Kafka的时间间隔，默认50ms
         * 20.reconnect.backoff.max.ms 尝试连接到Kafka，生产者客户端等待的最大时间，默认1000ms
         * 21.retry.backoff.ms 消息发送失败重试时间间隔，默认100ms
         * 22.metrics.sample.window.ms 样本计算时间窗口，默认30000ms
         * 23.metrics.num.samples 用于维护metrics的样本数量，默认2
         * 24.metrics.log.level metrics日志记录级别，默认info
         * 25.metric.reporters 类的列表，用于衡量指标，默认空list
         * 26.check.crcs 自动检查CRC32记录的消耗
         * 27.key.deserializer key反序列化方式
         * 28.value.deserializer value反序列化方式
         * 29.connections.max.idle.ms 设置多久之后关闭空闲连接，默认540000ms
         * 30.request.timeout.ms 客户端将等待请求的响应的最大时间,如果在这个时间内没有收到响应，客户端将重发请求，超过重试次数将抛异常，默认30000ms
         * 31.default.api.timeout.ms 设置消费者api超时时间，默认60000ms
         * 32.interceptor.classes 自定义拦截器
         * 33.exclude.internal.topics 内部的主题:一consumer_offsets和一transaction_state。该参数用来指定Kafka中的内部主题是否可以向消费者公开，默认值为true。如果设置为true，那么只能使用subscribe(Collection)的方式而不能使用subscribe(Pattern)的方式来订阅内部主题，设置为false则没有这个限制。
         * 34.isolation.level 用来配置消费者的事务隔离级别。如果设置为“read committed”，那么消费者就会忽略事务未提交的消息，即只能消费到LSO(LastStableOffset)的位置，默认情况下为 “read_uncommitted”，即可以消费到HW(High Watermark)处的位置
         */
    	 Properties properties = new Properties();
         properties.put("bootstrap.servers", "192.168.236.128:9092");
         properties.put("group.id", "group-1");
         properties.put("enable.auto.commit", "true");
         properties.put("auto.commit.interval.ms", "1000");
         properties.put("auto.offset.reset", "earliest");
         properties.put("session.timeout.ms", "30000");
         properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
         properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

         KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
         kafkaConsumer.subscribe(Arrays.asList("HelloWorld"));
         while (true) {
             ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
             for (ConsumerRecord<String, String> record : records) {
                 System.out.printf("offset = %d, value = %s", record.offset(), record.value());
                 System.out.println();
             }
         }
    }

}