package org.russow.repository.impl;

import org.russow.repository.GoodRepository;
import org.russow.model.Good;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class GoodRepositoryImpl implements GoodRepository<Good> {

    @PersistenceContext()
    private EntityManager entityManager;

    private EntityTransaction transaction;

    @Override
    public List<Good> getGoodsFromCategory(int categoryId) {
        transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Good> query = entityManager.createQuery(SQLGood.GET_GOOD_BY_CATEGORY_ID.QUERY, Good.class);
        query.setParameter("id", categoryId);

        final List<Good> goods = query.getResultList();

        transaction.commit();
        entityManager.close();

        return goods;
    }

    enum SQLGood {
        GET_GOOD_BY_CATEGORY_ID("SELECT g FROM Good g WHERE g.category.id = (:id)");

        String QUERY;

        SQLGood(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
