package org.russow;

import org.russow.views.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("org.russow");
        context.refresh();

        Menu menu = (Menu) context.getBean("getMenu");

        menu.getCommand();
    }
}
