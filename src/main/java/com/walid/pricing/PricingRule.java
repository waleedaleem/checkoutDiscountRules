package com.walid.pricing;

import com.walid.checkout.Checkout;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class PricingRule {

    private Checkout checkout;
    private Predicate condition;

    public PricingRule given(Checkout checkout) {
        this.checkout = checkout;
        return this;
    }

    public PricingRule when(Predicate<Checkout> condition) {
        this.condition = condition;
        return this;
    }

    public Checkout then(UnaryOperator<Checkout> adjustment) {
        // apply no change if either condition or adjustment are missing or condition is not satisfied
        if (condition == null || adjustment == null || condition.negate().test(checkout)) {
            return checkout;
        } else {
            return adjustment.apply(checkout);
        }
    }
}
