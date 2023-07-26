package com.mysecondservice.orderservice.service;

import com.mysecondservice.orderservice.dto.OrderLineItemDto;
import com.mysecondservice.orderservice.dto.OrderRequest;
import com.mysecondservice.orderservice.model.Order;
import com.mysecondservice.orderservice.model.OrderLineItems;
import com.mysecondservice.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

     public void placeOrder(OrderRequest orderRequest){
         Order order = new Order();
         order.setOrderNumber(UUID.randomUUID().toString());

         order.setOrderLineItemsLit(orderRequest.getOrderLineItemDtos()
                 .stream()
                 .map(this::maptoDto)
                 .toList());
         orderRepository.save(order);
     }

    private OrderLineItems maptoDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItems =new OrderLineItems();
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        orderLineItems.setSkuNumber(orderLineItemDto.getSkuNumber());

        return orderLineItems;
    }

    public List<Order> getAllProducts() {
        return orderRepository.findAll();

    }
}
