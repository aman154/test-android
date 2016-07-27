package com.ibusl.android.register.model;

/**
 * Created by aman on 15/4/16.
 */
public class Discount {
    private long discountId;
    private String discountName;
    private int discountType;
    private String discountQuantity;

    public Discount(){

    }

    public Discount(String discountName, int discountType, String discountQuantity) {
        this.discountName = discountName;
        this.discountType = discountType;
        this.discountQuantity = discountQuantity;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public String getDiscountQuantity() {
        return discountQuantity;
    }

    public void setDiscountQuantity(String discountQuantity) {
        this.discountQuantity = discountQuantity;
    }

    public long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }
}
