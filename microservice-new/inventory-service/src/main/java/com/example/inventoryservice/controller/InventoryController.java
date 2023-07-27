package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveDataToTheDb(@RequestBody Inventory inventory){
        inventoryService.saveInventory(inventory);
        return "file save successfully";
    }
    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String skuCode){
        return  inventoryService.isInStock(skuCode);
    }
}
