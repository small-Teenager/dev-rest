package com.dev.rest.controller.apply;

import com.dev.rest.entity.doc.Logistics;
import com.dev.rest.entity.doc.Order;
import com.dev.rest.response.ApiResponse;
import com.dev.rest.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/apply/mongodb")
public class MongoDBController {

    @Autowired
    private MongoDBService mongoDBService;


    @PostMapping
    public ApiResponse<Order> addOrder(@RequestBody Order order) {
        return ApiResponse.success(mongoDBService.addOrder(order));
    }

    @PostMapping("/update-logistics-by-order-id")
    public ApiResponse<Boolean> updateLogisticsByOrderId(@RequestBody Logistics logistics) {
        return ApiResponse.success(mongoDBService.updateLogisticsByOrderId(logistics));
    }

    @GetMapping("/{orderId}")
    public ApiResponse<Order> getOrderById(@PathVariable(value = "orderId") @NotNull Long orderId) {
        return ApiResponse.success(mongoDBService.getOrderById(orderId));
    }

    @GetMapping("/list/{limit}")
    public ApiResponse<List<Order>> find(@PathVariable(value = "limit") int limit) {
        return ApiResponse.success(mongoDBService.find(limit));
    }

}
