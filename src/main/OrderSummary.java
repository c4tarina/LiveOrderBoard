package main;

public class OrderSummary {
    private long quantityInGrams;
    private int price;
    private OrderType type;

    public OrderSummary(Order order) {
        this.quantityInGrams = order.getQuantityInGrams();
        this.price = order.getPrice();
        this.type = order.getType();
    }

    public void sumToQuantityInGrams(int quantityInGrams) {
        this.quantityInGrams += quantityInGrams;
    }

    @Override
    public String toString() {
        return type + ": " + quantityInGrams/1000.0 + "kg for £" + price;
    }
}
