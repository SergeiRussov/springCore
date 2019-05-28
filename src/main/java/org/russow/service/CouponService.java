package org.russow.service;

import org.springframework.stereotype.Component;

@Component
public interface CouponService {

    boolean addCoupon(int discount);
}
