package com.walid.checkout;

import com.walid.inventory.InventoryItem;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class CheckoutTest {
    @Test
    public void total() throws Exception {
        Checkout checkout = new Checkout();
        checkout.scan("ipd");
        assertThat(checkout.total(), equalTo(InventoryItem.getItem("ipd").getPrice()));

        checkout.scan("ipd");
        assertThat(checkout.total(), equalTo(2 * InventoryItem.getItem("ipd").getPrice()));
    }
}