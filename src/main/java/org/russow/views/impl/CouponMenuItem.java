package org.russow.views.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import lombok.extern.slf4j.Slf4j;
import org.russow.service.impl.CouponServiceImpl;
import org.russow.views.Executable;
import org.russow.views.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;

@Slf4j
@Data
public class CouponMenuItem implements Executable {

    private CouponServiceImpl couponService;
    private BufferedReader reader;

    public CouponMenuItem(CouponServiceImpl couponService) {
        this.couponService = couponService;
    }

    @Override
    public void run() {
        System.out.println(createCoupon(reader));
    }

    private String createCoupon(BufferedReader reader) {
        boolean flag = false;

        System.out.print("\nВведите величину скидки (в процентах): ");

        try {
            int discount = Integer.parseInt(reader.readLine());
            couponService.addCoupon(discount);

            flag = true;
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        String result = (flag ? "Успешно" : "Ошибка");

        return result;
    }
}
