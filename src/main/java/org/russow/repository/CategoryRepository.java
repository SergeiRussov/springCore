package org.russow.repository;

import org.russow.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryRepository<Entity extends Category> {

    List<Entity> getCategories();
}
