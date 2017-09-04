package com.walid.pricing;

import com.walid.checkout.Checkout;
import com.walid.inventory.InventoryItem;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PricingRuleTest {

    // no discount applied
    @Test
    public void buy2IPDforRRP$() throws Exception {
        Checkout checkout = new Checkout();
        checkout.scan("ipd");
        checkout.scan("ipd");
        checkout = new PricingRule().given(checkout)
                .when(co -> co.includesOrMore(4, "ipd"))
                .then(co -> co.reducePrice("ipd", 499.99));
        assertThat(checkout.total(), equalTo(2 * InventoryItem.getItem("ipd").getPrice()));
    }

    // the brand new Super iPad will have a bulk discounted applied, where the price will drop to $499.99 each, if someone buys more than 4
    @Test
    public void buy6IPDfor499$Each() throws Exception {
        Checkout checkout = new Checkout();
        checkout.scan("ipd");
        checkout.scan("ipd");
        checkout.scan("ipd");
        checkout.scan("ipd");
        checkout.scan("ipd");
        checkout.scan("ipd");
        checkout = new PricingRule().given(checkout)
                .when(co -> co.includesOrMore(4, "ipd"))
                .then(co -> co.reducePrice("ipd", 499.99));
        assertThat(checkout.total(), equalTo(6 * 499.99));
    }

    @Test
    public void buy2ATVgetNoDiscount() throws Exception {
        Checkout checkout = new Checkout();
        checkout.scan("atv");
        checkout.scan("atv");
        checkout = new PricingRule().given(checkout)
                .when(co -> co.includesOrMore(3, "atv"))
                .then(co -> co.buyNgetOnefor$(3, "atv", 0.0));
        assertThat(checkout.total(), equalTo(2 * InventoryItem.getItem("atv").getPrice()));
    }

    // we're going to have a 3 for 2 deal on Apple TVs. For example, if you buy 3 Apple TVs, you will pay the price of 2 only
    @Test
    public void buy3ATVget1for0$() throws Exception {
        Checkout checkout = new Checkout();
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout = new PricingRule().given(checkout)
                .when(co -> co.includesOrMore(3, "atv"))
                .then(co -> co.buyNgetOnefor$(3, "atv", 0.0));
        assertThat(checkout.total(), equalTo(2 * InventoryItem.getItem("atv").getPrice()));
    }

    // we're going to have a 3 for 2 deal on Apple TVs. For example, if you buy 3 Apple TVs, you will pay the price of 2 only
    @Test
    public void buy5ATVget1for0$() throws Exception {
        Checkout checkout = new Checkout();
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout = new PricingRule().given(checkout)
                .when(co -> co.includesOrMore(3, "atv"))
                .then(co -> co.buyNgetOnefor$(3, "atv", 0.0));
        assertThat(checkout.total(), equalTo(4 * InventoryItem.getItem("atv").getPrice()));
    }

    // we're going to have a 3 for 2 deal on Apple TVs. For example, if you buy 3 Apple TVs, you will pay the price of 2 only
    @Test
    public void buy6ATVget2for0$() throws Exception {
        Checkout checkout = new Checkout();
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout = new PricingRule().given(checkout)
                .when(co -> co.includesOrMore(3, "atv"))
                .then(co -> co.buyNgetOnefor$(3, "atv", 0.0));
        assertThat(checkout.total(), equalTo(4 * InventoryItem.getItem("atv").getPrice()));
    }

    // we will bundle in a free VGA adapter free of charge with every MacBook Pro sold
    @Test
    public void buy2MBPget2VGAfor0$() throws Exception {
        Checkout checkout = new Checkout();
        checkout.scan("mbp");
        checkout.scan("mbp");
        checkout.scan("vga");
        checkout.scan("vga");
        checkout = new PricingRule().given(checkout)
                .then(co -> co.buyXgetYfor$("mbp", "vga", 0.0));
        assertThat(checkout.total(), equalTo(2 * InventoryItem.getItem("mbp").getPrice()));
    }
}