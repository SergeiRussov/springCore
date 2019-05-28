package org.russow.repository.impl;

import org.russow.model.Coupon;
import org.russow.repository.CouponRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class CouponRepositoryImpl implements CouponRepository<Coupon> {

    @PersistenceContext()
    private EntityManager entityManager;

    private EntityTransaction transaction;
    private CriteriaBuilder criteriaBuilder;

    @Override
    public Coupon getCouponById(int couponId) {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        transaction = entityManager.getTransaction();
        transaction.begin();

        CriteriaQuery<Coupon> couponCriteriaQuery = criteriaBuilder.createQuery(Coupon.class);
        Root<Coupon> couponRoot = couponCriteriaQuery.from(Coupon.class);
        couponCriteriaQuery.select(couponRoot);
        couponCriteriaQuery.where(criteriaBuilder.equal(couponRoot.get("id"), ":couponId"));

        TypedQuery<Coupon> query = entityManager.createQuery(couponCriteriaQuery);
        query.setParameter("couponId", couponId);

        final List<Coupon> coupons = entityManager.createQuery(couponCriteriaQuery).getResultList();

        transaction.commit();
        entityManager.close();

        Coupon coupon = coupons.get(0);

        return coupon;
    }
}
