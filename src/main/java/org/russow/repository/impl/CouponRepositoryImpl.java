package org.russow.repository.impl;

import org.russow.model.Coupon;
import org.russow.repository.CouponRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class CouponRepositoryImpl implements CouponRepository<Coupon> {

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    @Override
    @Transactional
    public List<Coupon> getCouponById(int couponId) {
        criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Coupon> couponCriteriaQuery = criteriaBuilder.createQuery(Coupon.class);
        Root<Coupon> couponRoot = couponCriteriaQuery.from(Coupon.class);
        ParameterExpression<Integer> value = criteriaBuilder.parameter(Integer.class);
        couponCriteriaQuery.select(couponRoot).where(criteriaBuilder.equal(couponRoot.get("id"), value));

        TypedQuery<Coupon> query = entityManager.createQuery(couponCriteriaQuery);
        query.setParameter(value, couponId);

        final List<Coupon> coupons = query.getResultList();
        entityManager.close();

        return coupons;
    }

    @Override
    @Transactional
    public boolean addCoupon(Coupon coupon) {
        boolean result;

        entityManager.persist(coupon);
        entityManager.close();

        result = true;

        return result;
    }
}
