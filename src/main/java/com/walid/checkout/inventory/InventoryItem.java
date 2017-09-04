package com.walid.checkout.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryItem {

    public static final List<InventoryItem> INVENTORY_ITEM_LIST = new ArrayList<>();
    private final String sku;
    private final String name;
    private final double price;

    public InventoryItem(String sku, String name, double price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    public static void initInventroyItemList() {
        List<String> itemLines = Arrays.asList("ipd,Super iPad,549.99", "mbp,MacBook Pro,1399.9", "atv,Apple TV,109.50", "vga,VGA adapter,30.00");
        itemLines.forEach(l -> {
            String[] fields = l.split(",");
            INVENTORY_ITEM_LIST.add(new InventoryItem(fields[0], fields[1], Double.parseDouble(fields[2])));
        });
    }

    public static void initInventroyItemList(List<InventoryItem> itemList) {
        INVENTORY_ITEM_LIST.addAll(itemList);
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
