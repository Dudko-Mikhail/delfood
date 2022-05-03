package by.dudko.webproject.model.entity;

import java.math.BigDecimal;

public class OrderItem extends ValuableEntity {
    private final ValuableEntity valuable;
    private final BigDecimal discountedPrice;
    private int quantity;

    public OrderItem(ValuableEntity valuable, int quantity) {
        this.valuable = valuable;
        discountedPrice = valuable.getDiscountedPrice();
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    @Override
    public BigDecimal getPrice() {
        return valuable.getPrice().multiply(new BigDecimal(quantity));
    }

    @Override
    public BigDecimal getDiscount() {
        return valuable.getDiscount();
    }

    @Override
    public BigDecimal calculateDiscountAmount() {
        return valuable.calculateDiscountAmount().multiply(new BigDecimal(quantity));
    }

    @Override
    public BigDecimal getDiscountedPrice() {
        return discountedPrice.multiply(new BigDecimal(quantity));
    }

    public Valuable getValuable() {
        return valuable;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * This method increase quantity of the valuable item by the given value.
     * Quantity value cannot be less than zero.
     * @param value the addend.
     */
    public void increaseQuantity(int value) {
        quantity += value;
        if (quantity < 0) {
            quantity = 0;
        }
    }

    public boolean isEmpty() {
        return quantity == 0;
    }

    @Override
    public Integer getId() {
        return valuable.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem item = (OrderItem) o;

        if (quantity != item.quantity) return false;
        return valuable != null ? valuable.equals(item.valuable) : item.valuable == null;
    }

    @Override
    public int hashCode() {
        int result = valuable != null ? valuable.hashCode() : 0;
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItem{");
        sb.append("valuable=").append(valuable);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
