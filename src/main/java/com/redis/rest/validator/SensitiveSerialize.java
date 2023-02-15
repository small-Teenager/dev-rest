package com.redis.rest.validator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.redis.rest.annotation.SensitiveWrapped;
import com.redis.rest.enums.SensitiveEnum;
import com.redis.rest.utils.SensitiveInfoUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * 数据脱敏
 */
public class SensitiveSerialize extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏类型
     */
    private SensitiveEnum type;


    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        switch (this.type) {
            case CHINESE_NAME: {
                jsonGenerator.writeString(SensitiveInfoUtil.chineseName(s));
                break;
            }
            case ID_CARD: {
                jsonGenerator.writeString(SensitiveInfoUtil.idCardNum(s));
                break;
            }
            case FIXED_PHONE: {
                jsonGenerator.writeString(SensitiveInfoUtil.fixedPhone(s));
                break;
            }
            case MOBILE_PHONE: {
                jsonGenerator.writeString(SensitiveInfoUtil.mobilePhone(s));
                break;
            }
            case ADDRESS: {
                jsonGenerator.writeString(SensitiveInfoUtil.address(s, 4));
                break;
            }
            case EMAIL: {
                jsonGenerator.writeString(SensitiveInfoUtil.email(s));
                break;
            }
            case BANK_CARD: {
                jsonGenerator.writeString(SensitiveInfoUtil.bankCard(s));
                break;
            }
            case CNAPS_CODE: {
                jsonGenerator.writeString(SensitiveInfoUtil.cnapsCode(s));
                break;
            }
            default:
                break;
        }
    }

    /**
     * createContextual的作用是通过字段已知的上下文信息定制JsonSerializer对象
     * @param serializerProvider
     * @param beanProperty
     * @return
     * @throws JsonMappingException
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        // 为空直接跳过
        if (beanProperty != null) {
            // 非 String 类直接跳过
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                SensitiveWrapped sensitiveWrapped = beanProperty.getAnnotation(SensitiveWrapped.class);
                if (sensitiveWrapped == null) {
                    sensitiveWrapped = beanProperty.getContextAnnotation(SensitiveWrapped.class);
                }
                if (sensitiveWrapped != null) {
                    // 如果能得到注解，就将注解的 value 传入 SensitiveSerialize
                    return new SensitiveSerialize(sensitiveWrapped.value());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(beanProperty);
    }

    public SensitiveSerialize() {
    }

    public SensitiveSerialize(final SensitiveEnum type) {
        this.type = type;
    }
}

