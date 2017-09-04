package com.walid.checkout;

import com.walid.inventory.InventoryItem;

import java.util.ArrayList;
import java.util.List;

public class Checkout {

    private final List<CheckoutItem> checkoutItems = new ArrayList<>();

    public void scan(String sku) {
        // add scanned item to list
        checkoutItems.add(new CheckoutItem(InventoryItem.getItem(sku)));
    }

    public double total() {
        return checkoutItems.parallelStream()
                .mapToDouble(CheckoutItem::getSalePrice)
                .sum();
    }
}
