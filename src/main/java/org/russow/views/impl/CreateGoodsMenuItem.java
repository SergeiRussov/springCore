package org.russow.views.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.russow.jdbc.JDBCUtils;
import lombok.extern.slf4j.Slf4j;
import org.russow.service.impl.GoodServiceImpl;
import org.russow.views.Executable;
import org.russow.views.Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

@Slf4j
@Data
public class CreateGoodsMenuItem implements Executable {

    private GoodServiceImpl goodService;
    private BufferedReader reader;

    public CreateGoodsMenuItem(GoodServiceImpl goodService) {
        this.goodService = goodService;
    }

    @Override
    public void run() {
        System.out.println(downloadGoods(reader));
    }

    private String downloadGoods(BufferedReader reader) {
        boolean flag = false;

        try {
            System.out.print("\nВведите путь до файла: ");
            String path = reader.readLine();

            goodService.addGoods(new File(path));

            flag = true;
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        String result = (flag ? "Успешно" : "Ошибка");

        return result;
    }
}
