package com.walid.inventory;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class InventoryItemTest {
    @Test
    public void initInventroyItemList() throws Exception {
        assertTrue(InventoryItem.INVENTORY_ITEM_LIST.contains(new InventoryItem("ipd", "Super iPad", 549.99)));
    }
}