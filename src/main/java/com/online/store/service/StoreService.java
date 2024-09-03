package com.online.store.service;

import com.online.store.model.Item;
import com.online.store.model.Order;
import com.online.store.model.error.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StoreService {
    private final List<Item> items = new ArrayList<>();
    private final Map<String, Order> orders = new HashMap<>();

    public StoreService() {
        for (int i = 1; i <= 100; i++) {
            items.add(
                    new Item("item" + i, "http://image.url/item" + i,
                            "Item " + i, "Description " + i, 100.00 + i, 10.0));
        }
    }

    public List<Item> getItems(int page, int size) {
        int zeroBasedPageIndex = page - 1;
        return items.stream()
                .skip(zeroBasedPageIndex * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public String placeOrder(Order order) {
        Optional<Item> foundItem = items.stream()
                .filter(item -> item.getItemId().equals(order.getItemId()))
                .findFirst();
        if (!foundItem.isPresent()) {
            throw new OrderNotFoundException("Item not found: " + order.getItemId());
        }
        String orderId = UUID.randomUUID().toString();
        orders.put(orderId, order);
        return orderId;
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }
}

