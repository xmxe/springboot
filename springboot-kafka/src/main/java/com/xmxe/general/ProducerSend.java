package com.xmxe.general;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class ProducerSend {
	public static void main(String[] args) {
        /*
         * # Snappy压缩技术是Google开发的，它可以在提供较好的压缩比的同时，减少对CPU的使用率并保证好的性能，所以建议在同时考虑性能和带宽的情况下使用。
         * # Gzip压缩技术通常会使用更多的CPU和时间，但会产生更好的压缩比，所以建议在网络带宽更受限制的情况下使用
         * # 默认不压缩，该参数可以设置成snappy、gzip或lz4对发送给broker的消息进行压缩
         * compression.type=Gzip
         * # 请求的最大字节数。这也是对最大消息大小的有效限制。注意：server具有自己对消息大小的限制，这些大小和这个设置不同。此项设置将会限制producer每次批量发送请求的数目，以防发出巨量的请求。
         * max.request.size=1048576
         * # TCP的接收缓存 SO_RCVBUF空间大小，用于读取数据
         * receive.buffer.bytes=32768
         * # client等待请求响应的最大时间,如果在这个时间内没有收到响应，客户端将重发请求，超过重试次数发送失败
         * request.timeout.ms=30000
         * # TCP的发送缓存 SO_SNDBUF空间大小，用于发送数据
         * send.buffer.bytes=131072
         * # 指定server等待来自followers的确认的最大时间，根据acks的设置，超时则返回error
         * timeout.ms=30000
         * # 在block前一个connection上允许最大未确认的requests数量。
         * # 当设为1时，即是消息保证有序模式，注意：这里的消息保证有序是指对于单个Partition的消息有顺序，因此若要保证全局消息有序，可以只使用一个Partition，当然也会降低性能
         * max.in.flight.requests.per.connection=5
         * # 在第一次将数据发送到某topic时，需先fetch该topic的metadata，得知哪些服务器持有该topic的partition，该值为最长获取metadata时间
         * metadata.fetch.timeout.ms=60000
         * # 连接失败时，当我们重新连接时的等待时间
         * reconnect.backoff.ms=50
         * # 在重试发送失败的request前的等待时间，防止若目的Broker完全挂掉的情况下Producer一直陷入死循环发送，折中的方法
         * retry.backoff.ms=100
         * # metrics系统维护可配置的样本数量，在一个可修正的window size
         * metrics.sample.window.ms=30000
         * # 用于维护metrics的样本数
         * metrics.num.samples=2
         * # 类的列表，用于衡量指标。实现MetricReporter接口
         * metric.reporters=[]
         * # 强制刷新metadata的周期，即使leader没有变化
         * metadata.max.age.ms=300000
         * # 与broker会话协议，取值：LAINTEXT, SSL, SASL_PLAINTEXT, SASL_SSL
         * security.protocol=PLAINTEXT
         * # 分区类，实现Partitioner接口
         * partitioner.class=class org.apache.kafka.clients.producer.internals.DefaultPartitioner
         * # 控制block的时长，当buffer空间不够或者metadata丢失时产生block
         * max.block.ms=60000
         * # 关闭达到该时间的空闲连接
         * connections.max.idle.ms=540000
         * # 当向server发出请求时，这个字符串会发送给server，目的是能够追踪请求源
         * client.id=""
         */
		Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.236.128:9092");
        // acks=0配置适用于实现非常高的吞吐量 , acks=all这是最安全的模式。 Server完成producer request前需要确认的数量。
        // acks=0时，producer不会等待确认，直接添加到socket等待发送；acks=1时，等待leader写到local log就行；acks=all或acks=-1时，等待isr中所有副本确认
        // （注意：确认都是broker接收到消息放入内存就直接返回确认，不是需要等待数据写入磁盘后才返回确认，这也是kafka快的原因）
        properties.put("acks", "all");
        // 发生错误时，重传次数。当开启重传时，需要将`max.in.flight.requests.per.connection`设置为1，否则可能导致失序
        properties.put("retries", 0);
        // 发送到同一个partition的消息会被先存储在batch中，该参数指定一个batch可以使用的内存大小，单位是byte。不一定需要等到batch被填满才能发送Producer可以将发往同一个Partition的数据做成一个Produce Request发送请求，即Batch批处理，以减少请求次数，该值即为每次批处理的大小。
        // 另外每个Request请求包含多个Batch，每个Batch对应一个Partition，且一个Request发送的目的Broker均为这些partition的leader副本。若将该值设为0，则不会进行批处理
        properties.put("batch.size", 16384);
        // 生产者在发送消息前等待linger.ms，从而等待更多的消息加入到batch中。如果batch被填满或者linger.ms达到上限，就把batch中的消息发送出去
        // Producer默认会把两次发送时间间隔内收集到的所有Requests进行一次聚合然后再发送，以此提高吞吐量，而linger.ms则更进一步，这个参数为每次发送增加一些delay，以此来聚合更多的Message。
        // 官网解释翻译：producer会将request传输之间到达的所有records聚合到一个批请求。通常这个值发生在欠负载情况下，record到达速度快于发送。
        // 但是在某些场景下，client即使在正常负载下也期望减少请求数量。这个设置就是如此，通过人工添加少量时延，而不是立马发送一个record，
        // producer会等待所给的时延，以让其他records发送出去，这样就会被聚合在一起。这个类似于TCP的Nagle算法。该设置给了batch的时延上限：
        // 当我们获得一个partition的batch.size大小的records，就会立即发送出去，而不管该设置；但是如果对于这个partition没有累积到足够的record，
        // 会linger指定的时间等待更多的records出现。该设置的默认值为0(无时延)。例如，设置linger.ms=5，会减少request发送的数量，
        // 但是在无负载下会增加5ms的发送时延。
        properties.put("linger.ms", 1);
        // Producer可以用来缓存数据的内存大小。该值实际为RecordAccumulator类中的BufferPool，即Producer所管理的最大内存。
        // 如果数据产生速度大于向broker发送的速度，producer会阻塞max.block.ms，超时则抛出异常
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<String, String>(properties);
            for (int i = 0; i < 100; i++) {
                String msg = "Message " + i;
                // ProducerRecord 含义: 发送给Kafka Broker的key/value 值对-- Topic （名字）-- PartitionID ( 可选)-- Key[( 可选 )-- Value
                // 生产者记录（简称PR）的发送逻辑:
                // <1> 若指定Partition ID,则PR被发送至指定Partition
                // <2> 若未指定Partition ID,但指定了Key, PR会按照hasy(key)发送至对应Partition
                // <3> 若既未指定Partition ID也没指定Key，PR会按照round-robin模式发送到每个Partition
                // <4> 若同时指定了Partition ID和Key, PR只会发送到指定的Partition (Key不起作用，代码逻辑决定)
                // 提供三种构造函数形参:
                // -- ProducerRecord(topic, partition, key, value)
                // -- ProducerRecord(topic, key, value)
                // -- ProducerRecord(topic, value)
                producer.send(new ProducerRecord<String, String>("HelloWorld", msg), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if(e != null){
                            e.printStackTrace();
                        }else{
                            System.out.println(recordMetadata.toString());
                        }
                    }
                });
                System.out.println("Sent:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
	}

}