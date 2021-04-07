package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingDaoStubImpl implements VendingDao {

    public Item onlyItem;
    List<Item> itemList = new ArrayList();

    public VendingDaoStubImpl() {
        onlyItem = new Item("Pepsi", new BigDecimal("2"), 10);
    }

    public VendingDaoStubImpl(Item item) {
        onlyItem = item;
    }

    public List<Item> loadVendingList() {
        itemList.add(onlyItem);
        return itemList;
    }


    public List<Item> buyItem(String userChoice, List<Item> vendingItems) {
        Item newItem = new Item(onlyItem.getName(),onlyItem.getCost(),onlyItem.getInventory()-1);
        itemList.remove(onlyItem);
        itemList.add(newItem);
        return itemList;
    }

    public void updateInventory(List<Item> vendingItems) throws VendingException {

    }


}
