package com.walid.checkout;

import com.walid.pricing.PricingRule;

/**
 * @author Walid Moustafa
 */
public class App {
    public static void main(String[] args) {
        // SKUs Scanned: atv, atv, atv, vga Total expected: $249.00
        Checkout co = new Checkout();
        co.scan("atv")
                .scan("atv")
                .scan("atv")
                .scan("vga");
        co = applyDiscountRules(co);
        System.out.printf("total = $%.2f", co.total());

        // SKUs Scanned: atv, ipd, ipd, atv, ipd, ipd, ipd Total expected: $2718.95
        co = new Checkout();
        co.scan("atv")
                .scan("ipd")
                .scan("ipd")
                .scan("atv")
                .scan("ipd")
                .scan("ipd")
                .scan("ipd");
        co = applyDiscountRules(co);
        System.out.printf("total = $%.2f", co.total());

        // SKUs Scanned: mbp, vga, ipd Total expected: $1949.98
        co = new Checkout();
        co.scan("mbp")
                .scan("vga")
                .scan("ipd");
        co = applyDiscountRules(co);
        System.out.printf("total = $%.2f", co.total());
    }

    /**
     * Apply the currently active discount rules
     * @param chkOut
     * @return
     */
    private static Checkout applyDiscountRules(Checkout chkOut) {

        Checkout checkout;

        // we're going to have a 3 for 2 deal on Apple TVs. For example, if you buy 3 Apple TVs, you will pay the price of 2 only
        checkout = new PricingRule().given(chkOut)
                .when(co -> co.includesOrMore(3, "atv"))
                .then(co -> co.buyNgetOnefor$(3, "atv", 0.0));

        // the brand new Super iPad will have a bulk discounted applied, where the price will drop to $499.99 each, if someone buys more than 4
        checkout = new PricingRule().given(checkout)
                .when(co -> co.includesOrMore(4, "ipd"))
                .then(co -> co.reducePrice("ipd", 499.99));

        // we will bundle in a free VGA adapter free of charge with every MacBook Pro sold
        checkout = new PricingRule().given(checkout)
                .then(co -> co.buyXgetYfor$("mbp", "vga", 0.0));

        return checkout;
    }
}
