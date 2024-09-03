package com.online.store.controller;

import com.online.store.model.Item;
import com.online.store.model.Order;
import com.online.store.model.error.ErrorField;
import com.online.store.model.error.ItemNotFoundException;
import com.online.store.model.error.OrderNotFoundException;
import com.online.store.model.error.ValidationException;
import com.online.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Operation(summary = "Get a list of items", description = "Returns a paginated list of items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items"),
            @ApiResponse(responseCode = "400", description = "Invalid page number or size"),
            @ApiResponse(responseCode = "404", description = "Items not found")
    })
    @GetMapping("/items")
    public ResponseEntity<?> getItems(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        List<ErrorField> errors = new ArrayList<>();

        if (page < 1) {
            errors.add(new ErrorField("page", "Page number must be one or greater."));
        }
        if (size < 1) {
            errors.add(new ErrorField("size", "Size must be at least 1."));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        List<Item> items = storeService.getItems(page, size);
        if (items.isEmpty()) {
            throw new ItemNotFoundException("Items Not Found");
        }
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Checkout an order", description = "Places an order and returns the order ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order successfully placed"),
            @ApiResponse(responseCode = "400", description = "Validation errors in order data")
    })
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ErrorField> errorFields = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> new ErrorField(fieldError.getField(), fieldError.getDefaultMessage()))
                    .toList();
            throw new ValidationException(errorFields);
        }
        String placedOrderId = storeService.placeOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(placedOrderId);
    }

    @Operation(summary = "Get an order by ID", description = "Returns details of an order by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order"),
            @ApiResponse(responseCode = "400", description = "Order ID cannot be empty"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new OrderNotFoundException("Order ID cannot be empty");
        }
        Order order = storeService.getOrder(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found for " + orderId);
        }
        return ResponseEntity.ok(order);
    }

}
