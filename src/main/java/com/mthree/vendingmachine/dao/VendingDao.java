package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;

import java.util.List;

public interface VendingDao {

    List<Item> loadVendingList() throws VendingException;

    List<Item> buyItem(String userChoice, List<Item> vendingItems);

    void updateInventory(List<Item> vendingItems) throws VendingException;
}
