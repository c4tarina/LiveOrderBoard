package main;

public enum OrderType {
    SELL(0), BUY(1);

    private int value;

    OrderType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}