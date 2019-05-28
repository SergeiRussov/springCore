package org.russow.views.impl;

import org.russow.views.Executable;
import org.russow.views.Menu;
import org.springframework.stereotype.Component;

@Component
public class Ref implements Executable {

    private int roleId;

    public Ref() {

    }

    @Override
    public void run() {
        roleId = Menu.getCustomer().getRole().getId();
        showMenu();
    }

    private String getMenuFromUser() {

        return "\n" + "1. Справка\n" +
                "2. Каталог\n" +
                "3. История покупок\n" +
                "4. Корзина\n" +
                "0. Выход";
    }

    private String getMenuFromAdmin() {

        return "\n" + "1. Справка\n" +
                "2. Каталог\n" +
                "3. История покупок\n" +
                "4. Корзина\n" +
                "5. Создать купон на скидку\n" +
                "6. Загрузить товары из файла\n" +
                "0. Выход";
    }

    private void showMenu() {
        String menu = "";

        if (roleId == 1) {
            menu = getMenuFromUser();
        } else if ((roleId == 2) || (roleId == 3)) {
            menu = getMenuFromAdmin();
        }

        System.out.println(menu);
    }
}
