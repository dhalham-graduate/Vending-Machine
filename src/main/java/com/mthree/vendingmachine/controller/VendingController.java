package com.mthree.vendingmachine.controller;

import com.mthree.vendingmachine.dao.VendingException;
import com.mthree.vendingmachine.dto.Item;
import com.mthree.vendingmachine.service.VendingService;
import com.mthree.vendingmachine.ui.VendingView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class VendingController {

    private final VendingView view;
    private final VendingService service;
    private BigDecimal balance = new BigDecimal("0");
    private List<Item> vendingItems;
    private boolean itemExists;
    private boolean enoughMoney;

    public VendingController(VendingView view, VendingService service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws VendingException {


            vendingItems = new ArrayList<>(service.loadVendingList());
            boolean keepGoing = true;
            int menuSelection;

            while (keepGoing) {

                menuSelection = getMenuSelection(vendingItems);
                switch (menuSelection) {
                    case 1:
                        topUp();
                        break;
                    case 2:
                        buyItem();
                        break;
                    case 3:
                        keepGoing = false;
                        exitProgramme();
                        break;
                    default:
                        break;
                }

            }

    }

    private void exitProgramme() {
        List<BigDecimal> changeList = getChange(balance);
        view.displayChange(changeList.get(0), changeList.get(1), changeList.get(2), changeList.get(3));
    }

    private List<BigDecimal> getChange(BigDecimal balance) {

        BigDecimal remaining = balance.multiply(new BigDecimal("100"));
        BigDecimal quarters;
        BigDecimal dimes;
        BigDecimal nickels;
        BigDecimal pennies;
        List<BigDecimal> changeList = new ArrayList<>();

        quarters = remaining.divide(new BigDecimal("25"), 0, RoundingMode.DOWN);
        remaining = remaining.remainder(new BigDecimal("25"));
        dimes = remaining.divide(new BigDecimal("10"), 0, RoundingMode.DOWN);
        remaining = remaining.remainder(new BigDecimal("10"));
        nickels = remaining.divide(new BigDecimal("5"), 0, RoundingMode.DOWN);
        remaining = remaining.remainder(new BigDecimal("5"));
        pennies = remaining.divide(new BigDecimal("1"), 0, RoundingMode.DOWN);
        remaining = remaining.remainder(new BigDecimal("1"));

        changeList.add(0, quarters);
        changeList.add(1, dimes);
        changeList.add(2, nickels);
        changeList.add(3, pennies);

        return changeList;
    }

    private void buyItem() throws VendingException{

        vendingItems = new ArrayList<>(service.loadVendingList());
        String userChoice = view.getUserChoice();
        itemExists = service.checkItemExists(userChoice, vendingItems, balance);

        if (itemExists) {
            vendingItems = service.buyItem(userChoice, vendingItems);
            balance = service.newBalance (userChoice, vendingItems, balance);
            service.updateInventory(vendingItems);
            view.printBuyOutCome(true, userChoice.toLowerCase());
        }

        else {
            view.printBuyOutCome(false, userChoice.toLowerCase());
        }

    }

    private void topUp() {

        List<BigDecimal> moneyRetrieved = view.getTopUp();

        balance = balance.add(convertToDollars(moneyRetrieved.get(0), Money.QUARTER));
        balance = balance.add(convertToDollars(moneyRetrieved.get(1), Money.DIME));
        balance = balance.add(convertToDollars(moneyRetrieved.get(2), Money.NICKEL));
        balance = balance.add(convertToDollars(moneyRetrieved.get(3), Money.PENNY));

    }

    private int getMenuSelection(List<Item> itemSet) {
        return view.printMenuAndGetSelection(itemSet, balance);
    }

    private BigDecimal convertToDollars (BigDecimal amount, Money typeOfMoney) {

        switch (typeOfMoney) {
            case QUARTER: return amount.multiply(new BigDecimal("0.25"));
            case NICKEL: return amount.multiply(new BigDecimal("0.05"));
            case DIME: return amount.multiply(new BigDecimal("0.1"));
            case PENNY: return amount.multiply(new BigDecimal("0.01"));
            default: return new BigDecimal("0");
        }
    }

}
