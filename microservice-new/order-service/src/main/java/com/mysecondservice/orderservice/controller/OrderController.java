package com.mysecondservice.orderservice.controller;

import com.mysecondservice.orderservice.dto.OrderRequest;
import com.mysecondservice.orderservice.model.Order;
import com.mysecondservice.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "order saved Successfully";
    }
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        return orderService.getAllProducts();
    }
}
