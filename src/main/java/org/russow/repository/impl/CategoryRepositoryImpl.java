package org.russow.repository.impl;

import org.russow.repository.CategoryRepository;
import org.russow.model.Category;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CategoryRepositoryImpl implements CategoryRepository<Category> {

    @PersistenceContext()
    private EntityManager entityManager;

    private EntityTransaction transaction;

    @Override
    public List<Category> getCategories() {
        transaction = entityManager.getTransaction();
        transaction.begin();

        final List<Category> categories = entityManager.createQuery("from Category", Category.class).getResultList();

        transaction.commit();
        entityManager.close();

        return categories;
    }
}
