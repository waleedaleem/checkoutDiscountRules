package com.walid.checkout;

import com.walid.inventory.InventoryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Checkout {

    private final List<CheckoutItem> checkoutItems = new ArrayList<>();

    public void scan(String sku) {
        // add scanned item to list
        checkoutItems.add(new CheckoutItem(InventoryItem.getItem(sku)));
    }

    public boolean includesOrMore(int count, String sku) {
        return checkoutItems.stream()
                .filter(item -> item.getSku().equals(sku))
                .count() >= count;
    }

    /**
     * reduce the price of an item to a given value
     *
     * @param sku
     * @param reducedPrice
     * @return
     */
    public Checkout reducePrice(String sku, double reducedPrice) {

        checkoutItems.parallelStream()
                .filter(item -> item.getSku().equals(sku))
                .forEach(item -> item.adjustSalePrice(reducedPrice));

        return this;
    }

    /**
     * Buy n pieces of a specific item, get one of them for the noted price (discountedPrice)
     * Note that discountedPrice can perfectly be zero as well a.k.a buy n get one free
     *
     * @param n
     * @param sku
     * @param discountedPrice
     * @return
     */
    public Checkout buyNgetOnefor$(int n, String sku, double discountedPrice) {

        // not willing to give items away ;)
        assert n > 1;

        AtomicInteger index = new AtomicInteger();

        Map<Integer, CheckoutItem> discountItemMap = checkoutItems.parallelStream()
                .filter(item -> item.getSku().equals(sku))
                .collect(Collectors.toMap(item -> index.incrementAndGet(), Function.identity()));

        // adjust the price of every nth item to discountedPrice
        discountItemMap.entrySet().parallelStream()
                .filter(e -> e.getKey() % n == 0)
                .forEach(e -> e.getValue().adjustSalePrice(discountedPrice));

        return this;
    }

    /**
     * Buy a specific item, get another item bundeled at a reduced price (discountedPrice)
     * Note that discountedPrice can perfectly be zero as well a.k.a buy n get one bundeled for free
     *
     * @param buySku
     * @param bundeledSku
     * @param discountedPrice
     * @return
     */
    public Checkout buyXgetYfor$(String buySku, String bundeledSku, double discountedPrice) {

        // number of bundledSku to sell for discountedPrice
        long count = checkoutItems.parallelStream()
                .filter(item -> item.getSku().equals(buySku))
                .count();

        // adjust the price of bundled items
        checkoutItems.parallelStream()
                .filter(item -> item.getSku().equals(bundeledSku))
                .limit(count)
                .forEach(bundledItem -> bundledItem.adjustSalePrice(discountedPrice));

        return this;
    }

    public double total() {
        return checkoutItems.parallelStream()
                .mapToDouble(CheckoutItem::getSalePrice)
                .sum();
    }
}
