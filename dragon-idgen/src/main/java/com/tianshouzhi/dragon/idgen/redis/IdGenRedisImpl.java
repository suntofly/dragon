package com.tianshouzhi.dragon.idgen.redis;

import com.tianshouzhi.dragon.idgen.AbstractIdGen;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by TIANSHOUZHI336 on 2017/3/9.
 */
public class IdGenRedisImpl extends AbstractIdGen {
    private static final Logger LOGGER= LoggerFactory.getLogger(IdGenRedisImpl.class);
    //表示主键的key
    private String key;
    private String host;
    private int port=6379;
    private int connectionTimeout=3000;
    private int soTimeout=3000;
    private Jedis jedis;

    @Override
    protected void doInit() {
        if(StringUtils.isAnyBlank(host,key)){
            throw new RuntimeException("host and key can't be blank!!!");
        }
        jedis=new Jedis(host,port,connectionTimeout,soTimeout);
    }

    @Override
    public Long getAutoIncrementId() throws Exception {
         return jedis.incr(key);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }
}
