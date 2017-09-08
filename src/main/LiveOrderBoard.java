package main;

import java.util.*;
import java.util.stream.Collectors;

public class LiveOrderBoard {
    private HashMap<Integer, Order> orders;

    public LiveOrderBoard() {
        orders = new HashMap<Integer, Order>();
    }

    public int registerOrder(int userId, int quantityInGrams, int price, OrderType type) {
        int orderId = Order.getNextId();
        orders.put(orderId, new Order(userId, quantityInGrams, price, type));
        return orderId;
    }

    public void cancelOrder(int orderId) throws IllegalArgumentException {
        Order o = orders.remove(orderId);
        if (o == null) {
            // Could be improved with a custom exception
            throw new IllegalArgumentException();
        }
    }

    public boolean contains(int orderId) {
        return orders.containsKey(orderId);
    }

    /** Alternatively could return List<OrderSummary> instead of
     * appending to StringBuilder. I chose this solution to stop the
     * client from having to handle the list of objects.
     */
    public void getSummary(StringBuilder builder) {
        for (OrderSummary o : getSummary(OrderType.SELL)) {
            builder.append(o.toString() + "\n");
        }
        for (OrderSummary o : getSummary(OrderType.BUY)) {
            builder.append(o.toString() + "\n");
        }
    }

    private List<OrderSummary> getSummary(OrderType orderType) {
        List<Order> filteredAndSorted = orders
            .values()
            .stream()
            .filter(order -> order.getType() == orderType)
            .sorted()
            .collect(Collectors.toList());

        return groupOrdersByPrice(filteredAndSorted);
    }

    /** This method expects the input list to have orders of
     * one type only and to be sorted accordingly.
     */
    private List<OrderSummary> groupOrdersByPrice(List<Order> filteredAndSorted) {
        List<OrderSummary> grouped = new ArrayList<OrderSummary>();
        OrderSummary summary = null;
        Order previous = null;

        for (Order current : filteredAndSorted) {
            if (summary == null || current.getPrice() != previous.getPrice()) {
                summary = current.getOrderSummary();
                grouped.add(summary);
            } else {
                summary.sumToQuantityInGrams(current.getQuantityInGrams());
            }
            previous = current;
        }

        return grouped;
    }
}