package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingException;
import com.mthree.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public interface VendingService {

    public List<Item> loadVendingList() throws VendingException;

    public boolean checkItemExists(String userChoice, List<Item> itemList, BigDecimal balance);

    List<Item> buyItem(String userChoice, List<Item> vendingItems) throws VendingException;

    void updateInventory(List<Item> vendingItems) throws VendingException;

    BigDecimal newBalance(String userChoice, List<Item> vendingItems, BigDecimal balance);
}
