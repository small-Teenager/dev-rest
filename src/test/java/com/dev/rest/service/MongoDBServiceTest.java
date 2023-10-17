package com.dev.rest.service;

import com.alibaba.fastjson.JSON;
import com.dev.rest.DevRestApplicationTests;
import com.dev.rest.common.utils.IdWorker;
import com.dev.rest.entity.doc.Logistics;
import com.dev.rest.entity.doc.Order;
import com.dev.rest.enums.LogisticsStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Date;
import java.util.List;

public class MongoDBServiceTest extends DevRestApplicationTests {

    @Autowired
    private MongoDBService mongoDBService;

    @Autowired
    private IdWorker idWorker;

    @Test
    public void addOrder() {
        for (int i = 0; i < 1; i++) {
            Order order = new Order();
            Long orderId = idWorker.nextId();
            order.setOrderId(orderId);
            order.setStatus(LogisticsStatusEnum.PROCESSING.getCode());
            order.setOrderTime(new Date());
            order.setShipper("张飞" + i);
            order.setShippingAdress("河北省涿州市涿县" + i);
            order.setShipperPhone("13579246810");
            order.setShipTime(new Date());
            order.setRecevier("刘备" + i);
            order.setRecevierAddress("河北省涿州市大树楼桑村" + i);
            order.setRecevierPhone("13479246810");
            Order order1 =  mongoDBService.addOrder(order);
            System.err.println(JSON.toJSONString(order1));

//            Logistics logistics = new Logistics();
//            logistics.setOrderId(orderId);
//            logistics.setOperation("仓库已发货");
//            logistics.setOperator("关羽" + i);
//            logistics.setOperationTime(new Date());
//            logistics.setAddress("河北省涿州市仓库" + i);
//            logistics.setDetails("哈哈哈哈哈" + i);
//            mongoDBService.updateLogisticsByOrderId(logistics);
        }
    }

    @Test
    public void getOrderById() {
        Long orderId = 1711375766148616192L;
        Order order = mongoDBService.getOrderById(orderId);
        System.err.println(JSON.toJSONString(order));
    }

    @Test
    public void find() {
        List<Order> orderList = mongoDBService.find(1,10);
        System.err.println(JSON.toJSONString(orderList));
    }

    @Test
    public void updateLogisticsByOrderId() {
        Logistics logistics = new Logistics();
        Long orderId = 4187524937734024L;
        logistics.setOrderId(orderId);
        logistics.setOperation("仓库已发货");
        logistics.setOperator("关羽");
        logistics.setOperationTime(new Date());
        logistics.setAddress("河北省涿州市仓库");
        logistics.setDetails("哈哈哈哈哈");
        // 116.401394,39.889695
        //经度
        double lng = 116.401394D ;
        //维度
        double lat = 39.889695D ;

        logistics.setLocation(new GeoJsonPoint(lng, lat));
        mongoDBService.updateLogisticsByOrderId(logistics);
    }
}
