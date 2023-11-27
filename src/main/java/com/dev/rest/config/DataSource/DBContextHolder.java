package com.dev.rest.config.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class DBContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DBContextHolder.class);

    /**
     * 线程局部变量，不同线程的threadlocal相互独立；是一种保存线程私有信息的机制，因为在整个生命周期都有效，
     * 所以可以方便地在一个线程关联的不同业务模块之间传递信息，比如事务ID、Cookie等上下文相关信息。
     */
    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
    public static void master() {
        set(DBTypeEnum.MASTER);
        logger.info("当前数据源：{} 切换主数据源-写入", get());
    }

    public static void slave() {
        set(DBTypeEnum.SLAVE1);
        logger.info("当前数据源：{} 切换从数据源(SLAVE1)-读出", get());
        //  轮询
//        int index = counter.getAndIncrement() % 2;
//        if (counter.get() > 9999) {
//            counter.set(-1);
//        }
//        if (index == 0) {
//            set(DBTypeEnum.SLAVE1);
//            logger.info("当前数据源：{} 切换从数据源(SLAVE1)-读出", get());
//        }else if (index == 1){
//            set(DBTypeEnum.SLAVE2);
//            logger.info("当前数据源：{} 切换从数据源(SLAVE2)-读出", get());
//        }
    }
}
