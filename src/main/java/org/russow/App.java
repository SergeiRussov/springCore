package org.russow;

import org.russow.views.Menu;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");

        Menu menu = (Menu) ctx.getBean("menu");

        menu.getCommand();
    }
}
