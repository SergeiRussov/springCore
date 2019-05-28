package org.russow;

import org.russow.views.Menu;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("org.russow");
        context.refresh();

        Menu menu = (Menu) context.getBean("menu");

        menu.getCommand();
    }
}
