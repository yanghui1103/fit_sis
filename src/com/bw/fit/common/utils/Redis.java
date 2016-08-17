package com.bw.fit.common.utils;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class Redis {
    
    private static JedisCluster jc;  
    static {
         //只给集群里一个实例就可以  
          Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();  
          jedisClusterNodes.add(new HostAndPort("192.168.64.12", 6380));  
          jedisClusterNodes.add(new HostAndPort("192.168.64.12", 6381));  
          jedisClusterNodes.add(new HostAndPort("192.168.64.12", 6382));  
          jedisClusterNodes.add(new HostAndPort("192.168.64.12", 7380));  
          jedisClusterNodes.add(new HostAndPort("192.168.64.12", 7381));  
          jedisClusterNodes.add(new HostAndPort("192.168.64.12", 7382));   
          jc = new JedisCluster(jedisClusterNodes);  
      }
    
    public static void main(String[] args){
        jc.set("1","AABB");
        System.out.println(jc.get("1")); 
        
    }
}
