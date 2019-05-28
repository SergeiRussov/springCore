package org.russow.repository.impl;

import org.russow.repository.CategoryRepository;
import org.russow.model.Category;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CategoryRepositoryImpl implements CategoryRepository<Category> {

    @PersistenceContext()
    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    @Override
    @Transactional
    public List<Category> getCategories() {
        criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Category> categoryCriteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = categoryCriteriaQuery.from(Category.class);
        CriteriaQuery<Category> allCategories = categoryCriteriaQuery.select(categoryRoot);

        TypedQuery<Category> allCategoriesQuery = entityManager.createQuery(allCategories);

        final List<Category> categories = allCategoriesQuery.getResultList();

        entityManager.close();

        return categories;
    }
}
