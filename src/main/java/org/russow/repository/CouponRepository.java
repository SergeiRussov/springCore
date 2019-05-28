package org.russow.repository;

import org.russow.model.Coupon;

import java.util.List;

public interface CouponRepository<Entity extends Coupon> {

    List<Entity> getCouponById(int couponId);
    boolean addCoupon(Entity entity);
}
