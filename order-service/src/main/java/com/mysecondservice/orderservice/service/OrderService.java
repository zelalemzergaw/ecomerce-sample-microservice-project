package com.mysecondservice.orderservice.service;

import com.mysecondservice.orderservice.dto.OrderLineItemDto;
import com.mysecondservice.orderservice.dto.OrderRequest;
import com.mysecondservice.orderservice.model.Order;
import com.mysecondservice.orderservice.model.OrderLineItems;
import com.mysecondservice.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

     public void placeOrder(OrderRequest orderRequest){
         Order order = new Order();
         order.setOrderNumber(UUID.randomUUID().toString());

         orderRequest.getOrderLineItemDtos()
                 .stream()
                 .map(this::maptoDto)
                 .toList();
     }

    private OrderLineItems maptoDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItems =new OrderLineItems();
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        orderLineItems.setSkuNumber(orderLineItemDto.getSkuNumber());

        return orderLineItems;
    }
}
