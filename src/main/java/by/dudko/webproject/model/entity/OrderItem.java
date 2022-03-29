package by.dudko.webproject.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItem implements Serializable {
    private Dish dish;
    private int amount;

    public OrderItem(Dish dish, int amount) {
        this.dish = dish;
        this.amount = amount;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void increaseAmount(int value) {
        amount += value;
    }

    public BigDecimal calculateItemPrice() { // TODO check method
        BigDecimal realDishPrice = dish.calculateDiscountedPrice();
        BigDecimal productAmount = new BigDecimal(amount);
        return realDishPrice.multiply(productAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (amount != orderItem.amount) return false;
        return dish != null ? dish.equals(orderItem.dish) : orderItem.dish == null;
    }

    @Override
    public int hashCode() {
        int result = dish != null ? dish.hashCode() : 0;
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItem{");
        sb.append("dish=").append(dish);
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
