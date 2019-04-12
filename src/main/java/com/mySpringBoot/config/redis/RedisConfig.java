package com.mySpringBoot.config.redis;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching // 启用缓存，这个注解很重要
@PropertySource("classpath:redis.properties") 
public class RedisConfig extends CachingConfigurerSupport {

     /**
     * 生成key的策略
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
    
    /**
     * 管理缓存
     *
     * @param redisTemplate
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
    	RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();  // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        config = config.entryTtl(Duration.ofMinutes(1))     // 设置缓存的默认过期时间1小时，也是使用Duration设置
                .disableCachingNullValues();     // 不缓存空值

        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames =  new HashSet<>();
        cacheNames.add("my-redis-cache1");
        cacheNames.add("my-redis-cache2");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("my-redis-cache1", config);
        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));

        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)     // 使用自定义的缓存配置初始化一个cacheManager
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    
        /*
         springboot 1.x配置
         RedisCacheManager cacheManager= new RedisCacheManager(redisTemplate);
		    cacheManager.setDefaultExpiration(60);
		    Map<String,Long> expiresMap=new HashMap<>();
		    expiresMap.put("Product",5L);
		    cacheManager.setExpires(expiresMap);
		    return cacheManager;
		springboot 2.x配置
          RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        	return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build(); 
         */
    }
    
    /**
     * RedisTemplate配置
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
         StringRedisTemplate template = new StringRedisTemplate(factory);
         Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
         ObjectMapper om = new ObjectMapper();
         om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
         om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
         jackson2JsonRedisSerializer.setObjectMapper(om);
         template.setValueSerializer(jackson2JsonRedisSerializer);//如果key是String 需要配置一下StringSerializer,不然key会乱码 /XX/XX
         template.afterPropertiesSet();
         return template;
    }

    /*@Bean  
    public ConfigureRedisAction configureRedisAction() {  
        return ConfigureRedisAction.NO_OP;  
    }  */
  
    @Value("${spring.redis.host}")  
    private String host;  
  
    @Value("${spring.redis.port}")  
    private int port;  
  
    @Value("${spring.redis.timeout}")  
    private int timeout;  
  
    @Value("${spring.redis.pool.max-idle}")  
    private int maxIdle;  
  
    @Value("${spring.redis.pool.max-wait}")  
    private long maxWaitMillis;  
  
    @Value("${spring.redis.password}")  
    private String password;  
  
    @Bean  
    public JedisPool redisPoolFactory() {  
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();  
        jedisPoolConfig.setMaxIdle(maxIdle);  
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);  
  
      JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);  
        //   JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);   //无密码情况  
        return jedisPool;  
    }  
}  
