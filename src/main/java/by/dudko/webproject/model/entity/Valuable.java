package by.dudko.webproject.model.entity;

import java.math.BigDecimal;

public interface Valuable {

    /**
     * @return price without discount.
     */
    BigDecimal getPrice();

    /**
     * @return discount percentage.
     */
    default BigDecimal getDiscount() {
        return new BigDecimal("0.0");
    }

    /**
     * Returns discount of the valuable item. If there is no discount on the product, 0.0 is returned.
     * @return the specified discount amount.
     */
    default BigDecimal calculateDiscountAmount() {
        BigDecimal price = getPrice();
        BigDecimal discount = getDiscount();
        return (price != null && discount != null) ? discount.multiply(price) : new BigDecimal("0.0");
    }

    /**
     * @return discounted price of the valuable item.
     */
    default BigDecimal getDiscountedPrice() {
        return getPrice().subtract(calculateDiscountAmount());
    }
}
