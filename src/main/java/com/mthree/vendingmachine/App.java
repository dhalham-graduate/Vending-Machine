package com.mthree.vendingmachine;

import com.mthree.vendingmachine.controller.VendingController;
import com.mthree.vendingmachine.dao.VendingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) throws VendingException {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingController controller = context.getBean("controller", VendingController.class);

        controller.run();
    }

}
