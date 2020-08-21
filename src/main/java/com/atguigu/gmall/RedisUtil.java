package com.atguigu.gmall;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RedisUtil {


    public static void main(String[] args) {
//        Jedis jedis = new Jedis("hdp2", 6379);
//        jedis.auth("123");
//
//        jedis.set("k100","v100");
//        Set<String> keyset = jedis.keys("*");
//        for (String key : keyset) {
//            System.out.println(key);
//        }
//        Long exists = jedis.exists("k2", "k100", "k99");
//        System.out.println(exists);
//        Map<String, String> userInfo = jedis.hgetAll("user_info:0101");
//        Set<Tuple> z1 = jedis.zrevrangeWithScores("z1", 0, -1);
//        jedis.close();
        Jedis jedis = getJedisFromSentinelPool();
        jedis.set("k222","v222");
        jedis.close(); //如果是池中得到jedis ，close方法会帮你还给池子

    }


    static  JedisPool jedisPool=null;

    public  static  Jedis getJedisFromPool(){
            if(jedisPool==null){
                JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                jedisPoolConfig.setMaxTotal(200);
                jedisPoolConfig.setBlockWhenExhausted(true);
                jedisPoolConfig.setMaxWaitMillis(1000);
                jedisPoolConfig.setMinIdle(30);
                jedisPoolConfig.setMaxIdle(50);
                jedisPoolConfig.setTestOnBorrow(true);

                jedisPool=new JedisPool(jedisPoolConfig,"hdp2",6379,10000,"123");
                return jedisPool.getResource();
            }else{
              return   jedisPool.getResource();

            }

    }


    static JedisSentinelPool jedisSentinelPool=null;

    public  static  Jedis getJedisFromSentinelPool(){
        if(jedisSentinelPool==null){
            Set<String> sentinelSet=new HashSet<>();
            sentinelSet.add("hdp2:26379");

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(200);
            jedisPoolConfig.setBlockWhenExhausted(true);
            jedisPoolConfig.setMaxWaitMillis(1000);
            jedisPoolConfig.setMinIdle(30);
            jedisPoolConfig.setMaxIdle(50);
            jedisPoolConfig.setTestOnBorrow(true);

            jedisSentinelPool=new JedisSentinelPool("mymaster",sentinelSet,jedisPoolConfig);
            return jedisSentinelPool.getResource();
        }else{
            return   jedisSentinelPool.getResource();

        }

    }

}
