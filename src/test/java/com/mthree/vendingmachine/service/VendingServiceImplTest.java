package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingException;
import com.mthree.vendingmachine.dto.Item;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingServiceImplTest {

    private VendingService service;

    public VendingServiceImplTest() {

        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        service = context.getBean("service", VendingServiceImpl.class);
    }

    @Test
    void testLoadVendingList() throws VendingException {

        //Arrange
        Item testClone = new Item(("Pepsi"), new BigDecimal("2"), 10);

        //Act and Assert
        assertEquals(1, service.loadVendingList().size());
        assertTrue(service.loadVendingList().contains(testClone));
    }

    @Test
    void testCheckItemExists () throws VendingException {

        //Arrange
        List<Item> tempItemList = service.loadVendingList();
        String userChoiceExists = "Pepsi";
        String userChoiceDoesNotExist = "Coke";
        BigDecimal sufficientBalance = new BigDecimal("2.5");
        BigDecimal inSufficientBalance = new BigDecimal("1.5");

        //Act and Assert
        assertFalse(service.checkItemExists(userChoiceDoesNotExist, tempItemList, sufficientBalance));
        assertFalse(service.checkItemExists(userChoiceExists, tempItemList, inSufficientBalance));

        assertTrue(service.checkItemExists(userChoiceExists, tempItemList, sufficientBalance));
    }

    @Test
    void testBuyItem() throws VendingException {

        //Arrange
        List<Item> tempItemList = service.loadVendingList();
        String userChoice = "Pepsi";
        int inventory = tempItemList.get(0).getInventory();

        //Act
        List<Item> updatedList = service.buyItem(userChoice, tempItemList);
        int finalInventory = inventory - 1;

        //Assert
        assertEquals(finalInventory, updatedList.get(0).getInventory());
        assertEquals(1, updatedList.size());
        assertEquals(userChoice, updatedList.get(0).getName());
    }

    @Test
    void testGetNewBalance() throws VendingException {

        //Assert
        List<Item> tempItemList = service.loadVendingList();
        String userChoice = "Pepsi";
        BigDecimal balance = new BigDecimal("3");

        //Act
        BigDecimal finalBalance = new BigDecimal("1");

        //Assert
        assertEquals(finalBalance, service.newBalance(userChoice, tempItemList, balance));
    }
}