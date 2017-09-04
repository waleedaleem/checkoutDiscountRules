package com.walid.checkout;

import com.walid.inventory.InventoryItem;

public class CheckoutItem {

    private InventoryItem inventoryItem;
    private double salePrice;

    public CheckoutItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        this.salePrice = inventoryItem.getPrice();
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void adjustSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
}
