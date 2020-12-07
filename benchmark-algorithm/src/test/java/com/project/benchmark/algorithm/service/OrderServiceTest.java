package com.project.benchmark.algorithm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.benchmark.algorithm.dto.base.PageParams;
import com.project.benchmark.algorithm.dto.base.SortParams;
import com.project.benchmark.algorithm.dto.order.NewOrderTO;
import com.project.benchmark.algorithm.dto.order.OrderFiltersTO;
import com.project.benchmark.algorithm.dto.response.ResponseDataTO;
import com.project.benchmark.algorithm.dto.order.OrderTO;
import com.project.benchmark.algorithm.dto.stock.StockTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionFiltersTO;
import com.project.benchmark.algorithm.dto.transaction.TransactionTO;
import com.project.benchmark.algorithm.dto.user.LoginUserTO;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OrderServiceTest extends TestCase {

    /*

    UserService userService;
    OrderService orderService;

    @Override
    public void setUp() {
        userService = new UserService(new LinkedBlockingQueue<>());
        String auth = login();
        assertNotNull(auth);
        orderService = new OrderService(auth, new LinkedBlockingQueue<>());
    }

    private String login() {
        LoginUserTO user = new LoginUserTO();
        user.setUsername("MarcinNajman@gmail.pl");
        user.setPassword("MarcinNajman.gmail.pl1");
        return userService.login(user).getData();
    }

    private ResponseDataTO<List<OrderTO>> getOrders() throws IOException {
        OrderFiltersTO filters = new OrderFiltersTO();
        SortParams sort = new SortParams("id", true);
        PageParams params = new PageParams(0, 20, Collections.singletonList(sort));
        filters.setPageParams(params);
        return orderService.getOrders(filters);
    }

    public void testGetOrders() throws IOException {
        var response = getOrders();
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    public void testGetOrderById() throws IOException {
        int randomOrderId = getOrders().getData()
                .stream().mapToInt(OrderTO::getId).findAny().orElseThrow();
        ResponseDataTO<OrderTO> response = orderService.getOrderById(randomOrderId);
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    public void testGetOrderTransactions() throws IOException {
        int randomOrderId = getOrders().getData()
                .stream().mapToInt(OrderTO::getId).findAny().orElseThrow();
        ResponseDataTO<List<TransactionTO>> response = orderService.getOrderTransactions(randomOrderId, new TransactionFiltersTO());
        assertNull(response.getError());
        assertNotNull(response.getData());
    }

    public void testCreateOrder() throws JsonProcessingException {
        NewOrderTO order = createExampleOrder();
        ResponseDataTO<Void> response = orderService.createOrder(order);
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }

    private NewOrderTO createExampleOrder(){
        NewOrderTO newOrder = new NewOrderTO();
        newOrder.setAmount((long) 10);
        newOrder.setDateClosing(null);
        newOrder.setDateCreation(OffsetDateTime.now());
        newOrder.setDateExpiration(OffsetDateTime.now().plusDays(3));
        newOrder.setOrderType("BUYING_ORDER");
        newOrder.setPrice(1.0);
        newOrder.setPriceType("EQUAL");
        newOrder.setRemainingAmount((long)10);
        StockTO stockTO = new StockTO();
        stockTO.setId(633); //istniejący stock
        stockTO.setPriceChangeRatio(0.0);
        stockTO.setName("CBANewFunc");
        stockTO.setAmount((long)10);
        stockTO.setCurrentPrice(5.05);
        stockTO.setAbbreviation("CBA");
        stockTO.setTag("BENCHMARK");
        newOrder.setStock(stockTO);
        return newOrder;
    }

    public void testDeactivateOrder() throws JsonProcessingException {
        Integer orderId = 10457;
        ResponseDataTO<Void> response = orderService.deactivateOrder(orderId);
        assertNull(response.getError());
        assertEquals(Integer.valueOf(200), response.getParams().getStatus());
    }

     */
}
