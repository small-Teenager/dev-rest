package com.dev.rest.service.impl;

import com.alibaba.fastjson2.JSON;
import com.dev.rest.entity.doc.Logistics;
import com.dev.rest.entity.doc.Order;
import com.dev.rest.service.MongoDBService;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoDBServiceImpl implements MongoDBService {

    private static final Logger log = LoggerFactory.getLogger(MongoDBService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Order addOrder(Order order) {
        return mongoTemplate.insert(order);
    }

    @Override
    public Boolean updateLogisticsByOrderId(Logistics logistics) {
        Query query = new Query(Criteria.where("orderId").is(logistics.getOrderId()));
        Update update = new Update();
        update.set("status", logistics.getStatus());//更新状态
        update.push("logistics", logistics);
        UpdateResult updateResult = mongoTemplate.upsert(query, update, Order.class);
        log.info("mongodb update logistics info,param:{},result:{}", JSON.toJSONString(logistics), updateResult);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public Order getOrderById(Long orderId) {
        Query query = new Query(Criteria.where("orderId").is(orderId));
        return mongoTemplate.findOne(query, Order.class);
    }

    @Override
    public List<Order> find(int page, int limit) {
        Query query = new Query();
        query.skip((page - 1) * limit).limit(limit);
        return mongoTemplate.find(query, Order.class);
    }
}
