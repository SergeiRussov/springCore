package org.russow.config;

import org.russow.model.*;
import org.russow.views.Menu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Category getCategory(){
        return new Category();
    }

    @Bean
    public Contact getContact() {
        return new Contact();
    }

    @Bean
    public Coupon getCoupon() {
        return new Coupon();
    }

    @Bean
    public Customer getCustomer() {
        return new Customer();
    }

    @Bean
    public Good getGood() {
        return new Good();
    }

    @Bean
    public Order getOrder() {
        return new Order();
    }

    @Bean
    public OrderStatus getOrderStatus() {
        return new OrderStatus();
    }

    @Bean
    public Role getRole() {
        return new Role();
    }

    @Bean
    public Menu getMenu() {
        return new Menu();
    }
}
