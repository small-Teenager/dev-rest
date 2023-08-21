package com.dev.rest.serialize;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.xerial.snappy.Snappy;

import java.io.Serializable;

/**
 * Snappy 序列化
 *
 * @param <T>
 */
public class SnappyRedisSerializer<T> implements RedisSerializer<T> {

    private RedisSerializer<T> innerSerializer;

    public SnappyRedisSerializer() {

    }

    public SnappyRedisSerializer(RedisSerializer<T> innerSerializer) {
        this.innerSerializer = innerSerializer;
    }

    /**
     * Create a byte array by serialising and Compressing a java graph (object)
     */
    @Override
    public byte[] serialize(T object) throws SerializationException {
        try {
            byte[] bytes = innerSerializer != null ? innerSerializer.serialize(object)
                    : SerializationUtils.serialize((Serializable) object);
            return Snappy.compress(bytes);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        try {
            if (bytes == null || bytes.length <= 0) {
                return null;
            }
            byte[] bos = Snappy.uncompress(bytes);
            return (T) (innerSerializer != null ?
                    innerSerializer.deserialize(bos) : SerializationUtils.deserialize(bos));
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }
}