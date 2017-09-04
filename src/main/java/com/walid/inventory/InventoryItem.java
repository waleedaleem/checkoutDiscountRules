package com.walid.inventory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InventoryItem {

    public static final Set<InventoryItem> INVENTORY_ITEM_LIST = new HashSet<>();
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

    /**
     * given an sku, returns the corresponding inventory item or throws an exception
     */
    public static InventoryItem getItem(String sku) {
        return INVENTORY_ITEM_LIST.stream()
                .filter(item -> item.getSku().equals(sku))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Undefined sku scanned. Please update inventory first."));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryItem)) return false;

        InventoryItem that = (InventoryItem) o;

        if (Double.compare(that.getPrice(), getPrice()) != 0) return false;
        if (!getSku().equals(that.getSku())) return false;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getSku().hashCode();
        result = 31 * result + getName().hashCode();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
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
