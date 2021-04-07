package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.AuditDao;
import com.mthree.vendingmachine.dao.VendingDao;
import com.mthree.vendingmachine.dao.VendingException;
import com.mthree.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public class VendingServiceImpl implements VendingService {

    private VendingDao dao;
    private AuditDao auditDao;
    private boolean itemExists;

    public VendingServiceImpl(VendingDao dao, AuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    public List<Item> loadVendingList() throws VendingException {
       return dao.loadVendingList();
    }

    @Override
    public boolean checkItemExists(String userChoice, List<Item> itemList, BigDecimal balance) {

        itemExists = false;

        for (Item items : itemList)
        {
            if (userChoice.toLowerCase().equals(items.getName().toLowerCase())) {
                itemExists = true;
                if (itemExists) {
                    if (balance.floatValue() < items.getCost().floatValue()) {
                        itemExists = false;
                    }
                }
            }
        }

        return itemExists;
    }

    @Override
    public List<Item> buyItem(String userChoice, List<Item> vendingItems) throws VendingException{
        auditDao.writeAuditEntry("User bought " + userChoice.substring(0,1).toUpperCase() + userChoice.substring(1).toLowerCase() + ".");
        return dao.buyItem(userChoice, vendingItems);
    }

    @Override
    public void updateInventory(List<Item> vendingItems) throws VendingException{
        dao.updateInventory(vendingItems);
    }

    @Override
    public BigDecimal newBalance(String userChoice, List<Item> vendingItems, BigDecimal balance) {

        BigDecimal updatedBalance = new BigDecimal("0");

        for (Item items : vendingItems) {
            if (userChoice.toLowerCase().equals(items.getName().toLowerCase())) {
                updatedBalance =  balance.subtract(items.getCost());
            }
        }

        return updatedBalance;
    }

}
