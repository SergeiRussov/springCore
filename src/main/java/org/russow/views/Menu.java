package org.russow.views;

import lombok.NoArgsConstructor;
import org.russow.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Customer;
import org.russow.views.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@NoArgsConstructor
public class Menu {

    private static Customer currentCustomer;

    private String customerName;
    private Ref ref;
    private CatalogMenuItem catalogMenuItem;
    private OrderHistoryView orderHistoryView;
    private OrderItem orderItem;
    private CouponMenuItem couponMenuItem;
    private CreateGoodsMenuItem createGoodsMenuItem;
    private CloseShop closeShop;
    private CustomerRepository customerRepository;
    private CategoryMenuItem categoryMenuItem;

    private Map<Integer, Executable> menuItems = new HashMap<>();

    @Autowired
    public Menu(Ref ref, CatalogMenuItem catalogMenuItem, OrderHistoryView orderHistoryView,
                OrderItem orderItem, CouponMenuItem couponMenuItem,
                CreateGoodsMenuItem createGoodsMenuItem, CloseShop closeShop, CustomerRepository customerRepository,
                CategoryMenuItem categoryMenuItem) {
        this.ref = ref;
        this.catalogMenuItem = catalogMenuItem;
        this.orderHistoryView = orderHistoryView;
        this.orderItem = orderItem;
        this.couponMenuItem = couponMenuItem;
        this.createGoodsMenuItem = createGoodsMenuItem;
        this.closeShop = closeShop;
        this.customerRepository = customerRepository;
        this.categoryMenuItem = categoryMenuItem;
    }

    public static Customer getCustomer() {
        return currentCustomer;
    }

    public void getCommand() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){

            customerLogin(reader);

            catalogMenuItem.setReader(reader);

            couponMenuItem.setReader(reader);
            createGoodsMenuItem.setReader(reader);
            orderItem.setReader(reader);
            categoryMenuItem.setReader(reader);

            menuItems.put(0, closeShop);
            menuItems.put(1, ref);
            menuItems.put(2, catalogMenuItem);
            menuItems.put(3, orderHistoryView);
            menuItems.put(4, orderItem);
            menuItems.put(5, couponMenuItem);
            menuItems.put(6, createGoodsMenuItem);

            while (CloseShop.isIsWork()) {
                setCustomer(customerName);
                menuItems.get(1).run();
                System.out.print("\nВведите номер команды: ");
                int itemNumber = Integer.parseInt(reader.readLine());
                if (menuItems.containsKey(itemNumber)) {
                    menuItems.get(itemNumber).run();
                } else {
                    System.out.println("Такой команды не существует. Повторите попытку.");
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private Customer customerLogin(BufferedReader reader) {
        while (true) {
            try {
                System.out.print("Введите полное имя пользователя: ");
                customerName = reader.readLine();

                setCustomer(customerName);

                if (currentCustomer == null) {
                    throw new IOException();
                }

                System.out.println("\nУспешный вход");
                break;
            } catch (IOException e) {
                System.out.println("\nПользователь не найден");
                log.error(e.getMessage());
            }
        }

        return currentCustomer;
    }

    private void setCustomer(String customerName) {
        List<Customer> customers = customerRepository.getCustomers();

        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                currentCustomer = customer;
            }
        }
    }
}
