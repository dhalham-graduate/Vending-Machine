package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class VendingDaoFileImpl implements VendingDao {

    public final String ITEM_LIST;
    public final static String DELIMITER = "::";
    private Map<String, Item> items = new HashMap<>();

    public VendingDaoFileImpl() {
        ITEM_LIST = "VendingMachineItems.txt";
    }

    public VendingDaoFileImpl(String fileName) {
        ITEM_LIST = fileName;
    }

    private Item unmarshallItem (String itemText){

        String[] itemTokens = itemText.split(DELIMITER);
        return new Item(itemTokens[0], new BigDecimal(itemTokens[1]), Integer.parseInt(itemTokens[2]));

    }

    public List<Item> loadVendingList() throws VendingException {

        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_LIST)));
        } catch (FileNotFoundException e) {
            throw new VendingException(
                    "-_- Could not load roster data into memory.", e);
        }

        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getName(), currentItem);
        }

        scanner.close();
        return new ArrayList<Item>(items.values());
    }

    @Override
    public List<Item> buyItem(String userChoice, List<Item> vendingItems) {

        List<Item> itemList = new ArrayList<>(vendingItems);

        for (Item items : vendingItems) {
            if (userChoice.toLowerCase().equals(items.getName().toLowerCase())){
                Item newItem = new Item(items.getName(), items.getCost(), (items.getInventory()-1));
                itemList.remove(items);
                itemList.add(newItem);
            }
        }

        return itemList;
    }

    private String marshallItem(Item aItem){

        String itemAsText = aItem.getName() + DELIMITER;
        itemAsText += aItem.getCost() + DELIMITER;
        itemAsText += aItem.getInventory();

        return itemAsText;
    }


    @Override
    public void updateInventory(List<Item> vendingItems) throws VendingException{

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ITEM_LIST));
        } catch (IOException e) {
            throw new VendingException("Could not update the inventory.", e);
        }

        for (Item currentItem : vendingItems) {
            out.println(marshallItem(currentItem));
            out.flush();
        }
        out.close();
    }

}
