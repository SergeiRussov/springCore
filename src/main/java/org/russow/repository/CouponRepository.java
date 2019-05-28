package org.russow.repository;

import org.russow.model.Coupon;
import org.springframework.stereotype.Component;

@Component
public interface CouponRepository<Entity extends Coupon> {

    Entity getCouponById(int couponId);
}
