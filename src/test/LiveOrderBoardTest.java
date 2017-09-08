package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.LiveOrderBoard;
import main.OrderType;

public class LiveOrderBoardTest {

    private LiveOrderBoard board;

    @Before
    public void setUp() {
        board = new LiveOrderBoard();
    }

    @Test
    public void shouldRegisterSellOrders() {
        int orderId = board.registerOrder(1, 3500, 306, OrderType.SELL);
        assertTrue(board.contains(orderId));
    }

    @Test
    public void shouldRegisterBuyOrders() {
        int orderId = board.registerOrder(1, 3500, 306, OrderType.BUY);
        assertTrue(board.contains(orderId));
    }

    @Test
    public void shouldCancelOrderIfOrderExists() {
        int orderId = board.registerOrder(1, 3500, 306, OrderType.SELL);
        assertTrue(board.contains(orderId));
        board.cancelOrder(orderId);
        assertFalse(board.contains(orderId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCancelOrderIfOrderDoesNotExist() {
        int orderId = board.registerOrder(1, 3500, 306, OrderType.SELL);
        board.cancelOrder(orderId);
        board.cancelOrder(orderId);
    }

    @Test
    public void summaryShouldShowSellOrders() {
        board.registerOrder(1, 3500, 306, OrderType.SELL);

        String expected = "SELL: 3.5kg for £306\n";
        StringBuilder actual = new StringBuilder();
        board.getSummary(actual);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void summaryShouldShowBuyOrders() {
        board.registerOrder(1, 3500, 306, OrderType.BUY);

        String expected = "BUY: 3.5kg for £306\n";
        StringBuilder actual = new StringBuilder();
        board.getSummary(actual);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void summaryShouldShowSellOrdersInAscendingPriceOrder() {
        board.registerOrder(1, 3500, 306, OrderType.SELL);
        board.registerOrder(2, 1200, 310, OrderType.SELL);
        board.registerOrder(3, 1500, 307, OrderType.SELL);

        String expected =
                "SELL: 3.5kg for £306\n" +
                "SELL: 1.5kg for £307\n" +
                "SELL: 1.2kg for £310\n";

        StringBuilder actual = new StringBuilder();
        board.getSummary(actual);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void summaryShouldShowBuyOrdersInDescendingPriceOrder() {
        board.registerOrder(1, 3500, 306, OrderType.BUY);
        board.registerOrder(2, 1200, 310, OrderType.BUY);
        board.registerOrder(3, 1500, 307, OrderType.BUY);

        String expected =
                "BUY: 1.2kg for £310\n" +
                "BUY: 1.5kg for £307\n" +
                "BUY: 3.5kg for £306\n";

        StringBuilder actual = new StringBuilder();
        board.getSummary(actual);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void summaryShouldMergeOrdersByPrice() {
        board.registerOrder(1, 3500, 309, OrderType.SELL);
        board.registerOrder(1, 3500, 310, OrderType.SELL);
        board.registerOrder(2, 1200, 310, OrderType.SELL);
        board.registerOrder(3, 1500, 307, OrderType.BUY);
        board.registerOrder(4, 1200, 307, OrderType.BUY);

        String expected =
                "SELL: 3.5kg for £309\n" +
                "SELL: 4.7kg for £310\n" +
                "BUY: 2.7kg for £307\n";

        StringBuilder actual = new StringBuilder();
        board.getSummary(actual);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void summaryShouldBeBlankIfNoOrdersExist() {
        String expected = "";

        StringBuilder actual = new StringBuilder();
        board.getSummary(actual);

        assertEquals(expected, actual.toString());
    }
}
