package org.russow.views.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.russow.service.GoodService;
import org.russow.views.Executable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

@Component
@Slf4j
@Data
public class CreateGoodsMenuItem implements Executable {

    private GoodService goodService;
    private BufferedReader reader;

    public CreateGoodsMenuItem(GoodService goodService) {
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

            goodService.addGoodsFromFile(new File(path));

            flag = true;
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        String result = (flag ? "Успешно" : "Ошибка");

        return result;
    }
}
