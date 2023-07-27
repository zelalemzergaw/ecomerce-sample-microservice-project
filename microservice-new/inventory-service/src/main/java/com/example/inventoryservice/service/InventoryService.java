package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
@Transactional(readOnly = true)
public boolean isInStock(String skuCode){
    return inventoryRepository.findBySkuCode(skuCode).isPresent();
}

public void saveInventory(Inventory inventory){
    inventoryRepository.save(inventory);
}
}
