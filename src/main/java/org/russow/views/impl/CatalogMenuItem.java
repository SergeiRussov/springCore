package org.russow.views.impl;

import org.russow.repository.CategoryRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Category;
import org.russow.views.Executable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Data
public class CatalogMenuItem implements Executable {

    private CategoryRepository categoryRepository;
    private CategoryMenuItem categoryMenuItem;
    private BufferedReader reader;

    private Map<Integer, Executable> categoryMenuItems = new HashMap<>();

    public CatalogMenuItem(CategoryRepository categoryRepository, CategoryMenuItem categoryMenuItem) {
        this.categoryRepository = categoryRepository;
        this.categoryMenuItem = categoryMenuItem;
    }

    @Override
    public void run() {
        fillCategoryMenuItem();
        categoryMenuItem.setReader(reader);
        selectCategory(reader);
    }

    private void fillCategoryMenuItem() {
        List<Category> list = categoryRepository.getCategories();

        for (Category category : list) {
            System.out.println(category.getId() + ". " + category.getName());
            categoryMenuItem.setId(category.getId());
            categoryMenuItems.put(category.getId(), categoryMenuItem);
        }
    }

    private void selectCategory(BufferedReader reader) {
        try {
            System.out.print("\nВведите номер раздела: ");
            int id = Integer.parseInt(reader.readLine());
            if (categoryMenuItems.containsKey(id)) {
                categoryMenuItems.get(id).run();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
