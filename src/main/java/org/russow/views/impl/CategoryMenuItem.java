package org.russow.views.impl;

import lombok.AllArgsConstructor;
import org.russow.jdbc.JDBCUtils;
import org.russow.jdbc.repository.impl.GoodRepositoryImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Good;
import org.russow.model.Order;
import org.russow.views.Executable;
import org.russow.views.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class CategoryMenuItem implements Executable {

    private static Order order;
    private GoodRepositoryImpl goodRepository;
    private int id;
    private BufferedReader reader;

    public CategoryMenuItem(GoodRepositoryImpl goodRepository) {
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
            goodList = order.getGoods();

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
