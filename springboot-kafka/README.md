## 配置文件

### consumer

```properties
# 消费者所属消费组的唯一标识
group.id
# 一次拉取请求的最大消息数，默认500条
max.poll.records
# 指定拉取消息线程最长空闲时间，默认300000ms
max.poll.interval.ms
# 检测消费者是否失效的超时时间，默认10000ms
session.timeout.ms
# 消费者心跳时间，默认3000ms
heartbeat.interval.ms
# 连接集群broker地址
bootstrap.servers
# 是否开启自动提交消费位移的功能，默认true
enable.auto.commit
# 自动提交消费位移的时间间隔，默认5000ms
auto.commit.interval.ms
# 消费者的分区配置策略,默认RangeAssignor
partition.assignment.strategy
# 如果分区没有初始偏移量，或者当前偏移量服务器上不存在时，将使用的偏移量设置，earliest从头开始消费，latest从最近的开始消费，none抛出异常，如果存在已经提交的offest时,不管设置为earliest或者latest都会从已经提交的offest处开始消费,如果不存在已经提交的offest时,earliest表示从头开始消费,latest表示从最新的数据消费,也就是新产生的数据.none topic各分区都存在已提交的offset时，从提交的offest处开始消费；只要有一个分区不存在已提交的offset，则抛出异常.kafka-0.10.1.X版本之前: auto.offset.reset的值为smallest,和,largest.(offest保存在zk中).kafka-0.10.1.X版本之后: auto.offset.reset的值更改为:earliest,latest,和none(offest保存在kafka的一个特殊的topic,名为:__consumer_offsets里面)
auto.offset.reset
# 消费者客户端一次请求从Kafka拉取消息的最小数据量，如果Kafka返回的数据量小于该值，会一直等待，直到满足这个配置大小，默认1b
fetch.min.bytes
# 消费者客户端一次请求从Kafka拉取消息的最大数据量，默认50MB
fetch.max.bytes
# 从Kafka拉取消息时，在不满足fetch.min.bytes条件时，等待的最大时间，默认500ms
fetch.max.wait.ms
# 强制刷新元数据时间，毫秒，默认300000，5分钟
metadata.max.age.ms
# 设置从每个分区里返回给消费者的最大数据量，区别于fetch.max.bytes，默认1MB
max.partition.fetch.bytes
# Socket发送缓冲区大小，默认128kb,-1将使用操作系统的设置
send.buffer.bytes
# Socket发送缓冲区大小，默认64kb,-1将使用操作系统的设置
receive.buffer.bytes
# 消费者客户端的id
client.id
# 连接失败后，尝试连接Kafka的时间间隔，默认50ms
reconnect.backoff.ms
# 尝试连接到Kafka，生产者客户端等待的最大时间，默认1000ms
reconnect.backoff.max.ms
# 消息发送失败重试时间间隔，默认100ms
retry.backoff.ms
# 样本计算时间窗口，默认30000ms
metrics.sample.window.ms
# 用于维护metrics的样本数量，默认2
metrics.num.samples
# metrics日志记录级别，默认info
metrics.log.level
# 类的列表，用于衡量指标，默认空list
metric.reporters
# 自动检查CRC32记录的消耗
check.crcs
# key反序列化方式
key.deserializer
# value反序列化方式
value.deserializer
# 设置多久之后关闭空闲连接，默认540000ms
connections.max.idle.ms
# 客户端将等待请求的响应的最大时间,如果在这个时间内没有收到响应，客户端将重发请求，超过重试次数将抛异常，默认30000ms
request.timeout.ms
# 设置消费者api超时时间，默认60000ms
default.api.timeout.ms
# 自定义拦截器
interceptor.classes
# 内部的主题:consumer_offsets和一transaction_state。该参数用来指定Kafka中的内部主题是否可以向消费者公开，默认值为true。如果设置为true，那么只能使用subscribe(Collection)的方式而不能使用subscribe(Pattern)的方式来订阅内部主题，设置为false则没有这个限制。
exclude.internal.topics
# 用来配置消费者的事务隔离级别。如果设置为“read committed”，那么消费者就会忽略事务未提交的消息，即只能消费到LSO(LastStableOffset)的位置，默认情况下为“read_uncommitted”，即可以消费到HW(High Watermark)处的位置
isolation.level
key.deserializer = org.apache.kafka.common.serialization.StringDeserializer
value.deserializer = org.apache.kafka.common.serialization.StringDeserializer
```

### producer

```properties
# Snappy压缩技术是Google开发的，它可以在提供较好的压缩比的同时，减少对CPU的使用率并保证好的性能，所以建议在同时考虑性能和带宽的情况下使用。Gzip压缩技术通常会使用更多的CPU和时间，但会产生更好的压缩比，所以建议在网络带宽更受限制的情况下使用，默认不压缩，该参数可以设置成snappy、gzip或lz4对发送给broker的消息进行压缩
compression.type=Gzip
# 请求的最大字节数。这也是对最大消息大小的有效限制。注意：server具有自己对消息大小的限制，这些大小和这个设置不同。此项设置将会限制producer每次批量发送请求的数目，以防发出巨量的请求。
max.request.size=1048576
# TCP的接收缓存SO_RCVBUF空间大小，用于读取数据
receive.buffer.bytes=32768
# client等待请求响应的最大时间,如果在这个时间内没有收到响应，客户端将重发请求，超过重试次数发送失败
request.timeout.ms=30000
# TCP的发送缓存SO_SNDBUF空间大小，用于发送数据
send.buffer.bytes=131072
# 指定server等待来自followers的确认的最大时间，根据acks的设置，超时则返回error
timeout.ms=30000
# 在block前一个connection上允许最大未确认的requests数量。当设为1时，即是消息保证有序模式，注意：这里的消息保证有序是指对于单个Partition的消息有顺序，因此若要保证全局消息有序，可以只使用一个Partition，当然也会降低性能
max.in.flight.requests.per.connection=5
# 在第一次将数据发送到某topic时，需先fetch该topic的metadata，得知哪些服务器持有该topic的partition，该值为最长获取metadata时间
metadata.fetch.timeout.ms=60000
# 连接失败时，当我们重新连接时的等待时间
reconnect.backoff.ms=50
# 在重试发送失败的request前的等待时间，防止若目的Broker完全挂掉的情况下Producer一直陷入死循环发送，折中的方法
retry.backoff.ms=100
# metrics系统维护可配置的样本数量，在一个可修正的window size
metrics.sample.window.ms=30000
# 用于维护metrics的样本数
metrics.num.samples=2
# 类的列表，用于衡量指标。实现MetricReporter接口
metric.reporters=[]
# 强制刷新metadata的周期，即使leader没有变化
metadata.max.age.ms=300000
# 与broker会话协议，取值：LAINTEXT,SSL,SASL_PLAINTEXT,SASL_SSL
security.protocol=PLAINTEXT
# 分区类，实现Partitioner接口
partitioner.class=class org.apache.kafka.clients.producer.internals.DefaultPartitioner
# 控制block的时长，当buffer空间不够或者metadata丢失时产生block
max.block.ms=60000
# 关闭达到该时间的空闲连接
connections.max.idle.ms=540000
# 当向server发出请求时，这个字符串会发送给server，目的是能够追踪请求源
client.id=""
# acks=0配置适用于实现非常高的吞吐量,acks=all这是最安全的模式。Server完成producer request前需要确认的数量。acks=0时，producer不会等待确认，直接添加到socket等待发送；acks=1时，等待leader写到local log就行；acks=all或acks=-1时，等待isr中所有副本确认（注意：确认都是broker接收到消息放入内存就直接返回确认，不是需要等待数据写入磁盘后才返回确认，这也是kafka快的原因）
acks = all
# 发生错误时，重传次数。当开启重传时，需要将`max.in.flight.requests.per.connection`设置为1，否则可能导致失序
retries = 0
# 发送到同一个partition的消息会被先存储在batch中，该参数指定一个batch可以使用的内存大小，单位是byte。不一定需要等到batch被填满才能发送Producer可以将发往同一个Partition的数据做成一个Produce Request发送请求，即Batch批处理，以减少请求次数，该值即为每次批处理的大小。另外每个Request请求包含多个Batch，每个Batch对应一个Partition，且一个Request发送的目的Broker均为这些partition的leader副本。若将该值设为0，则不会进行批处理
batch.size = 16384
# 生产者在发送消息前等待linger.ms，从而等待更多的消息加入到batch中。如果batch被填满或者linger.ms达到上限，就把batch中的消息发送出去,Producer默认会把两次发送时间间隔内收集到的所有Requests进行一次聚合然后再发送，以此提高吞吐量，而linger.ms则更进一步，这个参数为每次发送增加一些delay，以此来聚合更多的Message。官网解释翻译：producer会将request传输之间到达的所有records聚合到一个批请求。通常这个值发生在欠负载情况下，record到达速度快于发送。但是在某些场景下，client即使在正常负载下也期望减少请求数量。这个设置就是如此，通过人工添加少量时延，而不是立马发送一个record,producer会等待所给的时延，以让其他records发送出去，这样就会被聚合在一起。这个类似于TCP的Nagle算法。该设置给了batch的时延上限：当我们获得一个partition的batch.size大小的records，就会立即发送出去，而不管该设置；但是如果对于这个partition没有累积到足够的record，会linger指定的时间等待更多的records出现。该设置的默认值为0(无时延)。例如，设置linger.ms=5，会减少request发送的数量，但是在无负载下会增加5ms的发送时延。
linger.ms = 1
# Producer可以用来缓存数据的内存大小。该值实际为RecordAccumulator类中的BufferPool，即Producer所管理的最大内存。如果数据产生速度大于向broker发送的速度，producer会阻塞max.block.ms，超时则抛出异常
buffer.memory = 33554432
key.serializer = org.apache.kafka.common.serialization.StringSerializer
value.serializer = org.apache.kafka.common.serialization.StringSerializer
```

## 相关文章

- [Spring Kafka之@KafkaListener单条或批量处理消息](https://mp.weixin.qq.com/s/MuXC-nYceLOsBo76z0KvyA)
- [SpringBoot整合Kafka实现千万级数据异步处理，实战介绍！](https://mp.weixin.qq.com/s/3mwx_DCK8ExwANepk8ysng)