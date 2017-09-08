package main;

public class Order implements Comparable<Order> {
    private int userId;
    private int quantityInGrams;
    private int price;
    private OrderType type;

    private static int nextId = 0;

    public static int getNextId() {
        return nextId;
    }

    public Order(int userId, int quantityInGrams, int price, OrderType type) {
        this.userId = userId;
        this.quantityInGrams = quantityInGrams;
        this.price = price;
        this.type = type;
        nextId++;
    }

    public int getUserId() {
        return userId;
    }

    public int getQuantityInGrams() {
        return quantityInGrams;
    }

    public int getPrice() {
        return price;
    }

    public OrderType getType() {
        return type;
    }

    public OrderSummary getOrderSummary() {
        return new OrderSummary(this);
    }

    @Override
    public int compareTo(Order o) {
        // sort SELL orders by price in ascending order
        if (this.type == OrderType.SELL && o.type == OrderType.SELL) {
            return this.price - o.price;
        // sort BUY orders by price in descending order
        } else if (this.type == OrderType.BUY && o.type == OrderType.BUY) {
            return o.price - this.price;
        }
        // put SELL orders before BUY orders
        return this.type.getValue() - o.type.getValue();
    }
}