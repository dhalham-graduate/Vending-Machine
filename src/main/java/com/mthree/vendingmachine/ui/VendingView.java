package com.mthree.vendingmachine.ui;

import com.mthree.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingView {

    private UserIO io;

    public VendingView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection(List<Item> passedSet, BigDecimal currentBalance) {
        io.print("\r\nVending Machine Options:\r\n");
        for (Item i : passedSet){
            if (i.getInventory() > 0) {
                io.print(i.getName() + ": $" + i.getCost());
            }
        }
        io.print("\r\nBalance: $" + currentBalance);
        io.print("\r\n1. Top Up");
        io.print("2. Buy an item");
        io.print("3. Exit\r\n");


        return io.readInt("Please select from the above choices.", 1, 3);
    }

    public List<BigDecimal> getTopUp() {

        List<BigDecimal> moneyCollection = new ArrayList<>();

        moneyCollection.add(new BigDecimal(io.readString("Please enter the amount of quarters you would like to top up: ")));
        moneyCollection.add(new BigDecimal(io.readString("Please enter the amount of dimes you would like to top up: ")));
        moneyCollection.add(new BigDecimal(io.readString("Please enter the amount of nickels you would like to top up: ")));
        moneyCollection.add(new BigDecimal(io.readString("Please enter the amount of cents you would like to top up: ")));

        io.readString("Money successfully added, please hit enter to return to the menu:");
        return moneyCollection;
    }

    public String getUserChoice() {
        return io.readString("\r\nPlease type the item you would like to buy:");
    }

    public void printBuyOutCome(boolean outcome, String userChoice) {

        if(!outcome){
            io.readString("\r\n" + userChoice.substring(0,1).toUpperCase() + userChoice.substring(1) + " does not exist, or you do not have enough money. Please hit enter to return to the main menu:");
        }

        if (outcome) {
            io.readString("\r\nYou have successfully bought " + userChoice.substring(0,1).toUpperCase() + userChoice.substring(1) + "! Hit enter to return to the main menu");
        }
    }

    public void displayChange(BigDecimal quarters, BigDecimal dimes, BigDecimal nickels, BigDecimal pennies) {
        io.print("\r\nThank you for using the vending machine! Here is your change: \r\n");
        io.print("Quarters: " + quarters);
        io.print("Dimes: " + dimes);
        io.print("Nickels: " + nickels);
        io.print("Pennies: " + pennies);
    }
}
