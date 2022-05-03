package by.dudko.webproject.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class OrderManager { // FIXME refactor this class
    private final Map<Integer, OrderItem> orderItems;
    private BigDecimal price;
    private BigDecimal totalDiscount;
    private int productQuantity;

    public OrderManager() {
        orderItems = new HashMap<>();
        price = new BigDecimal("0.0");
        totalDiscount = new BigDecimal("0.0");
    }

    public void addOrderItem(OrderItem orderItem) {
        int itemId = orderItem.getId();
        if (containsItem(itemId)) {
            changeOrderItemQuantity(itemId, orderItem.getQuantity());
        } else if (orderItem.getQuantity() > 0) {
            addNewOrderItem(orderItem);
        }
    }

    private void addNewOrderItem(OrderItem item) {
        int itemId = item.getId();
        orderItems.put(itemId, item);
        updateOrderElements(item, item.getQuantity());
    }

    /**
     *  Changes the quantity of orderItem with itemId to the specified value
     *  @param itemId the orderItem id
     *  @param value the specified value
     */
    public void changeOrderItemQuantity(int itemId, int value) {
        OrderItem item = orderItems.get(itemId);
        if (item == null) {
            return;
        }
        if (!willBeRemoved(item, value)) {
            item.increaseQuantity(value);
            updateOrderElements(item, value);
        } else {
            removeOrderItem(itemId);
        }
    }

    private void updateOrderElements(OrderItem item, int value) {
        updateQuantity(value);
        updateOrderPrice(item, value); // FIXME что-то идёт не так с общей ценой. Ошибка может быть и в OrderItem и в Valuable
        updateTotalDiscount(item, value); //
    }

    private void updateQuantity(int difference) {
        productQuantity += difference;
    }

    private void updateOrderPrice(OrderItem item, int difference) {
        Valuable valuable = item.getValuable();
        BigDecimal oneItemPrice = valuable.getDiscountedPrice();
        BigDecimal priceChange = oneItemPrice.multiply(new BigDecimal(difference));
        price = price.add(priceChange);
    }

    private void updateTotalDiscount(OrderItem item, int value) {
        Valuable valuable = item.getValuable();
        BigDecimal oneItemDiscount = valuable.calculateDiscountAmount();
        BigDecimal discountChange = oneItemDiscount.multiply(new BigDecimal(value));
        totalDiscount = totalDiscount.add(discountChange);
    }

    public boolean willBeRemoved(OrderItem item, int amountChange) {
        int itemAmount = item.getQuantity();
        return itemAmount + amountChange <= 0;
    }

    public void removeOrderItem(int itemId) {
        OrderItem item = orderItems.remove(itemId);
        if (item != null) {
            updateOrderElements(item, -item.getQuantity());
        }
    }

    public boolean containsItem(int itemId) { // TODO remove after addOrderItemFix (return boolean) addOrderItemIfAbsent
        return orderItems.containsKey(itemId);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems.values();
    }

    public BigDecimal getOrderPrice() { // TODO удалить после тестирования
        return orderItems.values().stream()
                .map(OrderItem::getDiscountedPrice)
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal("0.0"));
    }

    public Order prepareOrder(long userId) { // TODO localDateTime ???      OrderStatus ???
        Order order = new Order();
        order.setOrderItems(new ArrayList<>(orderItems.values()));
        order.setOrderDate(LocalDateTime.now());
        order.setPrice(price);
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.ACTIVE);
        return order;
    }

    public Optional<OrderItem> getOrderItemById(int itemId) {
        return Optional.ofNullable(orderItems.get(itemId));
    }

    public int getTotalProductQuantity() {
        return productQuantity;
    }

    public int getItemQuantity(int itemId) {
        OrderItem item = orderItems.get(itemId);
        return item != null ? item.getQuantity() : 0;
    }

    public Set<Integer> getItemsIdSet() {
        return new HashSet<>(orderItems.keySet());
    }
}
