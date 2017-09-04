package com.walid.checkout.inventory;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class InventoryItemTest {
    @Test
    public void initInventroyItemList() throws Exception {
        InventoryItem.initInventroyItemList();
        assertThat(InventoryItem.INVENTORY_ITEM_LIST.get(0).getSku(), equalTo("ipd"));
        assertThat(InventoryItem.INVENTORY_ITEM_LIST.get(0).getName(), equalTo("Super iPad"));
        assertThat(InventoryItem.INVENTORY_ITEM_LIST.get(0).getPrice(), equalTo(549.99));
    }
}