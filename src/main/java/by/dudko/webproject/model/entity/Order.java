package by.dudko.webproject.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order extends RootEntity { // todo fields and about price
    private long userId;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private BigDecimal price;
    private List<OrderItem> orderItems;

    public Order(long id, long userId, OrderStatus orderStatus, LocalDateTime orderDate,
                 BigDecimal price, List<OrderItem> orderItems) {
        super(id);
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.price = price;
        this.orderItems = orderItems;
    }

    public BigDecimal calculateOrderPrice() { // check method
        return orderItems.stream()
                .map(OrderItem::calculateItemPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (userId != order.userId) return false;
        if (orderStatus != order.orderStatus) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        return orderItems != null ? orderItems.equals(order.orderItems) : order.orderItems == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (orderItems != null ? orderItems.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("userId=").append(userId);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", price=").append(price);
        sb.append(", orderItems=").append(orderItems);
        sb.append('}');
        return sb.toString();
    }
}
