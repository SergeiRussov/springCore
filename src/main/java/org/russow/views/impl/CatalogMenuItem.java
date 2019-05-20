package org.russow.views.impl;

import lombok.AllArgsConstructor;
import org.russow.jdbc.repository.impl.CategoryRepositoryImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Category;
import org.russow.views.Executable;
import org.russow.views.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class CatalogMenuItem implements Executable {

    private CategoryRepositoryImpl categoryRepository;
    private CategoryMenuItem categoryMenuItem;
    private Map<Integer, Executable> categoryMenuItems;
    private BufferedReader reader;

    public CatalogMenuItem(CategoryRepositoryImpl categoryRepository, CategoryMenuItem categoryMenuItem,
                           Map<Integer, Executable> categoryMenuItems) {
        this.categoryRepository = categoryRepository;
        this.categoryMenuItem = categoryMenuItem;
        this.categoryMenuItems = categoryMenuItems;
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
