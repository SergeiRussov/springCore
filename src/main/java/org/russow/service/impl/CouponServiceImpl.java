package org.russow.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.russow.model.Coupon;
import org.russow.service.CouponService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

@Service
@Slf4j
public class CouponServiceImpl implements CouponService {

    @PersistenceContext
    private EntityManager entityManager;

    private EntityTransaction transaction;

    @Override
    public boolean addCoupon(int discount) {
        boolean result;

        transaction = entityManager.getTransaction();
        transaction.begin();

        Coupon coupon = new Coupon();
        coupon.setDiscount(discount);

        entityManager.persist(coupon);
        transaction.commit();

        entityManager.close();

        result = true;

        return result;
    }
}
