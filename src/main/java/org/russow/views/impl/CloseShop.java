package org.russow.views.impl;

import lombok.Data;
import org.russow.views.Executable;
import org.springframework.stereotype.Component;

@Component
@Data
public class CloseShop implements Executable {

    private static boolean isWork = true;

    public static boolean isIsWork() {
        return isWork;
    }

    @Override
    public void run() {
        System.out.println(closeShop());
    }

    private String closeShop() {
        isWork = false;
        return "Закрытие магазина";
    }
}
