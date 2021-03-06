package org.russow.views.impl;

import lombok.Data;
import org.russow.jdbc.repository.impl.CouponRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Coupon;
import org.russow.model.Good;
import org.russow.model.Order;
import org.russow.service.impl.OrderServiceImpl;
import org.russow.views.Executable;
import org.russow.views.Menu;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@Data
public class OrderItem implements Executable {

    private Order order;
    private OrderServiceImpl orderService;
    private CouponRepositoryImpl couponRepository;
    private Coupon coupon;
    private BufferedReader reader;

    public OrderItem(OrderServiceImpl orderService, CouponRepositoryImpl couponRepository,
                     Coupon coupon) {
        this.orderService = orderService;
        this.couponRepository = couponRepository;
        this.coupon = coupon;
    }

    @Override
    public void run() {
        showOrder();
    }

    private void showOrder() {
        if (CategoryMenuItem.getOrder() == null) {
            System.out.println("Корзина пуста. Товары можно добавлять из раздела Каталог.");
        } else {
            order = CategoryMenuItem.getOrder();
            int totalPrice = 0;

            for (Good good : order.getGoods()) {
                totalPrice += good.getPrice();
            }

            order.setTotalPrice(totalPrice);

            String inf = "Дата заказа: " + order.getDate() + ", сумма заказа: " +
                    (totalPrice + ".\nСписок товаров: ");
            System.out.println(inf);
            System.out.println(getGoodsList());

            validePurchase(reader);
        }
    }

    private String getGoodsList() {
        StringBuilder temp = new StringBuilder();

        for (Good good : order.getGoods()) {
            temp.append("ID: " + good.getId() + ", наименование товара: " + good.getName() + ", цена товара: " +
                    good.getPrice() +"\n");
        }

        return temp.toString();
    }

    private void validePurchase(BufferedReader reader) {
        System.out.println("Провести заказ?\n1. Да\n2. Нет");
        try {
            int answer = Integer.parseInt(reader.readLine());
            if (answer == 1) {
                addCoupon(reader);
                orderService.addOrder(order);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void addCoupon(BufferedReader reader) {
        System.out.println("Хотите добавить купон на скидку?\n1. Да\n2. Нет");
        try {
            int answer = Integer.parseInt(reader.readLine());
            if (answer == 1) {
                System.out.println("Введите ID купона: ");
                int id = Integer.parseInt(reader.readLine());
                order.setCoupon(couponRepository.getCouponFromId(id));

                int newTotalPrice = order.getTotalPrice() - (order.getTotalPrice() / 100 * order.getCoupon().getDiscount());
                order.setTotalPrice(newTotalPrice);
            } else {
                coupon.setId(404);
                coupon.setDiscount(0);

                order.setCoupon(coupon);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        System.out.println("Текущая сумма заказа: " + order.getTotalPrice());
    }
}
