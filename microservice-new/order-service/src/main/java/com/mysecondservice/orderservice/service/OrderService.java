package com.mysecondservice.orderservice.service;

import com.mysecondservice.orderservice.dto.InventoryResponse;
import com.mysecondservice.orderservice.dto.OrderLineItemDto;
import com.mysecondservice.orderservice.dto.OrderRequest;
import com.mysecondservice.orderservice.model.Order;
import com.mysecondservice.orderservice.model.OrderLineItems;
import com.mysecondservice.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

     public void placeOrder(OrderRequest orderRequest){
         Order order = new Order();
         order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemDtos()
                 .stream()
                 .map(this::maptoDto)
                 .toList();
         order.setOrderLineItemsLit(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemsLit().stream()
                .map(OrderLineItems::getSkuNumber).toList();
// call the inventory service and place order if product is in
         InventoryResponse[] inventoryResponsesArray=webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
                         uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                 .retrieve()
                 .bodyToMono(InventoryResponse[].class)
                 .block();
    Boolean allProductInStock = Arrays.stream(inventoryResponsesArray)
            .allMatch(InventoryResponse::isInStock);
         if (allProductInStock){
             orderRepository.save(order);
         }else {
             throw new IllegalArgumentException("Product not available in the stock,please try later");
         }

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
