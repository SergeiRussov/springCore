package org.russow.jdbc.repository;

import org.russow.model.Coupon;

import java.util.List;

public interface CouponRepository<Entity extends Coupon> {

    List<Entity> getCoupons();
}
