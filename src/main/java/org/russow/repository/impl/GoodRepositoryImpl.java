package org.russow.repository.impl;

import org.russow.repository.GoodRepository;
import org.russow.model.Good;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Transactional
public class GoodRepositoryImpl implements GoodRepository<Good> {

    @PersistenceContext()
    private EntityManager entityManager;

    @Override
    public List<Good> getGoodsFromCategory(int categoryId) {

        TypedQuery<Good> query = entityManager.createQuery(SQLGood.GET_GOOD_BY_CATEGORY_ID.QUERY, Good.class);
        query.setParameter("id", categoryId);

        final List<Good> goods = query.getResultList();

        entityManager.close();

        return goods;
    }

    @Override
    public boolean addGood(Good good) {
        boolean result;

        entityManager.persist(good);
        entityManager.close();

        result = true;

        return result;
    }

    enum SQLGood {
        GET_GOOD_BY_CATEGORY_ID("SELECT g FROM Good g WHERE g.category.id = (:id)");

        String QUERY;

        SQLGood(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
