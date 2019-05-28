package org.russow.service.impl;

import org.russow.model.Coupon;
import org.russow.repository.CouponRepository;
import org.russow.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponServiceImpl implements CouponService {

    private CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public boolean addCoupon(int discount) {
        boolean result;

        Coupon coupon = new Coupon();
        coupon.setDiscount(discount);

        couponRepository.addCoupon(coupon);

        result = true;

        return result;
    }
}
