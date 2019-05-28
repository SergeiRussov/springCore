package org.russow.views.impl;


import org.russow.repository.GoodRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Good;
import org.russow.model.Order;
import org.russow.views.Executable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Data
public class CategoryMenuItem implements Executable {

    private static Order order;
    private GoodRepository goodRepository;
    private int id;
    private BufferedReader reader;

    public CategoryMenuItem() {

    }

    public CategoryMenuItem(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    public static Order getOrder() {
        return order;
    }

    @Override
    public void run() {
        List<Good> goods = getGoodsFromCategory(id);

        int i = 1;
        for (Good good : goods) {
            System.out.println(i++ + ". " + good.getName() + " " + good.getPrice());
        }

        setGoodsFromOrder();
    }

    private List<Good> getGoodsFromCategory(int id) {
        List<Good> goods = goodRepository.getGoodsFromCategory(id);

        return goods;
    }

    private List<Good> setGoodsFromOrder() {
        List<Good> goodList;

        if (order == null) {
            order = new Order();
            order.setDate(LocalDate.now());
            goodList = new ArrayList<>();

            addGoods(goodList);

            order.setGoods(goodList);
        } else {
            goodList = (List<Good>) order.getGoods();

            addGoods(goodList);
            order.setGoods(goodList);
        }

        return goodList;
    }

    private void addGoods(List<Good> goods) {
        List<Good> goodsFromCategory = getGoodsFromCategory(id);

        while (true) {
            System.out.print("Ведите ID товара, чтобы добавить его в корзину и 0, чтобы вернуться в меню: ");
            int id = -1;

            try {
                id = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            if (id == 0) {
                break;
            } else {
                goods.add(goodsFromCategory.get(id - 1));
                System.out.println("Успешно");
            }
        }
    }
}
